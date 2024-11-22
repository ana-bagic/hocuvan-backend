package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Event;

public class EventLocationDTA {
    private Double latitude;
    private Double longitude;

    private Event event;

    public EventLocationDTA() {
    }

    public EventLocationDTA(Double latitude, Double longitude, Event event) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
