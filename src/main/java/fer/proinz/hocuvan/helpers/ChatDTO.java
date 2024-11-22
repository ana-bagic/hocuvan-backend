package fer.proinz.hocuvan.helpers;

import fer.proinz.hocuvan.domain.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatDTO {
    Long chatId;
    String otherUsername;
    String lastMessage;

    public ChatDTO(Long chatId, String otherUsername, String lastMessage) {
        this.chatId=chatId;
        this.otherUsername = otherUsername;
        this.lastMessage = lastMessage;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }
    
    public String getLastMessage() {
    	return lastMessage;
    }
    
    public void setLastMessage(String lastMessage) {
    	this.lastMessage = lastMessage;
    }

    /*public static ChatDTO fromObjectToUsername(Organizer organizer, Visitor visitor){
        return new ChatDTO(organizer.getUsername(),visitor.getUsername());
    }*/

    public static ChatDTO fromChatToChatDTO(Chat chat, boolean isVisitor, LastMessageDTO lastMessage){
        return new ChatDTO(chat.getChatId(),
        		isVisitor ? chat.getOrganizerId().getUsername() : chat.getVisitorId().getUsername(),
        		lastMessage != null ? lastMessage.getText() : "");
    }


    public static List<ChatDTO> fromChattoChatDTOList(List<Chat> chatList, boolean isVisitor, List<LastMessageDTO> lastMessages){
        if(chatList==null){
            return null;
        }
        List<ChatDTO> chatDTOS=new ArrayList<>();
        int i = 0;
        for(Chat chat : chatList){
            chatDTOS.add(new ChatDTO(chat.getChatId(),
            		isVisitor ? chat.getOrganizerId().getUsername() : chat.getVisitorId().getUsername(),
            		lastMessages.get(i) != null ? lastMessages.get(i++).getText() : ""));
        }
        return chatDTOS;
    }
}
