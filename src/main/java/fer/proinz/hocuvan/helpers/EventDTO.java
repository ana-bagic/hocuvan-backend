package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Event;

import java.util.Date;

public class EventDTO {

    private Long eventId;

    private String eventName;

    private java.util.Date date;

    private String location;

    private String description;

    private float price;

    private int numberOfSeats;

    private String organizerUsername;

    private String orgName;

    private String categoryName;

    public EventDTO() {
    }

    public EventDTO(Long eventId, String eventName, Date date, String location, String description, float price, int numberOfSeats, String organizerUsername,String orgName, String categoryName) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.date = date;
        this.location = location;
        this.description = description;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.organizerUsername = organizerUsername;
        this.categoryName = categoryName;
        this.orgName=orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getOrganizerUsername() {
        return organizerUsername;
    }

    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static EventDTO fromEventToEventDTO(Event event){
        return new EventDTO(event.getEventId(),event.getEventName(),event.getDate(),event.getLocation(),event.getDescription(),event.getPrice(),event.getNumberOfSeats(),event.getOrganizerId().getUsername(),event.getOrganizerId().getOrgName(),event.getCategoryId().getCategoryName());
    }

}
