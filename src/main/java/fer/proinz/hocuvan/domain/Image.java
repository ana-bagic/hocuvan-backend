package fer.proinz.hocuvan.domain;

import com.sun.istack.Nullable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long imageId;

    @Lob
    @Nullable
    private String image;

    @Nullable
    @OneToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Visitor visitor;

    @Nullable
    @OneToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Organizer organizer;

    @Nullable
    @OneToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Event event;

    public Image(Long imageId, String image, Visitor visitor, Organizer organizer, Event event) {
        this.imageId = imageId;
        this.image = image;
        this.visitor = visitor;
        this.organizer = organizer;
        this.event = event;
    }

    public Image() {
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
