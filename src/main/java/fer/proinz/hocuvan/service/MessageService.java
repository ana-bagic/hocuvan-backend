package fer.proinz.hocuvan.service;

import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Chat;
import fer.proinz.hocuvan.domain.Message;
import fer.proinz.hocuvan.domain.*;
import fer.proinz.hocuvan.helpers.LastMessageDTO;

/**
 * The Interface MessageService is logic between controller and repository for
 * Message entity.
 * 
 * @author Nina
 */
public interface MessageService {

	/**
	 * Lists all messages from database.
	 *
	 * @return the list of all messages
	 */
	List<Message> listAll();

	/**
	 * Creates new message.
	 *
	 * @param event the event to be added to database
	 * @return new message
	 */
	Message createMessage(Message event);

	/*List<Message> getAllByVisitorIdAndAndOrganizerId(Visitor visitor, Organizer organizer);

	List<ChatDTO> getAllChatsVisitor(Visitor visitor);

	List<ChatDTO> getAllChatsOrganizer(Organizer organizer);*/

	List<Message> getAllByChat(Chat chat);

	Optional<Message> findById(long id);

	Message fetch(long id);

	LastMessageDTO getLastMessage(Chat chat);

}
