package fer.proinz.hocuvan.helpers;

import java.util.ArrayList;
import java.util.List;

import fer.proinz.hocuvan.domain.Message;

public class MessageDTO {
    Long chatId;
    String text;
    String sender;
    String receiver;

    public MessageDTO() {
    }

    public MessageDTO(Long chatId, String text, String sender, String receiver) {
        this.chatId=chatId;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public static List<MessageDTO> fromMessagetoMessageDTOList(List<Message> chatList){
        if(chatList==null){
            return null;
        }
        List<MessageDTO> chatDTOS=new ArrayList<>();

        String receiver = "";
        if(chatList.get(0).getSender().equals(chatList.get(0).getChatId().getVisitorId().getUsername())) {
        	receiver = chatList.get(0).getChatId().getOrganizerId().getUsername();
        } else {
        	receiver = chatList.get(0).getChatId().getVisitorId().getUsername();
        }
        
        for(Message mess : chatList) {
            chatDTOS.add(new MessageDTO(mess.getChatId().getChatId(), mess.getMessageText(),
            		mess.getSender(), receiver));
        }
        return chatDTOS;
    }
}
