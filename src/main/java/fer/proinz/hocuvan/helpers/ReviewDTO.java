package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Review;

import java.util.Calendar;
import java.util.Date;

public class ReviewDTO {

    private Long reviewId;

    private String username;

    private Integer grade;

    private String text;

    private java.util.Date date;

    public ReviewDTO() {
    }

    public ReviewDTO(Long reviewId, String username, Integer grade, String text) {
        this.reviewId = reviewId;
        this.username = username;
        this.grade = grade;
        this.text = text;
        this.date = Calendar.getInstance().getTime();
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setDate() {
        this.date = Calendar.getInstance().getTime();
    }

    public static ReviewDTO fromReviewToReviewDTO(Review review){
        return new ReviewDTO(review.getReviewId(), review.getVisitorId().getUsername(), review.getGrade(),review.getText());
    }
}
