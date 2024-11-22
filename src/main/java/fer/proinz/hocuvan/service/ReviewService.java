package fer.proinz.hocuvan.service;

import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Review;

/**
 * The Interface ReviewService is logic between controller and repository for
 * Review entity.
 * 
 * @author Nina
 */
public interface ReviewService {

	/**
	 * List s all reviews from database.
	 *
	 * @return the list of all reviews
	 */
	List<Review> listAll();

	/**
	 * Creates new review.
	 *
	 * @param review the review to be added to database
	 * @return new review
	 */
	Review createReview(Review review);

	List<Review> getOrganizerReviews(Organizer organizer);

	List<Review> getEventReviews(Event event);

	Optional<Review> findById(long id);

	Review fetch(long id);

	Review setReply(Long replyId, String reply);

	Review updateReview(Review review);

    Review deleteReview(Long reviewId);
}
