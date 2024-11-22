package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReviewWithReplyDTO {
    private Long reviewId;

    private String visitorUsername;

    private String organizerUsername;

    private Integer grade;

    private String text;

    private java.util.Date date;

    private String reply;

    public ReviewWithReplyDTO() {
    }

    public ReviewWithReplyDTO(Long reviewId, String visitorUsername, String organizerUsername, Integer grade, String text, String reply) {
        this.reviewId = reviewId;
        this.visitorUsername = visitorUsername;
        this.organizerUsername=organizerUsername;
        this.grade = grade;
        this.text = text;
        this.date = Calendar.getInstance().getTime();
        this.reply = reply;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getVisitorUsername() {
        return visitorUsername;
    }

    public void setVisitorUsername(String visitorUsername) {
        this.visitorUsername = visitorUsername;
    }

    public String getOrganizerUsername() {
        return organizerUsername;
    }

    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = Calendar.getInstance().getTime();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }


    public static ReviewWithReplyDTO fromReviewToReviewDTO(Review review){
        return new ReviewWithReplyDTO(review.getReviewId(), review.getVisitorId().getUsername(), review.getEventId().getOrganizerId().getUsername(),review.getGrade(),review.getText(),review.getReply());
    }


    public static List<ReviewWithReplyDTO> fromReviewListToReviewWithReplyDTOList(List<Review> reviews) {
        List<ReviewWithReplyDTO> reviewWithReplyDTOS = new ArrayList<>();
        for (Review r : reviews) {
            reviewWithReplyDTOS.add(new ReviewWithReplyDTO(r.getReviewId(), r.getVisitorId().getUsername(),r.getEventId().getOrganizerId().getUsername(),r.getGrade(), r.getText(), r.getReply()));
        }
        return reviewWithReplyDTOS;
    }

}
