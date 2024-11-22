package fer.proinz.hocuvan.service;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.helpers.VisitorDTO;

import java.util.List;

public interface EventVisitorsService {

    void save(Event event, Visitor visitor);

    void remove(Event event, Visitor visitor);

    List<EventDTO> getEventsOfVisitor(Visitor visitor);

    List<VisitorDTO> getVisitorsOfEvent(Event event);

    List<EventDTO> getMostPopularEvents();
}
