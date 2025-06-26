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
                int generatedAccountID = (int) pkResultSet.getLong(1);
                return new Account(generatedAccountID, account.getUsername(), account.getPassword());
            }            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * This method will check the database to make sure the account that wants to log in exists
     * @param account: The account that wants to log in
     * @return: the account from the database, including account_id
     */
    public Account logInAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username =? AND password = ?;";
            PreparedStatement pS = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pS.setString(1, account.getUsername());
            pS.setString(2, account.getPassword());
            ResultSet rs = pS.executeQuery();

            while (rs.next()) {
                Account loggedInAccount = new Account(rs.getInt("account_id"), 
                                                      rs.getString("username"), 
                                                      rs.getString("password"));
                return loggedInAccount;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
