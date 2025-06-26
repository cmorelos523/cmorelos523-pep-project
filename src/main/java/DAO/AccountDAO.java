package DAO;

import Util.ConnectionUtil;
import java.sql.*;

import Model.Account;

public class AccountDAO {
    
    /* 
     * This method will add a new account to the data base
     * @param account: a new account
     * @return: the author added 
     */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement pS = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pS.setString(1, account.getUsername());
            pS.setString(2, account.getPassword());
            pS.executeUpdate();
            ResultSet pkResultSet = pS.getGeneratedKeys();

            if (pkResultSet.next()) {
                int generatedAuthorID = (int) pkResultSet.getLong(1);
                return new Account(generatedAuthorID, account.getUsername(), account.getPassword());
            }            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
