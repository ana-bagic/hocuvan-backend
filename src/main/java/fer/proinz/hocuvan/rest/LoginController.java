package fer.proinz.hocuvan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.helpers.LoginRequest;
import fer.proinz.hocuvan.helpers.OrganizerDTO;
import fer.proinz.hocuvan.helpers.VisitorDTO;
import fer.proinz.hocuvan.rest.security.UserDetailsServiceImp;
import fer.proinz.hocuvan.service.OrganizerService;
import fer.proinz.hocuvan.service.RequestDeniedException;
import fer.proinz.hocuvan.service.VisitorService;

@RestController
@RequestMapping("/auth")
public class LoginController {
	@Value("${admin.password}")
	private String adminPasswordHash;

	/**
	 * The organizer service.
	 */
	@Autowired
	private OrganizerService organizerService;
	@Autowired
	private VisitorService visitorService;

	@Autowired
	private UserDetailsServiceImp userDetailsServiceImp;

	@PostMapping("")
	@CrossOrigin(origins = { "https://hocuvan-deployment.herokuapp.com", "http://localhost:4200" })
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> LoginAuthentication(@RequestBody LoginRequest req) {
		Visitor loggedVisitor;
		Organizer loggedOrganizer = organizerService.findByUsername(req.getUsername());

		if (loggedOrganizer == null) {// provjeri da li je među visitorima, ako nije pogledaj među organizatorima
			loggedVisitor = visitorService.findByUsername(req.getUsername());
			if (loggedVisitor != null
					&& new BCryptPasswordEncoder().matches(req.getPassword(), loggedVisitor.getPassword())) {
				userDetailsServiceImp.loadUserByUsername(loggedVisitor.getUsername());
				return ResponseEntity.status(HttpStatus.OK).body(VisitorDTO.fromVisitorToVisitorDTO(loggedVisitor));
			} else if (req.getUsername().equals("admin")
					&& new BCryptPasswordEncoder().matches(req.getPassword(), adminPasswordHash)) {
				userDetailsServiceImp.loadUserByUsername("admin");
				return ResponseEntity.status(HttpStatus.OK).body(req);
			} else {
				return ResponseEntity.badRequest()
						.body(new RequestDeniedException("Netočno korisničko ime ili lozinka"));
			}

		} else if (new BCryptPasswordEncoder().matches(req.getPassword(), loggedOrganizer.getPassword())) {
			userDetailsServiceImp.loadUserByUsername(loggedOrganizer.getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(OrganizerDTO.fromOrganizerToOrganizerDTO(loggedOrganizer));
		} else {
			System.out.println(req.getUsername() + " " + req.getPassword());
			return ResponseEntity.badRequest().body(new RequestDeniedException("Netočno korisničko ime ili lozinka"));
		}
	}

}
