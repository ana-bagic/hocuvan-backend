package fer.proinz.hocuvan.service.impl;

import fer.proinz.hocuvan.domain.Chat;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.repo.ChatRepository;
import fer.proinz.hocuvan.repo.OrganizerRepo;
import fer.proinz.hocuvan.repo.VisitorRepo;
import fer.proinz.hocuvan.service.ChatService;
import fer.proinz.hocuvan.service.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceJpa implements ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    VisitorRepo visitorRepo;

    @Autowired
    OrganizerRepo organizerRepo;

    /*@Override
    public Chat getByVisitorAndOrganizer(Visitor visitor, Organizer organizer) {
        if(visitor!=null && organizer!=null && organizerRepo.existsById(organizer.getId()) && visitorRepo.existsById(visitor.getId())){
            Chat chat = chatRepository.getByVisitorIdAndOrAndOrganizerId(visitor,organizer);
            return chat;
        }else {
            return null;
        }
    }*/

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> findById(long id) {
        return chatRepository.findById(id);
    }

    @Override
    public Chat fetch(long id) {
        return findById(id).orElseThrow(
                () -> new EntityMissingException(Chat.class)
        );
    }

    @Override
    public List<Chat> getAllByVisitor(Visitor visitor) {
        if(visitor!=null && chatRepository.existsByVisitorId(visitor)){
            List<Chat> chats =chatRepository.getAllByVisitorId(visitor);
            return chats;
        }
        return null;
    }

    @Override
    public List<Chat> getAllByOrganizer(Organizer organizer) {
        if(organizer!=null && chatRepository.existsByOrganizerId(organizer)){
            List<Chat> chats =chatRepository.getAllByOrganizerId(organizer);
            return chats;
        }
        return null;
    }
}
