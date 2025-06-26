package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;

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
}
