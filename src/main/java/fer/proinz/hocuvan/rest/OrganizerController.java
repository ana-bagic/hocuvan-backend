package fer.proinz.hocuvan.rest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import fer.proinz.hocuvan.domain.Image;
import fer.proinz.hocuvan.helpers.ImageDTO;
import fer.proinz.hocuvan.helpers.OrganizerDTO;
import fer.proinz.hocuvan.helpers.UserFilterDTO;
import fer.proinz.hocuvan.service.ImageService;
import fer.proinz.hocuvan.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.service.OrganizerService;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class OrganizerController sends responses to REST requests in JSON
 * format. It is a controller for Organizer entity.
 * 
 * @author Nina
 */
@RestController
@RequestMapping("/organizer")
public class OrganizerController {

	/** The organizer service. */
	@Autowired
	private OrganizerService organizerService;


	@Autowired
	private ImageService imageService;

	/**
	 * Lists all organizers when HTTP GET method called.
	 *
	 * @return the list of all organizers
	 */
	@GetMapping("")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public List<Organizer> listOrganizers() {
		return organizerService.listAll();
	}

	@GetMapping("/headquarters")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public List<String> listHeadquarters() {
		return organizerService.organisationHeadquarters();
	}

	@GetMapping("/filterByUsername")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getOrganizerByUsername(@RequestBody UserFilterDTO userFilterDTO) {
		try{
			List<Organizer> organizers=organizerService.filterByUsername(userFilterDTO.getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(organizers);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/{username}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> findByUsername(@PathVariable("username") String username) {

			try {
				Organizer organizer = organizerService.findByUsername(username);
				if (organizer != null) {
					return ResponseEntity.status(HttpStatus.OK).body(new OrganizerDTO(organizer.getOrgName(),organizer.getHeadquarters(),organizer.getUsername(),organizer.getPassword(),organizer.getEmail(),organizer.getPhoneNumber()));
				}
			} catch (RequestDeniedException ex) {
				return ResponseEntity.badRequest().body(ex);
			}

		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može pristupati samo svojim podacima!"));
	}


	/**
	 * Creates new organizer entity when HTTP POST method called.
	 *
	 * @param organizerReq the organizer given in JSON format
	 * @return the new created organizer
	 */
	@PostMapping("/register")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> createOrganizer(@RequestBody Organizer organizerReq) {
		try {
			Organizer organizer = organizerService.createOrganizer(organizerReq);
			if (organizer != null) {
				return ResponseEntity.status(HttpStatus.OK).body(OrganizerDTO.fromOrganizerToOrganizerDTO(organizer));
			}
		} catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}

		return ResponseEntity.badRequest().body(new RequestDeniedException("Neispravan zahtjev"));
	}

	@PutMapping("/{username}/updateOrganizer")
	@Secured({"ROLE_ORGANIZER"})
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> updateOrganizer(@PathVariable("username") String username, @AuthenticationPrincipal User user,@RequestBody Organizer organizerReq) {
		if(username.equals(user.getUsername())){
			try {
				Organizer organizer = organizerService.findByUsername(username);
				if (organizer != null) {
					Organizer updatedOrganizer=new Organizer(organizer.getId(),organizerReq.getOrgName(),organizerReq.getHeadquarters(),organizerReq.getUsername(),organizerReq.getPassword(),organizerReq.getEmail(),organizerReq.getPhoneNumber());
					updatedOrganizer=organizerService.updateOrganizer(updatedOrganizer);
					return ResponseEntity.status(HttpStatus.OK).body(new OrganizerDTO(updatedOrganizer.getOrgName(),updatedOrganizer.getHeadquarters(),updatedOrganizer.getUsername(),updatedOrganizer.getPassword(),updatedOrganizer.getEmail(),updatedOrganizer.getPhoneNumber()));
				}
			} catch (RequestDeniedException ex) {
				return ResponseEntity.badRequest().body(ex);
			}
		}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može mijenjati samo svoje podatke!"));
	}

	@DeleteMapping("/{username}/deleteOrganizer")
	@Secured({"ROLE_ORGANIZER", "ROLE_ADMIN"})
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> deleteOrganizer(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
		if(username.equals(user.getUsername())){
			try {
				Organizer organizer = organizerService.findByUsername(username);
				if (organizer != null) {
					Organizer deletedOrganizer=organizerService.deleteOrganizer(organizer.getId());
					return ResponseEntity.status(HttpStatus.OK).body(new OrganizerDTO(deletedOrganizer.getOrgName(),deletedOrganizer.getHeadquarters(),deletedOrganizer.getUsername(),deletedOrganizer.getPassword(),deletedOrganizer.getEmail(),deletedOrganizer.getPhoneNumber()));
				}
			} catch (RequestDeniedException ex) {
				return ResponseEntity.badRequest().body(ex);
			}
		}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može obrisati samo svoj račun!"));
	}

	@RequestMapping(value = "/{username}/updateProfilePhoto", method = RequestMethod.POST)
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> updatePhoto(@RequestPart(value = "file") MultipartFile multipartFile,@PathVariable("username") String username){//,@AuthenticationPrincipal User user){
		try {
				Organizer organizer = organizerService.findByUsername(username);
				if (organizer != null) {
					byte[] bytes = multipartFile.getBytes();
					imageService.createImage(new Image(1L,Base64.getEncoder().encodeToString(bytes),null,organizer,null));

					return ResponseEntity.status(HttpStatus.OK).body("Slika postavljena");
				}
			} catch (RequestDeniedException | IOException ex) {
				return ResponseEntity.badRequest().body(ex);
			}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može mijenjati samo svoju profilnu fotografiju!"));
	}

	@GetMapping("/image/{username}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getOrganizerImage(@PathVariable("username") String username) {
		try{
			Organizer organizer=organizerService.findByUsername(username);
			Image im = imageService.findByOrganizer(organizer);
			if(im == null){
				return ResponseEntity.status(HttpStatus.OK).body(new ImageDTO(null));
			}else {
				String image= im.getImage();
				return ResponseEntity.status(HttpStatus.OK).body(new ImageDTO(image));
			}
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}
}

