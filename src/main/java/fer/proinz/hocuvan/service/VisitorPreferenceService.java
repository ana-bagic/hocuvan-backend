package fer.proinz.hocuvan.service;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.domain.VisitorPreference;

import java.util.List;

public interface VisitorPreferenceService {
    List<Event> getEventsOfFavouriteCategories(Visitor visitor);

    void save(EventCategory category, Visitor visitor);

    List<VisitorPreference> deleteAllByVisitor(Visitor visitor);

    List<VisitorPreference> findAllByVisitor(Visitor visitor);
}
