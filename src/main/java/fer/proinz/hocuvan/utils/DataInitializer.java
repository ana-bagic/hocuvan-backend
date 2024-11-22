/*package fer.proinz.hocuvan.utils;

import fer.proinz.hocuvan.domain.*;
import fer.proinz.hocuvan.helpers.EventDTO;
import fer.proinz.hocuvan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;
*/
/**
 * Example component used to insert some test event categories at application startup.
 */
/*@Component
public class DataInitializer {

  @Autowired
  private EventCategoryService eventCategoryService;

  @Autowired
  private VisitorService visitorService;

  @Autowired
  private OrganizerService organizerService;

  @Autowired
  private EventService eventService;

  @Autowired
  private EventVisitorsService eventVisitorsService;

  @Autowired
  private ChatService chatService;

  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    createEventCategories();
    createVisitors();
    createOrganizers();
    createEvents();
    createEventVisitors();
    createChatsAndMessages();
  }

  private void createEvents() {
    EventDTO event=new EventDTO(1L,"Algiers | 07/03/21 VIB", Date.valueOf("2020-12-31"),"Savska cesta 160 10000 Zagreb","Miljenici zagrebačke publike – Algiers, vraćaju se u Zagreb premijerno u Vintage Industrial. Dolaze u sklopu promotivne turneje za posljednji album 'There is no Year, koji je u siječnju objavio Matador.",
            90.00f,100,"vinko","Vintage Industrial Bar","koncert");
    eventService.createEvent(event);
    event=new EventDTO(2L,"Kozmodisko | VIB", Date.valueOf("2020-12-30"),"Savska cesta 160 10000 Zagreb","Ulovi note šarenog disco feelinga u Savskoj 160. Travolta lives, a glazbu biraju disko Marko i Oliver koji vrte retro stvari 70-ih i 80-ih.",
            00.00f,100,"vinko","Vintage Industrial Bar","klub");
    eventService.createEvent(event);
    event=new EventDTO(3L,"The Siids | VIB", Date.valueOf("2020-03-18"),"Savska cesta 160 10000 Zagreb","U srijedu 18/03 iz europske prijestolnice kulture - Rijeke, dovodimo vam THE SIIDS! Jedan od najboljih domaćih live bendova koje možete vidjet! Zato ne propusti priliku za njihov prvi veći samostalan gig u Zagrebu!",
            40.00f,100,"vinko","Vintage Industrial Bar","koncert");
    eventService.createEvent(event);
    event=new EventDTO(4L,"The Siids | VIB", Date.valueOf("2020-12-30"),"Savska cesta 160 10000 Zagreb","U srijedu 18/03 iz europske prijestolnice kulture - Rijeke, dovodimo vam THE SIIDS! Jedan od najboljih domaćih live bendova koje možete vidjet! Zato ne propusti priliku za njihov prvi veći samostalan gig u Zagrebu!",
            40.00f,100,"vinko","Vintage Industrial Bar","koncert");
    eventService.createEvent(event);
    event=new EventDTO(5L,"Kozmodisko | VIB", Date.valueOf("2020-12-30"),"Savska cesta 160 10000 Zagreb","Ulovi note šarenog disco feelinga u Savskoj 160. Travolta lives, a glazbu biraju disko Marko i Oliver koji vrte retro stvari 70-ih i 80-ih.",
            00.00f,100,"vinko","Vintage Industrial Bar","klub");
    eventService.createEvent(event);

  }

  private void createOrganizers() {
    Organizer organizer=new Organizer(1L,"Kset","Zagreb","prankleen","prankleenprankleen","prankleen@gmail.com","098000722");
    organizerService.createOrganizer(organizer);
    organizer=new Organizer(2L,"Vintage Industrial Bar","Zagreb","vinko","vinkovinko","vinko@gmail.com","098000112");
    organizerService.createOrganizer(organizer);
    organizer=new Organizer(3L,"Mocvara","Zagreb","shrek","shrekshrek","shrek@gmail.com","098000700");
    organizerService.createOrganizer(organizer);
    organizer=new Organizer(4L,"Pracka","Zagreb","gospodin","gospodingospodin","gospodin@gmail.com","098000712");
    organizerService.createOrganizer(organizer);
    organizer=new Organizer(5L,"Krivi put","Zagreb","danko","dankodanko","danko@gmail.com","098009999");
    organizerService.createOrganizer(organizer);
  }

  private void createVisitors() {
    Visitor visitor=new Visitor(1L,"Ana","Anic","ana","anaana","ana@gmail.com","098227722");
    visitorService.createVisitor(visitor);
    visitor=new Visitor(2L,"Ivo","Ivic","ivo","ivoivoivo","ivo@gmail.com","098227333");
    visitorService.createVisitor(visitor);
    visitor=new Visitor(3L,"Luka","Lukic","luka","lukaluka","luka@gmail.com","098227555");
    visitorService.createVisitor(visitor);
    visitor=new Visitor(4L,"Hrvoje","Hrvojic","hrvoje","hrvojehrvoje","hrvoje@gmail.com","098227111");
    visitorService.createVisitor(visitor);
    visitor=new Visitor(5L,"Marta","Martic","marta","martamarta","marta@gmail.com","098227000");
    visitorService.createVisitor(visitor);
  }

  private void createEventCategories() {
    EventCategory eventCategory=new EventCategory(1L,"koncert");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(2L,"pub quiz");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(3L,"stand up");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(4L,"klub");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(5L,"panel");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(6L,"festival");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(7L,"događaji na otvorenom");
    eventCategoryService.createEventCategory(eventCategory);
    eventCategory=new EventCategory(8L,"ostalo");
    eventCategoryService.createEventCategory(eventCategory);
  }

  private void createEventVisitors() {
    List<Event> eventList=eventService.listAll();
    eventVisitorsService.save(eventList.get(0),visitorService.findByUsername("ana"));
    eventVisitorsService.save(eventList.get(0),visitorService.findByUsername("ivo"));
    eventVisitorsService.save(eventList.get(0),visitorService.findByUsername("luka"));
    eventVisitorsService.save(eventList.get(0),visitorService.findByUsername("hrvoje"));
    eventVisitorsService.save(eventList.get(0),visitorService.findByUsername("marta"));
    eventVisitorsService.save(eventList.get(1),visitorService.findByUsername("ana"));
    eventVisitorsService.save(eventList.get(1),visitorService.findByUsername("marta"));

  }

  private void createChatsAndMessages(){
    Visitor visitor=visitorService.findByUsername("ana");
    Organizer organizer=organizerService.findByUsername("vinko");
    chatService.save(new Chat(organizer,visitor));
    
    
    organizer=organizerService.findByUsername("prankleen");
    chatService.save(new Chat(organizer,visitor));
    organizer=organizerService.findByUsername("shrek");
    chatService.save(new Chat(organizer,visitor));
    organizer=organizerService.findByUsername("gospodin");
    chatService.save(new Chat(organizer,visitor));
    organizer=organizerService.findByUsername("danko");
    chatService.save(new Chat(organizer,visitor));
  }
}



*/
