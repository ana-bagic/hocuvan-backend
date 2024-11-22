package fer.proinz.hocuvan.rest;

import fer.proinz.hocuvan.helpers.StatisticsDTO;
import fer.proinz.hocuvan.service.EventService;
import fer.proinz.hocuvan.service.OrganizerService;
import fer.proinz.hocuvan.service.RequestDeniedException;
import fer.proinz.hocuvan.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    EventService eventService;
    @Autowired
    OrganizerService organizerService;
    @Autowired
    VisitorService visitorService;

    @GetMapping("")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getStat() {
        try {
            long numberOfEvents=eventService.countAll();
            long numberOfOrganizers=organizerService.countAll();
            long numberOfVisitors=visitorService.countAll();
                return ResponseEntity.status(HttpStatus.OK).body(new StatisticsDTO(numberOfEvents,numberOfOrganizers,numberOfVisitors));
        } catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }


}
