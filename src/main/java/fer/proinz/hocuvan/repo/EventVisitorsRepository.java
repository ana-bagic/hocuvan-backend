package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.EventVisitors;
import fer.proinz.hocuvan.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventVisitorsRepository extends JpaRepository<EventVisitors, Long> {
    List<EventVisitors> getAllByEventId(Event event);
    List<EventVisitors> getAllByVisitorId(Visitor visitor);

    int countByEventId(Event event);
}
