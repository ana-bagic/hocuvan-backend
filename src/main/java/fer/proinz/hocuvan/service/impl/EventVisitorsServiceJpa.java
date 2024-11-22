package fer.proinz.hocuvan.service.impl;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.EventVisitors;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.helpers.VisitorDTO;
import fer.proinz.hocuvan.repo.EventRepository;
import fer.proinz.hocuvan.repo.EventVisitorsRepository;
import fer.proinz.hocuvan.service.EventVisitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventVisitorsServiceJpa implements EventVisitorsService {

    @Autowired
    EventVisitorsRepository eventVisitorsRepository;

    @Autowired
    EventRepository eventRepository;

    @Override
    public void save(Event event, Visitor visitor) {
        EventVisitors eventVisitors=new EventVisitors(event,visitor);
        eventVisitorsRepository.save(eventVisitors);
    }

    @Override
    public void remove(Event event, Visitor visitor) {
        EventVisitors eventVisitors=new EventVisitors(event,visitor);
        eventVisitorsRepository.delete(eventVisitors);

    }

    @Override
    public List<EventDTO> getEventsOfVisitor(Visitor visitor) {
        List<EventDTO> events=new ArrayList<>();
        List<EventVisitors> eventVisitors=eventVisitorsRepository.getAllByVisitorId(visitor);
        for(EventVisitors ev : eventVisitors){
            events.add(EventDTO.fromEventToEventDTO(ev.getEventId()));
        }
        return events;
    }

    @Override
    public List<VisitorDTO> getVisitorsOfEvent(Event event) {
        List<VisitorDTO> visitors=new ArrayList<>();
        List<EventVisitors> eventVisitors=eventVisitorsRepository.getAllByEventId(event);
        for(EventVisitors ev : eventVisitors){
            visitors.add(VisitorDTO.fromVisitorToVisitorDTO(ev.getVisitorId()));
        }
        return visitors;
    }

    @Override
    public List<EventDTO> getMostPopularEvents() {
        Map<Long,Integer> idNumVisitorsMap=new HashMap<>();
        List<EventVisitors> eventVisitors =eventVisitorsRepository.findAll();
        for(EventVisitors ev : eventVisitors){
            if(idNumVisitorsMap.containsKey(ev.getEventId().getEventId())){
            }else{
                idNumVisitorsMap.put(ev.getEventId().getEventId(), eventVisitorsRepository.countByEventId(ev.getEventId()));
            }
        }

        LinkedHashMap<Long, Integer> reverseSortedMap = new LinkedHashMap<>();

        idNumVisitorsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        List<EventDTO> mostPopularEvents=new ArrayList<>();
        int cnt=0;
        for(Long key : reverseSortedMap.keySet()){
            if(cnt<6){
                mostPopularEvents.add(EventDTO.fromEventToEventDTO(eventRepository.findByEventId(key)));
            }
            else {
                break;
            }
            cnt++;
        }
        
        return mostPopularEvents;

    }


}
