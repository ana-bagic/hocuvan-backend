package fer.proinz.hocuvan.service;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.Image;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;

public interface ImageService {

    Image createImage(Image image);

    Image findByVisitor(Visitor visitor);

    Image findByOrganizer(Organizer organizer);

    Image findByEvent(Event event);

}
