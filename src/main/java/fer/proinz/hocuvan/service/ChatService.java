package fer.proinz.hocuvan.service;

import fer.proinz.hocuvan.domain.Chat;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    //Chat getByVisitorAndOrganizer(Visitor visitor, Organizer organizer);

    Chat save(Chat chat);

    Optional<Chat> findById(long id);

    Chat fetch(long id);

    List<Chat> getAllByVisitor(Visitor visitor);

    List<Chat> getAllByOrganizer(Organizer organizer);
}
