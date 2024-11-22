package fer.proinz.hocuvan.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Chat {

    @Id
    @GeneratedValue
    private Long chatId;

    @ManyToOne
    private Organizer organizerId;

    @ManyToOne
    private Visitor visitorId;

    @OneToMany(mappedBy = "chatId")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Set<Message> messages;

    public Chat() {
    }

    public Chat(Organizer organizerId, Visitor visitorId) {
        this.organizerId = organizerId;
        this.visitorId = visitorId;
    }

    public Chat(Long chatId, Organizer organizerId, Visitor visitorId) {
        this.chatId = chatId;
        this.organizerId = organizerId;
        this.visitorId = visitorId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Organizer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Organizer organizerId) {
        this.organizerId = organizerId;
    }

    public Visitor getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Visitor visitorId) {
        this.visitorId = visitorId;
    }
}
