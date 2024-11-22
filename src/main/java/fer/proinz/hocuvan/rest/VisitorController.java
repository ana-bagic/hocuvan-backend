package fer.proinz.hocuvan.rest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import fer.proinz.hocuvan.domain.Image;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.helpers.ImageDTO;
import fer.proinz.hocuvan.helpers.VisitorDTO;
import fer.proinz.hocuvan.helpers.UserFilterDTO;
import fer.proinz.hocuvan.service.EventVisitorsService;
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

import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.service.VisitorService;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class VisitorController sends responses to REST requests in JSON format.
 * It is a controller for Visitor entity.
 * 
 * @author Nina
 */

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    /**
     * The visitor service.
     */
    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private EventVisitorsService eventVisitorsService;

    @GetMapping("/{username}")
    @Secured({"ROLE_VISITOR", "ROLE_ADMIN"})
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> findByUsername(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        if(username.equals(user.getUsername()) || user.getUsername().equals("admin")){
            try {
                Visitor visitor = visitorService.findByUsername(username);
                if (visitor != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(VisitorDTO.fromVisitorToVisitorDTO(visitor));
                }
            } catch (RequestDeniedException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može pristupati samo svojim podacima!"));
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> listVisitors() {
        try{
            List<Visitor> visitors=visitorService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(visitors);
        }catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @GetMapping("/filterByUsername")
    @Secured("ROLE_ADMIN")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getVisitorByUsername(@RequestBody UserFilterDTO visitorFilterDTO) {
        try{
            List<Visitor> visitors=visitorService.filterByUsername(visitorFilterDTO.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(visitors);
        }catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }


    /**
     * Creates new visitor entity when HTTP POST method called.
     *
     * @param visitorReq the visitor given in JSON format
     * @return the new created visitor
     */
    @PostMapping("/register")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> createVisitor(@RequestBody Visitor visitorReq) {

        try {

            Visitor visitor = visitorService.createVisitor(visitorReq);

            if (visitor != null) {
                return ResponseEntity.status(HttpStatus.OK).body(VisitorDTO.fromVisitorToVisitorDTO(visitor));
            }
        } catch (RequestDeniedException  ex) {
            return ResponseEntity.badRequest().body(ex);
        }

        return ResponseEntity.badRequest().body(new RequestDeniedException("Neispravan zahtjev")); // TODO proslijediti još neke moguće
                                                                             //TODO  razloge neispravnog zahtjeva?
    }

    @PutMapping("/{username}/updateVisitor")
    @Secured({"ROLE_VISITOR"})
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> updateVisitor(@PathVariable("username") String username, @AuthenticationPrincipal User user,@RequestBody Visitor visitorReq) {
        if(username.equals(user.getUsername())){
            try {
                Visitor visitor = visitorService.findByUsername(username);
                if (visitor != null) {
                    Visitor updatedVisitor=new Visitor(visitor.getId(),visitorReq.getFirstName(),visitorReq.getLastName(),visitorReq.getUsername(),visitorReq.getPassword(),visitorReq.getEmail(),visitorReq.getPhoneNumber());
                    updatedVisitor=visitorService.updateVisitor(updatedVisitor);
                    return ResponseEntity.status(HttpStatus.OK).body(VisitorDTO.fromVisitorToVisitorDTO(updatedVisitor));
                }
            } catch (RequestDeniedException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može mijenjati samo svoje podatke!"));
    }

    @RequestMapping(value = "/{username}/updateProfilePhoto", method = RequestMethod.POST)
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> updatePhoto(@RequestPart(value = "file") MultipartFile multipartFile,@PathVariable("username") String username){//,@AuthenticationPrincipal User user){
        try {
                Visitor visitor = visitorService.findByUsername(username);
                if (visitor != null) {
                    byte[] bytes = multipartFile.getBytes();
                    imageService.createImage(new Image(1L,Base64.getEncoder().encodeToString(bytes),visitor,null,null));

                    return ResponseEntity.status(HttpStatus.OK).body("Slika postavljena");
                }
            } catch (RequestDeniedException | IOException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može mijenjati samo svoju profilnu fotografiju!"));
    }

    @GetMapping("/image/{username}")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getVisitorImage(@PathVariable("username") String username) {
        try{
            Visitor visitor=visitorService.findByUsername(username);
            Image im = imageService.findByVisitor(visitor);
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

    @DeleteMapping("/{username}/deleteVisitor")
    @Secured({"ROLE_VISITOR","ROLE_ADMIN"})
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> deleteVisitor(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        if(username.equals(user.getUsername()) || user.getUsername().equals("admin")){
            try {
                Visitor visitor = visitorService.findByUsername(username);
                if (visitor != null) {
                    Visitor deletedVisitor=visitorService.deleteVisitor(visitor.getId());
                    return ResponseEntity.status(HttpStatus.OK).body(VisitorDTO.fromVisitorToVisitorDTO(deletedVisitor));}
            } catch (RequestDeniedException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može obrisati samo svoj račun!"));
    }

    @GetMapping("/events")
    @Secured({"ROLE_VISITOR","ROLE_ADMIN","ROLE_ORGANIZER"})
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> showAttendingEvents(@AuthenticationPrincipal User user) {
        try {
            List<EventDTO> events=eventVisitorsService.getEventsOfVisitor(visitorService.findByUsername(user.getUsername()));
            return ResponseEntity.status(HttpStatus.OK).body(events);
        }catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }


}
