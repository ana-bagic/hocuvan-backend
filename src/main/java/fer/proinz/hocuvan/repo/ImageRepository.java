package fer.proinz.hocuvan.repo;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.Image;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByEvent(Event event);
    Image findByVisitor(Visitor visitor);
    Image findByOrganizer(Organizer organizer);


}
