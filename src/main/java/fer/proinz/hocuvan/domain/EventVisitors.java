package fer.proinz.hocuvan.domain;

import fer.proinz.hocuvan.domain.id.EventVisitorsId;

import javax.persistence.*;

@Entity
@IdClass(EventVisitorsId.class)
public class EventVisitors {
   /* @Id
    @GeneratedValue
    private Long id;*/

    @Id
    @ManyToOne
    private Event eventId;

    @Id
    @ManyToOne
    private Visitor visitorId;

    public EventVisitors() {
    }

    public EventVisitors(Event eventId, Visitor visitorId) {
        this.eventId = eventId;
        this.visitorId = visitorId;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Visitor getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Visitor visitorId) {
        this.visitorId = visitorId;
    }
}
