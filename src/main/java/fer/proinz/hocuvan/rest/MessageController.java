package fer.proinz.hocuvan.rest;

import fer.proinz.hocuvan.domain.Chat;
import fer.proinz.hocuvan.domain.Message;
import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.helpers.ChatDTO;
import fer.proinz.hocuvan.helpers.ChatNewDTO;
import fer.proinz.hocuvan.helpers.LastMessageDTO;
import fer.proinz.hocuvan.helpers.MessageDTO;
import fer.proinz.hocuvan.service.*;
import fer.proinz.hocuvan.domain.*;
import fer.proinz.hocuvan.helpers.*;
import fer.proinz.hocuvan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/send/message/{chatId}")
    public void sendMessage(MessageDTO message, @DestinationVariable("chatId") String chatId){
    	System.out.println("Usao je");
        Chat chat=chatService.fetch(message.getChatId());
        if(chat==null){
            Visitor visitor=(visitorService.findByUsername(message.getReceiver()) == null ? visitorService.findByUsername(message.getSender()) : visitorService.findByUsername(message.getReceiver()));
            Organizer organizer=(organizerService.findByUsername(message.getReceiver()) == null ? organizerService.findByUsername(message.getSender()) : organizerService.findByUsername(message.getReceiver()));

            if(visitor!=null || organizer!=null) {
                Chat newChat = new Chat(organizer, visitor);
                chat = chatService.save(newChat);
            }else {
                throw new RequestDeniedException("Nemoguće poslati poruku!");
            }
        }

        Message newMessage=new Message(message.getText(),message.getSender(),chat);
        messageService.createMessage(newMessage);
        this.simpMessagingTemplate.convertAndSend("/message/"+chatId,  newMessage);
    }

    @Secured({"ROLE_VISITOR","ROLE_ORGANIZER"})
    @PostMapping("/createChat")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> createChat(@RequestBody ChatNewDTO chatNewDTOt){
        Visitor visitor = visitorService.findByUsername(chatNewDTOt.getVisitor());
        Organizer organizer = organizerService.findByUsername(chatNewDTOt.getOrganizer());
        if(visitor != null && organizer != null){
            Chat chat = new Chat(organizer, visitor);
            chatService.save(chat);
            return ResponseEntity.status(HttpStatus.OK).body(new ChatDTO(chat.getChatId(), chat.getVisitorId().getUsername(), ""));
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Nemoguće stvoriti novi ragovor"));
    }

    @Secured({"ROLE_VISITOR","ROLE_ORGANIZER"})
    @GetMapping("{username}/allMessages/{chatId}")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getAllMessages(@PathVariable("chatId") String chatId, @PathVariable("username") String username, @AuthenticationPrincipal User user){
        Chat chat = chatService.fetch(Long.valueOf(chatId));
        if(username.equals(user.getUsername()) && chat!=null){
            List<Message> messages=messageService.getAllByChat(chat);
            return ResponseEntity.status(HttpStatus.OK).body(MessageDTO.fromMessagetoMessageDTOList(messages));
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Korisnik je pokušao pristupiti tuđim porukama ili razgovor ne postoji"));
    }

    @Secured({"ROLE_VISITOR","ROLE_ORGANIZER"})
    @GetMapping("{username}/allChats")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getAllChats(@PathVariable("username") String username, @AuthenticationPrincipal User user){
        if(username.equals(user.getUsername())) {
            Visitor visitor = visitorService.findByUsername(username);
            Organizer organizer = organizerService.findByUsername(username);
            if (visitor != null) {
                List<Chat> chats = chatService.getAllByVisitor(visitor);
                if(chats!=null) {
                    List<LastMessageDTO> lastMessages = new ArrayList<>();
                    for (Chat chat : chats) {
                        lastMessages.add(messageService.getLastMessage(chat));
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(ChatDTO.fromChattoChatDTOList(chats, true, lastMessages));
                }
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }

            if (organizer != null) {
                List<Chat> chats = chatService.getAllByOrganizer(organizer);
                if (chats != null) {
                    List<LastMessageDTO> lastMessages = new ArrayList<>();
                    for (Chat chat : chats) {
                        lastMessages.add(messageService.getLastMessage(chat));
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(ChatDTO.fromChattoChatDTOList(chats, false, lastMessages));
                }
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Korisnik moze pristupiti samo svojim razgovorima!"));
    }

    @GetMapping("/message/{id}")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> getMessageById(@PathVariable("id") String id) {
        try {
            Message message = messageService.fetch(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @GetMapping("/{username}/chat/{id}")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Transactional
    public ResponseEntity<Object> geChatById(@PathVariable("id") String id, @PathVariable("username") String username, @AuthenticationPrincipal User user) {        
        if(username.equals(user.getUsername())) {
            try {
                Visitor visitor = visitorService.findByUsername(username);
                Organizer organizer = organizerService.findByUsername(username);
                if (visitor != null) {
                	Chat chat = chatService.fetch(Long.valueOf(id));
                	LastMessageDTO lastMessageDTO =messageService.getLastMessage(chat);
                	ChatDTO chatDto=ChatDTO.fromChatToChatDTO(chat, true, lastMessageDTO);
                    return ResponseEntity.status(HttpStatus.OK).body(chatDto);
                }
                if(organizer != null) {
                	Chat chat = chatService.fetch(Long.valueOf(id));
                	LastMessageDTO lastMessageDTO =messageService.getLastMessage(chat);
                	ChatDTO chatDto=ChatDTO.fromChatToChatDTO(chat, false, lastMessageDTO);
                    return ResponseEntity.status(HttpStatus.OK).body(chatDto);
                }
            } catch (RequestDeniedException ex) {
                return ResponseEntity.badRequest().body(ex);
            }
        }
        return ResponseEntity.badRequest().body(new RequestDeniedException("Korisnik može pristupati samo svojim porukama!"));
    }

    @GetMapping("/chat/{id}/lastMessage")
    @CrossOrigin(origins = {"https://hocuvan-deployment.herokuapp.com","http://localhost:4200"})
    @Secured({"ROLE_VISITOR","ROLE_ORGANIZER"})
    @Transactional
    public ResponseEntity<Object> getLastMessage(@PathVariable("id") String id, @AuthenticationPrincipal User user) {
        try {
            Chat chat=chatService.fetch(Long.valueOf(id));
            LastMessageDTO lastMessageDTO =messageService.getLastMessage(chat);
            return ResponseEntity.status(HttpStatus.OK).body(lastMessageDTO);

        } catch (RequestDeniedException ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

}
