package fer.proinz.hocuvan.service.impl;

import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.domain.Image;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.repo.ImageRepository;
import fer.proinz.hocuvan.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceJpa  implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image createImage(Image image) {

        return imageRepository.save(image);
    }


    @Override
    public Image findByVisitor(Visitor visitor) {
        //Assert.notNull(username, "Korisničko ime je prazno");
        return imageRepository.findByVisitor(visitor);
    }

    @Override
    public Image findByOrganizer(Organizer organizer) {
        return imageRepository.findByOrganizer(organizer);
    }

    @Override
    public Image findByEvent(Event event) {
        return imageRepository.findByEvent(event);
    }
}
