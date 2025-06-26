package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    /*
     * This class private fields
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
     * This method calls the appropiate method in the DAO to create a new user
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
}
