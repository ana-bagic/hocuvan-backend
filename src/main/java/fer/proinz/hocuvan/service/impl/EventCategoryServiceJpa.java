package fer.proinz.hocuvan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.repo.EventCategoryRepository;
import fer.proinz.hocuvan.service.EventCategoryService;

/**
 * The Class EventCategoryServiceJpa is an Implementation of
 * EventCategoryService which communicates with repository.
 * 
 * @author Nina
 */
@Service
public class EventCategoryServiceJpa implements EventCategoryService {

	/** Repository of event category service. */
	@Autowired
	private EventCategoryRepository eventCategoryRepository;

	@Override
	public List<EventCategory> listAll() {
		return eventCategoryRepository.findAll();
	}

	@Override
	public EventCategory createEventCategory(EventCategory eventCategory) {
		/**
		 * TODO add asserts here before saving entity to database
		 */

		return eventCategoryRepository.save(eventCategory);

	}

	@Override
	public EventCategory findByName(String name) {
		return eventCategoryRepository.getByCategoryName(name);
	}
}
