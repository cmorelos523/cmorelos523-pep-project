package DAO;

import Util.ConnectionUtil;
import java.sql.*;

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
}
