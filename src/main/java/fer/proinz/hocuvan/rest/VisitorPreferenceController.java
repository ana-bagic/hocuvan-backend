package fer.proinz.hocuvan.rest;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.domain.VisitorPreference;
import fer.proinz.hocuvan.helpers.CategoryDTO;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.helpers.ReplyDTO;
import fer.proinz.hocuvan.service.EventCategoryService;
import fer.proinz.hocuvan.service.RequestDeniedException;
import fer.proinz.hocuvan.service.VisitorPreferenceService;
import fer.proinz.hocuvan.service.VisitorService;
import fer.proinz.hocuvan.helpers.*;
import fer.proinz.hocuvan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/preferences")
public class VisitorPreferenceController {

    @Autowired
    private VisitorPreferenceService visitorPreferenceService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private EventCategoryService eventCategoryService;

    //vraca sve *DANASNJE* evente koji su najdraže kategorije korisnika
    @GetMapping("/{username}/favourites")
    @Secured("ROLE_VISITOR")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> favouriteCategoriesTodaysEvents(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        try {
            //if(user.getUsername().equals(username)){
                Date date=java.util.Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate= formatter.format(date);

                List<Event> possibleEvents=visitorPreferenceService.getEventsOfFavouriteCategories(visitorService.findByUsername(username));
                List<EventDTO> todaysEvents=new ArrayList<>();

                for(Event e : possibleEvents) {
                    if (formatter.format(e.getDate()).equals(strDate)) {
                        todaysEvents.add(EventDTO.fromEventToEventDTO(e));
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(todaysEvents);
           // }
        }catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
       // return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može vidjeti samo svoje najdraže kategorije evenata!"));
    }

    @PostMapping("/addFavourites")
    @Secured("ROLE_VISITOR")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> addFavouriteCategories(@RequestBody CategoryDTO categoryDTO, @AuthenticationPrincipal User user) {
        try {
            EventCategory event=eventCategoryService.findByName(categoryDTO.getCategoryName());
            Visitor visitor=visitorService.findByUsername(user.getUsername());
            visitorPreferenceService.save(event,visitor);
            return ResponseEntity.status(HttpStatus.OK).body(event);
        }catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @DeleteMapping("/{username}/deleteFavourites")
    @Secured({"ROLE_VISITOR","ROLE_ADMIN"})
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> deleteFavouritesForVisitor(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        if(username.equals(user.getUsername()) || user.getUsername().equals("admin")){
            try {
                int num=0;
                Visitor visitor = visitorService.findByUsername(username);
                if (visitor != null) {
                    num=visitorPreferenceService.deleteAllByVisitor(visitor).size();
                }
                return ResponseEntity.status(HttpStatus.OK).body(new ReplyDTO("Obrisano " + num + " najdražih kategorija za korisnika "+username +"!"));
            } catch (RequestDeniedException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može obrisati samo svoj račun!"));
    }

    @GetMapping("/{username}/getCategories")
    @Secured("ROLE_VISITOR")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getFavouriteCategories(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        if(username.equals(user.getUsername()) || user.getUsername().equals("admin")) {
            try {
                Visitor visitor = visitorService.findByUsername(username);
                List<VisitorPreference> visitorPreferences = visitorPreferenceService.findAllByVisitor(visitor);
                List<CategoryDTO> categoryDTOS=new ArrayList<>();
                if(visitorPreferences.size()>0){
                    categoryDTOS = CategoryDTO.fromVisitorPreferencesToCategories(visitorPreferences);
                }
                return ResponseEntity.status(HttpStatus.OK).body(categoryDTOS);
            } catch (RequestDeniedException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može vidjeti samo svoje najdraže kategorije!"));
    }
}
