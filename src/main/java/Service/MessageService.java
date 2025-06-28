package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.*;

public class MessageService {
    /*
     * Private fields
     */
    private MessageDAO messageDAO;

    /* 
     * No args constructor, new DAO
     */
    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    /*
     * Constructor with DAO, used for testing
     * @param messageDAO: Mocked DAO used for testing
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /*
     * This method calls the appropiate method in the DAO to create a new message, it also makes sure
     * that the message is valid, to be valid it must be:
     *  1) Not blank
     *  2) Less than 255 characters
     *  3) Posted by a real user
     * @param message: a new message
     * @return: the new message if the addition was succesful
     */
    public Message addMessage(Message message) {
        if ((message.getMessage_text().length() > 0) && (message.getMessage_text().length() <= 255)) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }

    /*
     * This method calls the selectAllMessages method in the DAO to retrieve all messages
     * No parameters
     * @return: the list of all messages created
     */
    public List<Message> getAllMessages() {
        return messageDAO.selectAllMessages();
    }

    /*
     * This method calls the method in the DAO to retrieve the message identified by the id
     * @param message_id: The message's id we want to retrieve
     * @return: the message identified by its id
     */
    public Message getMessageByID(String message_id) {
        return messageDAO.selectMessageWhereID(Integer.parseInt(message_id));
    }

    /*
     * This method calls the method in the DAO to delete the message identified by the id
     * @param message_id: The message's id we want to delete
     */
    public int deleteMessageByID(String message_id) {
        return messageDAO.deleteMessageWhereID(Integer.parseInt(message_id));
    }

    /*
     * This method calls the appropiate method in the DAO to update the message identified by its id
     * @param message: the message containing all the data that will replace the values contained by
     *                 the existing message_id
     * @return: the updated message
     */
    public Message updateMessageByID(Message message) {
        int message_id = message.getMessage_id();
        
        if (messageDAO.selectMessageWhereID(message_id) == null) {
            return null;
        }

        if ((message.getMessage_text().length() > 0) && (message.getMessage_text().length() <= 255)) {
            return messageDAO.selectMessageWhereID(message.getMessage_id());
        } else {
            return null;
        }
    }
}
