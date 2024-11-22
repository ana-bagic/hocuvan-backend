package fer.proinz.hocuvan.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The Class Organizer represents an organizer of some event. Organizer is
 * entity in the database.
 * 
 * @author Nina
 */
@Entity//(name = "Organizator")
public class Organizer {

	/** The organizer id. */
	@Id
	@GeneratedValue
	//@Column(name = "id_organizatora")
	private Long id;

	/** The organisation name. */
	//@Column(name = "naziv_organizacije")
	@NotNull
	private String orgName;

	/** The headquarters. */
	//@Column(name = "sjediste")
	@NotNull
	private String headquarters;

	/** The username. */
	@Size(min = 1, max = 30)
	@Column(unique = true)//, name = "korisnicko_ime")
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


	@OneToMany(mappedBy = "organizerId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Event> events;

	@OneToMany(mappedBy = "organizerId")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Set<Chat> chats;

	@OneToOne(mappedBy ="organizer")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Image image;

	/**
	 * Instantiates a new organizer.
	 */
	public Organizer() {
	}

	public Organizer(Long organizer_id, String orgName, String headquarters, @Size(min = 1, max = 30) String username, @NotNull @Size(min = 5, max = 30) String password, @NotNull String email, @NotNull String phoneNumber) {
		this.id = organizer_id;
		this.orgName = orgName;
		this.headquarters = headquarters;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;

	}


	/**
	 * Gets the organizer id.
	 *
	 * @return the organizer id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the organizer id.
	 *
	 * @param organizer_id the new organizer id
	 */
	public void setId(Long organizer_id) {
		this.id = organizer_id;
	}

	/**
	 * Gets the organisation name.
	 *
	 * @return the organisation name
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * Sets the organisation name.
	 *
	 * @param orgName the new organisation name
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * Gets the headquarters.
	 *
	 * @return the headquarters
	 */
	public String getHeadquarters() {
		return headquarters;
	}

	/**
	 * Sets the headquarters.
	 *
	 * @param headquarters the new headquarters
	 */
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
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
		this.password = password;
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
