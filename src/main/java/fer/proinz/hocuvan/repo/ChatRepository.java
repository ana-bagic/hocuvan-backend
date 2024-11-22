package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Chat;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    //Chat getByVisitorIdAndOrAndOrganizerId(Visitor visitor, Organizer organizer);

    List<Chat> getAllByOrganizerId(Organizer organizer);

    List<Chat> getAllByVisitorId(Visitor visitor);

    boolean existsByVisitorId(Visitor visitor);

    boolean existsByOrganizerId(Organizer organizer);
}
