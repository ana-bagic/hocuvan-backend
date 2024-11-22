package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.EventCategory;
import fer.proinz.hocuvan.domain.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fer.proinz.hocuvan.domain.Event;

import java.util.Date;
import java.util.List;

/**
 * Interface which communicates with the database and creates entity Event in
 * database.
 * 
 * @author Nina
 *
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventName(String eventName);
    List<Event> findAllByCategoryId(EventCategory categoryId);
    List<Event> findAllByDate(Date date);
    List<Event> findAllByLocation(String location);
    List<Event> findAllByPrice(float price);
    List<Event> findAllByNumberOfSeats(int numberOfSeats);
    List<Event> findAllByOrganizerId(Organizer organizerId);

    Event findByEventId(Long id);

    List<Event> findAllByDateGreaterThanEqual(Date date);
    List<Event> findAllByDateEquals(Date date);

    @Modifying
    @Query("update Event e set e.eventName = :newEventName where e.eventId = :id")
    void updateEventName(@Param(value = "id") Long id, @Param(value = "newEventName") String newEventName);

    @Modifying
    @Query("update Event e set e.date = :newEventDate where e.eventId = :id")
    void updateDate(@Param(value = "id") Long id, @Param(value = "newEventDate") Date newEventDate);

    @Modifying
    @Query("update Event e set e.description = :newDescription where e.eventId = :id")
    void updateDescription(@Param(value = "id") Long id, @Param(value = "newDescription") String newDescription);

    @Modifying
    @Query("update Event e set e.location = :newLocation where e.eventId = :id")
    void updateLocation(@Param(value = "id") Long id, @Param(value = "newLocation") String newLocation);

    @Modifying
    @Query("update Event e set e.numberOfSeats = :newNumberOfSeats where e.eventId = :id")
    void updateEmail(@Param(value = "id") Long id, @Param(value = "newNumberOfSeats") int newNumberOfSeats);

    @Modifying
    @Query("update Event e set e.price= :newPrice where e.eventId = :id")
    void updatePrice(@Param(value = "id") Long id, @Param(value = "newPrice") Float newPrice);

    @Modifying
    @Query("update Event e set e.categoryId = :newCategory where e.eventId = :id")
    void updateCategory(@Param(value = "id") Long id, @Param(value = "newCategory") EventCategory newCategory);

    @Modifying
    @Query("update Event e set e.numberOfSeats = :newNumberOfSeats where e.eventId = :id")
    void updateNumberOfSeats(@Param(value = "id") Long id, @Param(value = "newNumberOfSeats") Integer newNumberOfSeats);


}
