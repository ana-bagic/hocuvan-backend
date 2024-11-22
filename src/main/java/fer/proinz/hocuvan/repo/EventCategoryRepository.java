package fer.proinz.hocuvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fer.proinz.hocuvan.domain.EventCategory;

/**
 * Interface which communicates with the database and creates entity
 * EventCategory in database.
 * 
 * @author Nina
 *
 */
@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {

    EventCategory getByCategoryId(Long id);

    EventCategory getByCategoryName(String categoryName);
}
