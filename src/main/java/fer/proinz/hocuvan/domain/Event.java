package fer.proinz.hocuvan.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * The Class Event represents a general event. Event is entity in the database.
 * 
 * @author Nina
 */
@Entity//(name = "Dogadaj")
public class Event {

	/*@Transient
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private java.util.Date parseDate(String date) {
		try {
			return DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}*/

	/** The event id. */
	@Id
	@GeneratedValue
	//@Column(name = "id_dogadaja")
	private Long eventId;

	/** The event name. */
	//@Column(name = "naziv_dogadaja")
	private String eventName;

	/** The date. */
	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "datum")
	private java.util.Date date;

	/** The location. */
	//@Column(name = "lokacija")
	private String location;

	/** The description */
	@Column(length=999)
	private String description;

	/** The price */
	private Float price;

	/** The number of seats. */
	//@Column(name = "br_mjesta")
	private Integer numberOfSeats;



	/** The organizer id. */
	@ManyToOne
	private Organizer organizerId;

	/** The category id. */
	@ManyToOne
	private EventCategory categoryId;


	@OneToMany(mappedBy = "eventId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<EventVisitors> eventVisitors;

	@OneToMany(mappedBy = "eventId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Review> reviews;

	@OneToMany(mappedBy = "eventId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Notification> notifications;

	@OneToOne(mappedBy ="event")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Image image;

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public Long getEventId() {
		return eventId;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId the new event id
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	/**
	 * Gets the event name.
	 *
	 * @return the event name
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * Sets the event name.
	 *
	 * @param eventName the new event name
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	/**
	 * Gets the organizer id.
	 *
	 * @return the organizer id
	 */

	public Organizer getOrganizerId() {
		return organizerId;
	}

	/**
	 * Sets the organizer id.
	 *
	 * @param organizerId the new organizer id
	 */
	public void setOrganizerId(Organizer organizerId) {
		this.organizerId = organizerId;
	}

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public EventCategory getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId the new category id
	 */
	public void setCategoryId(EventCategory categoryId) {
		this.categoryId = categoryId;
	}

	/**
     * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description.
	 *
	 * @param description the new category id
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}
	/**
	 * Sets the price.
	 *
	 * @param price the new category id
	 */
	public void setPrice(Float price) {
		this.price = price;
	}
	/**
	 * Instantiates a new event.
	 */	public Event() {
	}



	/**
	 * Instantiates a new event.
	 *
	 * @param eventId     the event id
	 * @param eventName   the event name
	 * @param date        the date
	 * @param location    the location
	 * @param organizerId the organizer id
	 * @param categoryId  the category id
	 */
	public Event(Long eventId, String eventName, Date date, String location, String description, Float price, Integer numberOfSeats, Organizer organizerId, EventCategory categoryId) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.date = date;
		this.location = location;
		this.description = description;
		this.price = price;
		this.numberOfSeats = numberOfSeats;
		this.organizerId = organizerId;
		this.categoryId = categoryId;
	}
}
