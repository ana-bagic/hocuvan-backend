package fer.proinz.hocuvan.domain;

import fer.proinz.hocuvan.domain.id.VisitorPreferenceId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(VisitorPreferenceId.class)
public class VisitorPreference {
    @Id
    @ManyToOne
    private Visitor visitorId;
    @Id
    @ManyToOne
    private EventCategory categoryId;

    public VisitorPreference() {
    }

    public VisitorPreference(Visitor visitorId, EventCategory categoryId) {
        this.visitorId = visitorId;
        this.categoryId = categoryId;
    }

    public Visitor getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Visitor visitorId) {
        this.visitorId = visitorId;
    }

    public EventCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(EventCategory categoryId) {
        this.categoryId = categoryId;
    }
}
