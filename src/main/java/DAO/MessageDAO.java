package DAO;

import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;
import Model.Account;
import Model.Message;

public class MessageDAO {
    /*
     * This method will add a new message to the database
     * @param: a new message
     * @return: the message added
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement pS = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pS.setInt(1, message.getPosted_by());
            pS.setString(2, message.getMessage_text());
            pS.setLong(3, message.getTime_posted_epoch());
            pS.executeUpdate();
            ResultSet pkResultSet = pS.getGeneratedKeys();

            if (pkResultSet.next()) {
                int generatedMessageID = (int) pkResultSet.getLong(1);
                return new Message(generatedMessageID, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * This method will retrieve all messages from the database
     * No parameters
     * @return: the list of all messages created
     */
    public List<Message> selectAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement pS = connection.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();

            while (rS.next()) {
                Message curr = new Message(rS.getInt("message_id"), 
                                           rS.getInt("posted_by"), 
                                           rS.getString("message_text"), 
                                           rS.getLong("time_posted_epoch"));
                messages.add(curr);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /*
     * This method will retrieve the message identified by its ID from the database
     * @param message_id: The ID of the message we want to retrieve
     * @return: the message if the ID matches
     */
    public Message selectMessageWhereID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setInt(1, message_id);
            ResultSet rS = pS.executeQuery();

            while (rS.next()) {
                Message message = new Message(rS.getInt("message_id"),
                                              rS.getInt("posted_by"), 
                                              rS.getString("message_text"), 
                                              rS.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * This method will delete the message identified by its ID from the database
     * @param message_id: The id of the message we will delete
     * @return: the number of rows deleted
     */
    public int deleteMessageWhereID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        int deletedRows = 0;

        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setInt(1, message_id);
            deletedRows = pS.executeUpdate();
  
            return deletedRows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return deletedRows;
    }

    /*
     * This method will update the message identified by its ID in the database
     * @param message: the message containing all the data that will replace the values contained by
     *                 the existing message_id
     * @return: the updated message
     */
    public void updateMessageWhereID(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, message.getMessage_text());
            pS.setInt(2, message.getMessage_id());
            pS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
