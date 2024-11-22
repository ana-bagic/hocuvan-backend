package fer.proinz.hocuvan.helpers;

import java.util.Date;

public class EventFilterDTO {
    private String eventName;

    private java.util.Date date;

    private String location;

    private Float price;

    private Integer numberOfSeats;

    private String organisationName;

    private String organisationHeadquarter;

    private String categoryName;

    public EventFilterDTO() {
    }

    public EventFilterDTO(String eventName, Date date, String location, Float price, Integer numberOfSeats, String organisationName, String organisationHeadquarter, String categoryName) {
        this.eventName = eventName;
        this.date = date;
        this.location = location;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.organisationName = organisationName;
        this.organisationHeadquarter = organisationHeadquarter;
        this.categoryName = categoryName;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrganisationHeadquarter() {
        return organisationHeadquarter;
    }

    public void setOrganisationHeadquarter(String organisationHeadquarter) {
        this.organisationHeadquarter = organisationHeadquarter;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
