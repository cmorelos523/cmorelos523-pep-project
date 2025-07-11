package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    /*
     * Private fields
     */
    private AccountDAO accountDAO;

    /* 
     * No args constructor, new DAO
     */
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /*
     * Constructor with DAO, used for testing
     * @param accountDAO: Mocked DAO used for testing
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /*
     * This method calls the appropiate method in the DAO to create a new account, it also makes
     * sure the account username is not black and the password is greater than 3 characters
     * @param account: a new Account
     * @return: the new account if the addition was succesful
     */
    public Account addAccount(Account account) {
        if ((account.getUsername().length() > 0) && (account.getPassword().length() >= 4)) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    /*
     * This method calls the appropiate method in the DAO to process an account logging in
     * @param account: The account that wants to log in
     * @return: the account information with its account_id
     */
    public Account logIn(Account account) {
        return accountDAO.logInAccount(account);
    }
}
