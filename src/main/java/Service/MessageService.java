package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        return messageDAO.addMessage(message);

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
            return messageDAO.deleteMessageById(message_id);
        }

    }

    public Message updateMessageById(int id, Message message) {
        return messageDAO.updateMessageById(id, message);

    }

    public List<Message> getMessageGivenAccountId(int accountId) {
        return messageDAO.getMessageGivenAccountId(accountId);
    }
    
}
