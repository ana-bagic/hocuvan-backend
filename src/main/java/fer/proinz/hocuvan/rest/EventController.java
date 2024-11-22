package fer.proinz.hocuvan.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import fer.proinz.hocuvan.domain.*;
import fer.proinz.hocuvan.helpers.*;
import fer.proinz.hocuvan.service.*;
import fer.proinz.hocuvan.domain.*;
import fer.proinz.hocuvan.helpers.*;
import fer.proinz.hocuvan.service.*;
import fer.proinz.hocuvan.utils.GeoCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class EventController sends responses to REST requests in JSON format. It
 * is a controller for Event entity.
 * 
 * @author Nina
 */
@RestController
@RequestMapping("/events")
public class EventController {

	/** The event service. */
	@Autowired
	private EventService eventService;

	@Autowired
	private OrganizerService organizerService;

	@Autowired
	private EventCategoryService eventCategoryService;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private EventVisitorsService eventVisitorsService;

	/**
	 * Lists all events when HTTP GET method called.
	 *
	 * @return the list of all events
	 */
	@GetMapping("")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public List<Event> listEvents() {
		return eventService.listAll();
	}

	@GetMapping("/{id}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getEventById(@PathVariable("id") String id) {
		try {
			Event event=eventService.fetch(Long.parseLong(id));
			EventDTO eventDTO = EventDTO.fromEventToEventDTO(event);
			return ResponseEntity.status(HttpStatus.OK).body(eventDTO);
		} catch (RequestDeniedException ex) {
		return ResponseEntity.badRequest().body(ex);
		}
	}


	@PostMapping("/{username}/createEvent")
	@Secured("ROLE_ORGANIZER")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> createEvent(@PathVariable("username") String username, @AuthenticationPrincipal User user, @RequestBody EventDTO eventDTO) {
		if(username.equals(user.getUsername())){
		try {
			eventDTO.setOrganizerUsername(username);
			Event createdEvent = eventService.createEvent(eventDTO);

			if (createdEvent != null) {
				return ResponseEntity.status(HttpStatus.OK).body(createdEvent);}
		} catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}}

		return ResponseEntity.badRequest().body(new RequestDeniedException("Neispravan zahtjev"));

	}

	@PutMapping("/{username}/updateEvent/{id}")
	@Secured("ROLE_ORGANIZER")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> updateEvent(@PathVariable("id") String id, @PathVariable("username") String username, @AuthenticationPrincipal User user, @RequestBody EventDTO eventDTO) {
		if(username.equals(user.getUsername())){
			try {
				Event event=eventService.fetch(Long.valueOf(id));
				if (event != null) {
					Event updatedEvent=new Event(event.getEventId(),eventDTO.getEventName(),eventDTO.getDate(),eventDTO.getLocation(),eventDTO.getDescription(),eventDTO.getPrice(),eventDTO.getNumberOfSeats(),organizerService.findByUsername(username),eventCategoryService.findByName(eventDTO.getCategoryName()));
					updatedEvent=eventService.updateEvent(updatedEvent);
					return ResponseEntity.status(HttpStatus.OK).body(new EventDTO(updatedEvent.getEventId(),updatedEvent.getEventName(),updatedEvent.getDate(),updatedEvent.getLocation(),updatedEvent.getDescription(),updatedEvent.getPrice(),updatedEvent.getNumberOfSeats(),updatedEvent.getOrganizerId().getUsername(), updatedEvent.getOrganizerId().getOrgName() ,updatedEvent.getCategoryId().getCategoryName()));}
			} catch (RequestDeniedException ex) {
				return ResponseEntity.badRequest().body(ex);
			}
		}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može mijenjati samo svoje podatke!"));
	}

	@DeleteMapping("/{username}/deleteEvent/{id}")
	@Secured("ROLE_ORGANIZER")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> deleteEvent(@PathVariable("id") String id, @PathVariable("username") String username, @AuthenticationPrincipal User user) {
		if(username.equals(user.getUsername())){
			try {
				Event event = eventService.fetch(Long.valueOf(id));
				if (event != null) {
					Event deleteEvent = eventService.deleteEvent(event.getEventId());
					return ResponseEntity.status(HttpStatus.OK).body(new EventDTO(deleteEvent.getEventId(),deleteEvent.getEventName(),deleteEvent.getDate(),deleteEvent.getLocation(),deleteEvent.getDescription(),deleteEvent.getPrice(),deleteEvent.getNumberOfSeats(),deleteEvent.getOrganizerId().getUsername(), deleteEvent.getOrganizerId().getOrgName(),deleteEvent.getCategoryId().getCategoryName()));}
			} catch (RequestDeniedException ex) {
				return ResponseEntity.badRequest().body(ex);
			}
		}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može obrisati samo svoj račun!"));
	}

	@PostMapping("/filterEvents")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> filterEvents(@RequestBody EventFilterDTO eventFilterDTO) {
		try {
			List<Event> events = eventService.filterEvents(eventFilterDTO);
			return ResponseEntity.status(HttpStatus.OK).body(events);
		}catch (RequestDeniedException | ParseException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/addToFavourites/{id}")
	@Secured("ROLE_VISITOR")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> selectImComing(@PathVariable("id") String id, @AuthenticationPrincipal User user) {
		try {
			Event event=eventService.fetch(Long.valueOf(id));
			Visitor visitor=visitorService.findByUsername(user.getUsername());
			eventVisitorsService.save(event,visitor);
			ReplyDTO reply = new ReplyDTO("Događaj " + event.getEventName() + " je dodan u favorite!");

			return ResponseEntity.status(HttpStatus.OK).body(reply);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/removeFromFavourites/{id}")
	@Secured("ROLE_VISITOR")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> deselectImComing(@PathVariable("id") String id, @AuthenticationPrincipal User user) {
		try {
			Event event=eventService.fetch(Long.valueOf(id));
			Visitor visitor=visitorService.findByUsername(user.getUsername());
			eventVisitorsService.remove(event,visitor);
			ReplyDTO reply = new ReplyDTO("Događaj " + event.getEventName() + " je maknut iz favorita!");

			return ResponseEntity.status(HttpStatus.OK).body(reply);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/visitors/{id}")
	@Secured({"ROLE_VISITOR","ROLE_ADMIN","ROLE_ORGANIZER"})
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> showEventsVisitors(@PathVariable("id") String id, @AuthenticationPrincipal User user) {
		try {
			List<VisitorDTO> visitors=eventVisitorsService.getVisitorsOfEvent(eventService.fetch(Long.valueOf(id)));
			return ResponseEntity.status(HttpStatus.OK).body(visitors);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/todaysEvents")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> showTodaysEvents() {
		try {
			Date date=java.util.Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String strDate= formatter.format(date);

			List<Event> possibleEvents=eventService.listAll();
			List<EventLocationDTA> todaysEvents=new ArrayList<>();

			for(Event e : possibleEvents){
				if(formatter.format(e.getDate()).equals(strDate)){
					todaysEvents.add(GeoCoder.codeAddressToLatAndLng(e));
				}
			}

			return ResponseEntity.status(HttpStatus.OK).body(todaysEvents);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/{id}/getEventLocation")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getEventsLocation(@PathVariable("id") String id) {
		try {
			Event event = eventService.fetch(Long.valueOf(id));
			EventLongLat eventLongLat = GeoCoder.getEventLongLat(event);
			return ResponseEntity.status(HttpStatus.OK).body(eventLongLat);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/mostPopularEvents")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public List<EventDTO> showMostPopularEvents() {
		return eventVisitorsService.getMostPopularEvents();
	}


	@GetMapping("/organizer/{username}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getOrganizerEvents(@PathVariable("username") String username) {
		try {
			Organizer organizer = organizerService.findByUsername(username);
			if(organizer!=null) {
				List<Event> events = eventService.getOrganizerEvents(organizer);
				return ResponseEntity.status(HttpStatus.OK).body(events);
			}
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}

		return ResponseEntity.badRequest().body("Organizator "+ username + " ne postoji.");
	}


	@RequestMapping(value = "/{username}/updateCoverPhoto/{id}", method = RequestMethod.POST)
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> updatePhoto(@PathVariable("id") String id,@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable("username") String username){//, @AuthenticationPrincipal User user){
		try {
				Event event=eventService.fetch(Long.valueOf(id));
				if (event != null) {
					byte[] bytes = multipartFile.getBytes();
					imageService.createImage(new Image(1L,Base64.getEncoder().encodeToString(bytes),null,null,event));

					return ResponseEntity.status(HttpStatus.OK).body("Slika postavljena");}

			} catch (RequestDeniedException | IOException ex) {
				return ResponseEntity.badRequest().body(ex);
			}

		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može mijenjati samo naslovne fotografije svojih dogadaja!"));
	}

	@GetMapping("/image/{id}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getEventImage(@PathVariable("id") String id) {
		try{
			Event event=eventService.fetch(Long.valueOf(id));
			Image im = imageService.findByEvent(event);
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

	@GetMapping("/getEventsCategoryName/{id}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getCategoryNameOfEvent(@PathVariable("id") String id) {
		try {
			Event event = eventService.fetch(Long.valueOf(id));
			EventCategory eventCategory = eventService.getEventCategoryOfEvent(event);
			return ResponseEntity.status(HttpStatus.OK).body(eventCategory.getCategoryName());
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/getOrgNameOfEvent/{id}")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getOrgNameOfEvent(@PathVariable("id") String id) {
		try {
			Event event = eventService.fetch(Long.valueOf(id));
			if(event!=null && event.getOrganizerId()!=null){
				return ResponseEntity.status(HttpStatus.OK).body(event.getOrganizerId().getOrgName());
			}
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Ne postoji taj event!"));

	}
	@GetMapping("/{username}/myEvents")
	@Secured("ROLE_VISITOR")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> showMyFavouriteEvents(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
		try {
			List<EventDTO> eventDTOS = eventVisitorsService.getEventsOfVisitor(visitorService.findByUsername(username));
			return ResponseEntity.status(HttpStatus.OK).body(eventDTOS);
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}


}
