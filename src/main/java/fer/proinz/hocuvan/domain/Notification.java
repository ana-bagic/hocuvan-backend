package fer.proinz.hocuvan.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue
    private Long notificationId;

    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date date;


    @ManyToOne
    private Event eventId;

    @ManyToOne
    private Visitor visitorId;

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

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Notification() {

    }
    public Notification(Long notificationId, String text, Date date,Visitor visitorId, Event eventId) {
        this.notificationId = notificationId;
        this.text = text;
        this.date = date;
        this.visitorId = visitorId;
        this.eventId = eventId;
    }
}
