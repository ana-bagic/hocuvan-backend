package fer.proinz.hocuvan.helpers;

public class LastMessageDTO {
    String text;

    public LastMessageDTO() {
    }

    public LastMessageDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
