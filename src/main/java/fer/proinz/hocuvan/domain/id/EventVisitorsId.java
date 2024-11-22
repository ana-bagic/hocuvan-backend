package fer.proinz.hocuvan.domain.id;

import java.io.Serializable;
import java.util.Objects;

public class EventVisitorsId implements Serializable {
    private Long eventId;
    private Long visitorId;

    public EventVisitorsId() {
    }

    public EventVisitorsId(Long eventId, Long visitorId) {
        this.eventId = eventId;
        this.visitorId = visitorId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventVisitorsId that = (EventVisitorsId) o;
        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(visitorId, that.visitorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, visitorId);
    }
}
