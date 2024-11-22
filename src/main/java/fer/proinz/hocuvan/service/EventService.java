package fer.proinz.hocuvan.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.helpers.EventFilterDTO;

/**
 * The Interface EventService is logic between controller and repository for
 * Event entity.
 * 
 * @author Nina
 */
public interface EventService {

	/**
	 * Lists all events from database.
	 *
	 * @return the list of all events
	 */
	List<Event> listAll();

	/**
	 * Creates new event.
	 *
	 * @param event the event to be added to database
	 * @return new event
	 */
	Event createEvent(EventDTO event);

	Optional<Event> findById(long id);

	Event fetch(Long id);

	Event updateEvent(Event event);

	Event deleteEvent(Long id);

	List<Event> filterEvents(EventFilterDTO eventFilterDTO) throws ParseException;

	long countAll();


	List<Event> getOrganizerEvents(Organizer organizer);


	EventCategory getEventCategoryOfEvent(Event event);
}
