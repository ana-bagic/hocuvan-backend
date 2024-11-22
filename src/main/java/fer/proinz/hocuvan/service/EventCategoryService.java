package fer.proinz.hocuvan.service;

import java.util.List;

import fer.proinz.hocuvan.domain.EventCategory;

/**
 * The Interface EventCategoryService is logic between controller and repository
 * for EventCategory entity.
 * 
 * @author Nina
 */
public interface EventCategoryService {

	/**
	 * List all event categories from database.
	 *
	 * @return the list of all event categories
	 */
	List<EventCategory> listAll();

	/**
	 * Creates new event category.
	 *
	 * @param eventCategory the event category to be added to database
	 * @return new event category
	 */
	EventCategory createEventCategory(EventCategory eventCategory);


	EventCategory findByName(String name);
}
