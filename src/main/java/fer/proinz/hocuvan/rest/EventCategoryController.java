package fer.proinz.hocuvan.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.service.EventCategoryService;

/**
 * The Class EventCategoryController sends responses to REST requests in JSON
 * format. It is a controller for EventCategory entity.
 * 
 * @author Nina
 */
@RestController
@RequestMapping("/eventcategories")
public class EventCategoryController {

	/** The event category service. */
	@Autowired
	private EventCategoryService eventCategoryService;

	/**
	 * Lists all event categories when HTTP GET method called.
	 *
	 * @return the list of all event categories
	 */
	@GetMapping("")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public List<EventCategory> listEventCategories() {
		return eventCategoryService.listAll();
	}

	/**
	 * Creates new event category entity when HTTP POST method called.
	 *
	 * @param eventCategory the event category given in JSON format
	 * @return the new created event category
	 */
	@PostMapping("")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public EventCategory createEventCategory(@RequestBody EventCategory eventCategory) {
		return eventCategoryService.createEventCategory(eventCategory);
	}
}
