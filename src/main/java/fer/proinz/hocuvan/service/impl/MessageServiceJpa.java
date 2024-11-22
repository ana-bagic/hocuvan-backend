package fer.proinz.hocuvan.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Chat;
import fer.proinz.hocuvan.domain.Message;
import fer.proinz.hocuvan.domain.*;
import fer.proinz.hocuvan.helpers.LastMessageDTO;
import fer.proinz.hocuvan.service.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.proinz.hocuvan.repo.MessageRepository;
import fer.proinz.hocuvan.service.MessageService;

/**
 * The Class MessageServiceJpa is an Implementation of MessageService which
 * communicates with repository.
 * 
 * @author Nina
 */
@Service
public class MessageServiceJpa implements MessageService {

	/** Repository of message service. */
	@Autowired
	private MessageRepository messageRepository;

	@Override
	public List<Message> listAll() {
		return messageRepository.findAll();
	}

	@Override
	public Message createMessage(Message message) {
		/**
		 * TODO add asserts here before saving entity to database
		 */

		return messageRepository.save(message);
	}

	@Override
	public List<Message> getAllByChat(Chat chat) {
		if(chat!=null && messageRepository.existsByChatId(chat)){
			List<Message> messages=messageRepository.getByChatId(chat);
			messages.sort(Comparator.comparing(Message::getDate));
			return messages;
		}else {
			return null;
		}
	}

	@Override
	public Message fetch(long id) {
		return findById(id).orElseThrow(
				() -> new EntityMissingException(Message.class)
		);
	}

	@Override
	public Optional<Message> findById(long id) {
		return messageRepository.findById(id);
	}

	@Override
	public LastMessageDTO getLastMessage(Chat chat) {
		List<Message> messages = getAllByChat(chat);
		if(messages!=null){
			int size = messages.size();
			Message lastMessage=messages.get(size-1);
			return new LastMessageDTO(lastMessage.getMessageText());
		}else {
			return null;
		}
	}

	/*@Override
	public List<Message> getAllByVisitorIdAndAndOrganizerId(Visitor visitor, Organizer organizer) {
		return null;//messageRepository.getAllByVisitorIdAndAndOrganizerId(visitor,organizer);
	}*/

	/*
	@Override
	public List<ChatDTO> getAllChatsVisitor(Visitor visitor) {
		List<Object> tmp=messageRepository.getAllDistinctVisID(visitor);
		List<ChatDTO> chats=new ArrayList<>();
		for (Object o : tmp){
			Object[] obj=(Object[]) o;
			Organizer org=(Organizer) obj[0];
			Visitor vis=(Visitor) obj[1];
			chats.add(new ChatDTO(org,vis));
		}
		return null;//chats;
	}*/

	/*
	@Override
	public List<ChatDTO> getAllChatsOrganizer(Organizer organizer) {
		List<Object> tmp=messageRepository.getAllDistinctOrgID(organizer);
		List<ChatDTO> chats=new ArrayList<>();
		for (Object o : tmp){
			Object[] obj=(Object[]) o;
			Organizer org=(Organizer) obj[0];
			Visitor vis=(Visitor) obj[1];
			chats.add(new ChatDTO(org,vis));
		}
		return null;//chats;
	}*/
}
