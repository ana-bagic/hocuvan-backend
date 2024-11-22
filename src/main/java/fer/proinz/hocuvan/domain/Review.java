package fer.proinz.hocuvan.domain;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class Review represents a review left by visitor after some event that he
 * attended. Review is entity in the database.
 */
@Entity
public class Review {

	/** The review id. */
	@Id
	@GeneratedValue
	//@Column(name = "id_recenzije")
	private Long reviewId;

	/** The grade. */
	//@Column(name = "ocjena")
	private Integer grade;

	/** The text. */
	//@Column(name = "text")
	private String text;

	/** The reply. */
	//@Column(name = "odgovor")
	@Nullable
	private String reply;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date;


	@ManyToOne
	private Visitor visitorId;

	@ManyToOne
	private Event eventId;


	/**
	 * Gets the review id.
	 *
	 * @return the review id
	 */
	public Long getReviewId() {
		return reviewId;
	}

	/**
	 * Sets the review id.
	 *
	 * @param reviewId the new review id
	 */
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	/**
	 * Gets the grade.
	 *
	 * @return the grade
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * Sets the grade.
	 *
	 * @param grade the new grade
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the reply.
	 *
	 * @return the reply
	 */
	public String getReply() {
		return reply;
	}

	/**
	 * Sets the reply.
	 *
	 * @param reply the new reply
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getDate() {
		return date;
	}

	public Event getEventId() {
		return eventId;
	}

	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	public void setDate(Date date) {
		this.date = Calendar.getInstance().getTime();
	}

	public Visitor getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(Visitor visitorId) {
		this.visitorId = visitorId;
	}

	/**
	 * Instantiates a new review.
	 */
	public Review() {
	}

	public Review(Long reviewId, Integer grade, String text,Visitor visitorId,Event eventId) {
		this.reviewId = reviewId;
		this.grade = grade;
		this.text = text;
		this.date= Calendar.getInstance().getTime();
		this.visitorId = visitorId;
		this.eventId = eventId;
	}


	public Review(Integer grade, String text,Visitor visitorId,Event eventId) {
		this.grade = grade;
		this.text = text;
		this.date= Calendar.getInstance().getTime();
		this.visitorId = visitorId;
		this.eventId = eventId;
	}
}
