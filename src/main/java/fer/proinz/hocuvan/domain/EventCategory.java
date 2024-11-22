package fer.proinz.hocuvan.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The class EventCategory represents a category of some event.
 * EventCategory is entity in the database.
 * 
 * @author Nina
 */
@Entity//(name = "Kategorija_Dogadaja")
public class EventCategory {

	/** The category id. */
	@Id
	@GeneratedValue
	//@Column(name = "id_kategorije")
	private Long categoryId;

	/** The category name. */
	//@Column(name = "naziv_kategorije")
	private String categoryName;

	//@ManyToMany(mappedBy="preference")
	//private Set<Visitor> visitors=new HashSet<>();
	@OneToMany(mappedBy = "categoryId")
	private Set<VisitorPreference> visitors;

	@OneToMany(mappedBy = "categoryId")
	private Set<Event> events=new HashSet<>();
	
	/**
	 * Instantiates a new event category.
	 *
	 * @param categoryId   the category id
	 * @param categoryName the category name
	 */
	public EventCategory(Long categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
	/**
	 * Instantiates a new event category.
	 */
	public EventCategory() {
	}

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId the new category id
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Gets the category name.
	 *
	 * @return the category name
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the category name.
	 *
	 * @param categoryName the new category name
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
