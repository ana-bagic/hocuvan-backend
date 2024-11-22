package fer.proinz.hocuvan.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class Message represents a message sent between User and Organizer.
 * Message is entity in the database.
 * 
 * @author Nina
 */
@Entity
public class Message {

	/** The message id. */
	@Id
	@GeneratedValue
	//@Column(name = "id_poruke")
	private Long messageId;

	/** The message text. */
	@Column(length=10000)
	private String messageText;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date;

	private String sender;

	@ManyToOne
	private Chat chatId;

	public Message() {
	}

	public Message(String messageText, String sender, Chat chatId) {
		this.messageText = messageText;
		this.date = Calendar.getInstance().getTime();
		this.sender = sender;
		this.chatId = chatId;
	}

	public Message(Long messageId, String messageText, String sender, Chat chatId) {
		this.messageId = messageId;
		this.messageText = messageText;
		this.date = Calendar.getInstance().getTime();
		this.sender = sender;
		this.chatId = chatId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = Calendar.getInstance().getTime();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Chat getChatId() {
		return chatId;
	}

	public void setChatId(Chat chatId) {
		this.chatId = chatId;
	}
}
