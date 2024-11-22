package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fer.proinz.hocuvan.domain.Review;

import java.util.List;

/**
 * Interface which communicates with the database and creates entity Review in
 * database.
 * 
 * @author Nina
 *
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByEventId(Event event);

    @Modifying
    @Query("update Review r set r.reply = :reply where r.reviewId = :id")
    void updateReply(@Param(value = "id") Long id, @Param(value = "reply") String reply);


    @Modifying
    @Query("update Review r set r.text = :newText where r.reviewId = :id")
    void updateText(@Param(value = "id") Long id, @Param(value = "newText") String newText);

    @Modifying
    @Query("update Review r set r.grade = :newGrade where r.reviewId = :id")
    void updateGrade(@Param(value = "id") Long id, @Param(value = "newGrade") Integer newGrade);
}
