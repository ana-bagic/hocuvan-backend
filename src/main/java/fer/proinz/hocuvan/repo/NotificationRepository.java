package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface which communicates with the database and creates entity
 * Notification in database.
 *
 * @author Nika
 *
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
