package fer.proinz.hocuvan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.repo.EventRepository;
import fer.proinz.hocuvan.service.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.proinz.hocuvan.domain.Review;
import fer.proinz.hocuvan.repo.ReviewRepository;
import fer.proinz.hocuvan.service.ReviewService;

/**
 * The Class ReviewServiceJpa is an Implementation of ReviewService which
 * communicates with repository.
 * 
 * @author Nina
 */
@Service
public class ReviewServiceJpa implements ReviewService {

	/** Repository of review service. */
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private EventRepository eventRepository;

	@Override
	public List<Review> listAll() {
		return reviewRepository.findAll();
	}

	@Override
	public Review createReview(Review review) {
		/**
		 * TODO add asserts here before saving entity to database
		 */

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getOrganizerReviews(Organizer organizer) {
		List<Event> events=eventRepository.findAllByOrganizerId(organizer);
		List<Review> reviews=new ArrayList<>();
		for(Event e : events){
			reviews.addAll(reviewRepository.findAllByEventId(e));
		}
		return reviews;
	}

	@Override
	public List<Review> getEventReviews(Event event) {
		return reviewRepository.findAllByEventId(event);
	}

	@Override
	public Optional<Review> findById(long id) {
		return reviewRepository.findById(id);
	}

	@Override
	public Review fetch(long id) {
		return findById(id).orElseThrow(
				() -> new EntityMissingException(Review.class)
		);
	}

	@Override
	public Review setReply(Long replyId, String reply) {
		reviewRepository.updateReply(replyId,reply);
		return fetch(replyId);
	}

	@Override
	public Review updateReview(Review review) {
		Long id=review.getReviewId();
		if(!reviewRepository.existsById(id)){
			throw new EntityMissingException(Review.class);
		}

		if(review.getText()!=null){
			reviewRepository.updateText(id,review.getText());
		}
		if(review.getGrade()!=null){
			reviewRepository.updateGrade(id,review.getGrade());
		}
		return fetch(id);
	}

	@Override
	public Review deleteReview(Long reviewId) {
		Review review=fetch(reviewId);
		reviewRepository.delete(review);
		return review;
	}


}
