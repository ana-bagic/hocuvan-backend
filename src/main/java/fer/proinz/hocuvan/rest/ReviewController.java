package fer.proinz.hocuvan.rest;

import java.util.Comparator;
import java.util.List;

import fer.proinz.hocuvan.helpers.ReplyDTO;
import fer.proinz.hocuvan.helpers.ReviewDTO;
import fer.proinz.hocuvan.helpers.ReviewWithReplyDTO;
import fer.proinz.hocuvan.service.*;
import fer.proinz.hocuvan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import fer.proinz.hocuvan.domain.Review;

/**
 * The Class ReviewController sends responses to REST requests in JSON format.
 * It is a controller for Review entity.
 * 
 * @author Nina
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

	/** The review service. */
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private EventService eventService;

	@Autowired
	private VisitorService visitorService;


	@Autowired
	private OrganizerService organizerService;

	/**
	 * Lists all reviews when HTTP GET method called.
	 *
	 * @return the list of all reviews
	 */
	@GetMapping("")
	@Transactional
	public List<Review> listReviews() {
		return reviewService.listAll();
	}


	@PostMapping("/{id}/createReview")
	@Secured("ROLE_VISITOR")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> createReview(@RequestBody ReviewDTO reviewDTO, @PathVariable("id") String eventId, @AuthenticationPrincipal User user) {
		try {
			Review review=new Review(reviewDTO.getGrade(),reviewDTO.getText(),visitorService.findByUsername(user.getUsername()),eventService.fetch(Long.valueOf(eventId)));
			review=reviewService.createReview(review);
			return ResponseEntity.status(HttpStatus.OK).body(ReviewDTO.fromReviewToReviewDTO(review));

		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/{id}/getOrganizerReviews")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getAllReviewsOfOrganizer(@PathVariable("id") String organizerId) {
		try {
			List<Review> reviews=reviewService.getOrganizerReviews(organizerService.fetch(Long.valueOf(organizerId)));
			reviews.sort(Comparator.comparing(o -> o.getDate()));
			return ResponseEntity.status(HttpStatus.OK).body(ReviewWithReplyDTO.fromReviewListToReviewWithReplyDTOList(reviews));
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@GetMapping("/{id}/getEventReviews")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> getAllReviewsOfEvent(@PathVariable("id") String eventId) {
		try{
			List<Review> reviews=reviewService.getEventReviews(eventService.fetch(Long.valueOf(eventId)));
			reviews.sort(Comparator.comparing(o -> o.getDate()));
			return ResponseEntity.status(HttpStatus.OK).body(ReviewWithReplyDTO.fromReviewListToReviewWithReplyDTOList(reviews));
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}

	@PostMapping("/{id}/replyToReview")
	@Secured("ROLE_ORGANIZER")
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@Transactional
	public ResponseEntity<Object> replyToReview(@RequestBody ReplyDTO replyDTO, @PathVariable("id") String reviewId,@AuthenticationPrincipal User user) {
		try{
			if(reviewService.fetch(Long.valueOf(reviewId)).getEventId().getOrganizerId().getUsername().equals(user.getUsername())){
				Review review=reviewService.setReply(Long.valueOf(reviewId),replyDTO.getReply());
				review.setReply(replyDTO.getReply());
				return ResponseEntity.status(HttpStatus.OK).body(ReviewWithReplyDTO.fromReviewToReviewDTO(review));
			}
		}catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}
		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može odgovoriti samo na njemu danu recenziju!"));
	}

	@PutMapping("/{id}/updateReview")
	@Secured({"ROLE_VISITOR","ROLE_ADMIN"})
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> updateReview(@RequestBody ReviewDTO reviewReq, @PathVariable("id") String reviewId,@AuthenticationPrincipal User user) {

			try {
				Review review =reviewService.fetch(Long.valueOf(reviewId));
				if(review != null && review.getVisitorId().getUsername().equals(user.getUsername())) {
					Review updatedReview=new Review(Long.valueOf(reviewId), reviewReq.getGrade(),reviewReq.getText(),review.getVisitorId(),review.getEventId());
					updatedReview=reviewService.updateReview(updatedReview);
					return ResponseEntity.status(HttpStatus.OK).body(ReviewWithReplyDTO.fromReviewToReviewDTO(updatedReview));
				}
			} catch (RequestDeniedException ex) {
				ex.printStackTrace();
				return ResponseEntity.badRequest().body(ex);
			}

		return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može mijenjati samo svoje recenzije!"));
	}

	@PutMapping("/{id}/updateReply")
	@Secured({"ROLE_ORGANIZER","ROLE_ADMIN"})
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> updateReply(@RequestBody ReplyDTO replyReq, @PathVariable("id") String reviewId,@AuthenticationPrincipal User user) {

		try {
			if(user.getUsername().equals(reviewService.fetch(Long.valueOf(reviewId)).getEventId().getOrganizerId().getUsername())){
				Review review=reviewService.setReply(Long.valueOf(reviewId),replyReq.getReply());
				review.setReply(replyReq.getReply());
				return ResponseEntity.status(HttpStatus.OK).body(ReviewWithReplyDTO.fromReviewToReviewDTO(review));
			}
		} catch (RequestDeniedException ex) {
			return ResponseEntity.badRequest().body(ex);
		}

		return ResponseEntity.badRequest().body(new RequestDeniedException("Organizator može mijenjati samo svoje odgovore na recenzije!"));
	}

	@DeleteMapping("/{id}/deleteReview")
	@Secured({"ROLE_VISITOR","ROLE_ADMIN"})
	@CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
	@ResponseBody
	@Transactional
	public ResponseEntity<Object> deleteReview( @PathVariable("id") String reviewId, @AuthenticationPrincipal User user) {
		try {
			Review review=reviewService.fetch(Long.valueOf(reviewId));
			//if(review!=null && (user.getUsername().equals(review.getEventId().getOrganizerId().getUsername())
			//	|| user.getUsername().equals("admin"))){
				Review deletedReview=reviewService.deleteReview(review.getReviewId());
				return ResponseEntity.status(HttpStatus.OK).body(ReviewWithReplyDTO.fromReviewToReviewDTO(deletedReview));
			//}
			} catch (RequestDeniedException ex) {
				return ResponseEntity.badRequest().body(ex);
			}

		//return ResponseEntity.badRequest().body(new RequestDeniedException("Posjetitelj može obrisati samo svoj račun!"));
	}
}
