package fer.proinz.hocuvan.domain.id;

import java.io.Serializable;
import java.util.Objects;

public class VisitorPreferenceId implements Serializable {
    private Long visitorId;
    private Long categoryId;

    public VisitorPreferenceId() {
    }

    public VisitorPreferenceId(Long visitorId, Long categoryId) {
        this.visitorId = visitorId;
        this.categoryId = categoryId;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorPreferenceId that = (VisitorPreferenceId) o;
        return Objects.equals(visitorId, that.visitorId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorId, categoryId);
    }
}
