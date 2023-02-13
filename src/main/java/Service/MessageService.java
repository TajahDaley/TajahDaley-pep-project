package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message) {
        if(message.posted_by >= 1 && message.message_text != "" && message.message_text.length() <= 255) {
            return messageDAO.addMessage(message);
        }else {
            return null;
        }

    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        Message deleteMessage = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessageById(message_id);
        if(deleteMessage != null) {
            return deleteMessage;
        }else {
            return null;
        }

    }

    public Message updateMessageById(int id, Message message) {
        if(message.message_text != "" && message.message_text.length() <= 255) {
            return messageDAO.updateMessageById(id, message);
        } else {
            return null;
        }

    }

    public List<Message> getMessageGivenAccountId(int accountId) {
        return messageDAO.getMessageGivenAccountId(accountId);
    }
    
}
