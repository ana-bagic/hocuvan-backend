package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.domain.VisitorPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorPreferenceRepository extends JpaRepository<VisitorPreference, Long> {
    List<VisitorPreference> getAllByVisitorId(Visitor visitor);

    List<VisitorPreference> deleteAllByVisitorId(Visitor visitor);

    List<VisitorPreference> findAllByVisitorId(Visitor visitor);
}
