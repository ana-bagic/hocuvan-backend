package fer.proinz.hocuvan.helpers;

public class ChatNewDTO {

    private String organizer;

    private String visitor;

    public ChatNewDTO() {
    }

    public ChatNewDTO(String organizer, String visitor) {
        this.organizer = organizer;
        this.visitor = visitor;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }
}
