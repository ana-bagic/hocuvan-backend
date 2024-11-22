package fer.proinz.hocuvan.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class Visitor represents a visitor of some event. Visitor is entity in
 * the database.
 * 
 * @author Nina
 * 
 */
@Entity
public class Visitor {

	/** The visitor id. */
	@Id
	@GeneratedValue
	//@Column(name = "id_posjetitelja")
	private Long id;

	/** The first name. */
	//@Column(name = "ime_posjetitelja")
	@NotNull
	private String firstName;

	/** The last name. */
	//Column(name = "prezime_posjetitelja")
	@NotNull
	private String lastName;

	/** The username. */
	@Size(min = 1, max = 30)
	@Column(unique = true)//,name = "korisnicko_ime")
	private String username;

	/** The password. */
	@NotNull
	//@Size(min = 5, max = 30)
	//@Column(name = "zaporka")
	private String password;

	/**
	 * e-mail
	 */
	@NotNull
	//@Column(name = "e-mail")
	private String email;

	/**
	 * Phone number
	 */
	@NotNull
	//@Column(name = "br_mobitela")
	private String phoneNumber;




	/** The preference. */
	//@ManyToMany
	//private Set<EventCategory> preference=new HashSet<>();
	@OneToMany(mappedBy = "visitorId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<VisitorPreference> preference;

	@OneToMany(mappedBy = "visitorId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<EventVisitors> eventVisitors;

	@OneToMany(mappedBy = "visitorId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Review> reviews;


	@OneToMany(mappedBy = "visitorId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Notification> notifications;

	@OneToMany(mappedBy = "visitorId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Chat> chats;

	@OneToOne(mappedBy = "visitor")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Image image;

	/**
	 * Instantiates a new visitor.
	 */
	public Visitor() {
	}

	public Visitor(Long visitor_id, String firstName, String lastName, @Size(min = 1, max = 30) String username, @NotNull @Size(min = 5, max = 30) String password, @NotNull String email, @NotNull String phoneNumber) {
		this.id = visitor_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	/*
	public void addEvent(Event event){
		events.add(event);
	}
	public void removeEvent(Event event){
		events.remove(event);
	}*/

	/**
	 * Gets the visitor id.
	 *
	 * @return the visitor id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the visitor id.
	 *
	 * @param visitor_id the new visitor id
	 */
	public void setId(Long visitor_id) {
		this.id = visitor_id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
