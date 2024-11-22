package fer.proinz.hocuvan.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.helpers.EventFilterDTO;
import fer.proinz.hocuvan.repo.EventCategoryRepository;
import fer.proinz.hocuvan.repo.OrganizerRepo;
import fer.proinz.hocuvan.service.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.repo.EventRepository;
import fer.proinz.hocuvan.service.EventService;
import org.springframework.util.Assert;

/**
 * The Class EventServiceJpa is an Implementation of EventService which
 * communicates with repository.
 * 
 * @author Nina
 */
@Service
public class EventServiceJpa implements EventService {

	/** Repository of event service. */
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private OrganizerRepo organizerRepo;

	@Autowired
	private EventCategoryRepository eventCategoryRepository;


	@Override
	public List<Event> listAll() {
		Date date = java.util.Calendar.getInstance().getTime();
		Timestamp ts=new Timestamp(date.getTime());
		ts.setHours(0);
		ts.setMinutes(0);
		ts.setSeconds(0);

		return eventRepository.findAllByDateGreaterThanEqual(ts);
	}




	@Override
	public Optional<Event> findById(long id) {
		return eventRepository.findById(id);
	}

	@Override
	public Event createEvent(EventDTO eventDTO) {
		validate(eventDTO);
		Event newEvent=new Event();
		newEvent.setCategoryId(eventCategoryRepository.getByCategoryName(eventDTO.getCategoryName()));
		newEvent.setOrganizerId(organizerRepo.findByUsername(eventDTO.getOrganizerUsername()));
		newEvent.setDate(eventDTO.getDate());
		newEvent.setEventName(eventDTO.getEventName());
		newEvent.setLocation(eventDTO.getLocation());
		newEvent.setNumberOfSeats(eventDTO.getNumberOfSeats());
		newEvent.setPrice(eventDTO.getPrice());
		newEvent.setDescription(eventDTO.getDescription());
		return eventRepository.save(newEvent);
	}

	@Override
	public Event fetch(Long id) {
		return findById(id).orElseThrow(
				() -> new EntityMissingException(Event.class)
		);
	}

	@Override
	public Event updateEvent(Event event) {
		Long id = event.getEventId();
		if (!eventRepository.existsById(id)){
			throw new EntityMissingException(Event.class);
		}

		if(event.getCategoryId()!=null){
			eventRepository.updateCategory(id,event.getCategoryId());
		}
		if(event.getDate()!=null){
			eventRepository.updateDate(id,event.getDate());
		}
		if(event.getDescription()!=null){
			eventRepository.updateDescription(id,event.getDescription());
		}
		if(event.getEventName()!=null){
			eventRepository.updateEventName(id,event.getEventName());
		}
		if(event.getLocation()!=null){
			eventRepository.updateLocation(id,event.getLocation());
		}
		if(event.getPrice()!=null){
			eventRepository.updatePrice(id,event.getPrice());
		}
		if(event.getNumberOfSeats()!=null){
			eventRepository.updateNumberOfSeats(id,event.getNumberOfSeats());
		}

		return fetch(id);
	}

	@Override
	public Event deleteEvent(Long id) {
		Event event = fetch(id);
		eventRepository.delete(event);
		return event;
	}

	@Override
	public List<Event> filterEvents(EventFilterDTO eventFilterDTO) throws ParseException {
		List<Event> resultList=eventRepository.findAll();
		List<Event> tmpList=new ArrayList<>();
		boolean none=true;

		if(eventFilterDTO.getEventName()==null || eventFilterDTO.getEventName()==""){
		}else{
			none=false;
			tmpList=eventRepository.findAllByEventName(eventFilterDTO.getEventName());
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}

		if(eventFilterDTO.getDate()==null || String.valueOf(eventFilterDTO.getDate())==""){
			tmpList=listAll();
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}else{
			none=false;
			//PRIVREMENO RJESENJE
			Date date =eventFilterDTO.getDate();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date todayWithZeroTime = formatter.parse(formatter.format(date));
			tmpList=eventRepository.findAll();

			if(tmpList!=null && !tmpList.isEmpty()){
				List<Event> eventList = new ArrayList<>();
				for(Event e : tmpList){
					System.out.println(e.getDate());
					System.out.println(formatter.parse(formatter.format(e.getDate())));
					if(formatter.parse(formatter.format(e.getDate())).equals(todayWithZeroTime)){
						if(resultList.contains(e)){
							eventList.add(e);
						}
					}
				}
				resultList=eventList;
			}

			//Timestamp ts=new Timestamp(eventFilterDTO.getDate().getTime());
			/*ts.setHours(0);
			ts.setMinutes(0);
			ts.setSeconds(0);*/
			/*tmpList=eventRepository.findAllByDate(ts);
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);*/
		}

		if(eventFilterDTO.getLocation()==null || eventFilterDTO.getLocation()==""){
		}else{
			none=false;
			tmpList=eventRepository.findAllByLocation(eventFilterDTO.getLocation());
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}

		if(eventFilterDTO.getPrice()==null || String.valueOf(eventFilterDTO.getPrice())==""){
		}else{
			none=false;
			tmpList=(eventRepository.findAllByPrice(eventFilterDTO.getPrice()));
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}

		if(eventFilterDTO.getNumberOfSeats()==null || String.valueOf(eventFilterDTO.getNumberOfSeats())==""){ }
		else{
			none=false;
			tmpList=eventRepository.findAllByNumberOfSeats(eventFilterDTO.getNumberOfSeats());
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}

		if(eventFilterDTO.getOrganisationName()==null || eventFilterDTO.getOrganisationName()==""){ }
		else{
			none=false;
			List<Organizer> orgs=organizerRepo.findAllByOrgName(eventFilterDTO.getOrganisationName());
			for(Organizer o : orgs){
				tmpList.addAll(eventRepository.findAllByOrganizerId(o));
			}
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}

		if(eventFilterDTO.getOrganisationHeadquarter()==null || eventFilterDTO.getOrganisationHeadquarter()==""){ }
		else{
			none=false;
			List<Organizer> orgs=organizerRepo.findAllByHeadquarters(eventFilterDTO.getOrganisationHeadquarter());
			for(Organizer o : orgs){
				tmpList.addAll(eventRepository.findAllByOrganizerId(o));
			}
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}

		if(eventFilterDTO.getCategoryName()==null || eventFilterDTO.getCategoryName()==""){
		}else{
			none=false;
			EventCategory category=eventCategoryRepository.getByCategoryName(eventFilterDTO.getCategoryName());
			tmpList=eventRepository.findAllByCategoryId(category);
			if(tmpList!=null && !tmpList.isEmpty())
				resultList=checkLists(resultList,tmpList);
		}
		if (none == true){
			return new ArrayList<>();
		}
		return resultList;
	}

	private List<Event> checkLists(List<Event> resultList, List<Event> tmpList) {
		List<Event> list=new ArrayList<>();
		for(Event e : tmpList){
			if(resultList.contains(e)){
				list.add(e);
			}
		}
		return list;
	}

	@Override
	public long countAll() {
		return eventRepository.count();
	}

	@Override
	public List<Event> getOrganizerEvents(Organizer organizer) {
		List<Event> events = eventRepository.findAllByOrganizerId(organizer);
		return events;
	}

	@Override
	public EventCategory getEventCategoryOfEvent(Event event) {
		if(event != null &&eventRepository.existsById(event.getEventId()) && event.getCategoryId()!=null){
			EventCategory eventCategory = event.getCategoryId();
			return eventCategory;
		}else {
			throw new EntityMissingException(EventCategory.class);
		}
	}


	private void validate(EventDTO event) {
		Assert.notNull(event, "Event object must be given");
		Assert.notNull(organizerRepo.findByUsername(event.getOrganizerUsername()),"Organizer object must be given");
		Assert.notNull(eventCategoryRepository.getByCategoryName(event.getCategoryName()),"Event category must be given");
		Assert.notNull(event.getEventName(), "Name must be given");
		Assert.notNull(event.getDate(), "Date must be given");
		Assert.notNull(event.getLocation(), "Location must be given");
	}
}
