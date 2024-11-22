package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fer.proinz.hocuvan.domain.Message;

import java.util.List;

/**
 * Interface which communicates with the database and creates entity Message in
 * database.
 * 
 * @author Nina
 *
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    //List<Message> getAllByVisitorIdAndAndOrganizerId(Visitor visitor, Organizer organizer);

    List<Message> getByChatId(Chat chat);

    boolean existsByChatId(Chat chat);
    /*
    @Query("SELECT DISTINCT m.organizerId, m.visitorId FROM Message m WHERE m.visitorId= :visitorId")
    List<Object> getAllDistinctVisID(@Param ("visitorId") Visitor visitorId);

    @Query("SELECT DISTINCT m.organizerId, m.visitorId FROM Message m WHERE m.organizerId= :organizerId")
    List<Object> getAllDistinctOrgID(@Param ("organizerId") Organizer organizerId);
*/
}
