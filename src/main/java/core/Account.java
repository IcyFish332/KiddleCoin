package core;

/**
 * Represents a general account with basic attributes such as accountId, username, and password.
 * This is an abstract class that can be extended by specific types of accounts.
 *
 * <p>Common methods for getting and setting attributes are provided.</p>
 *
 * @author Siyuan Lu
 */
public abstract class Account {
    protected String accountId;

    protected String username;
    protected String password;

    protected String accountType;

    /**
     * Constructs a new Account with the specified accountId, username, and password.
     *
     * @param accountId the unique identifier for this account
     * @param username the username associated with this account
     * @param password the password associated with this account
     */
    public Account(String accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }


    /**
     * Returns the accountId of this account.
     *
     * @return the accountId of this account
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the accountId for this account.
     *
     * @param accountId the new accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Returns the username of this account.
     *
     * @return the username of this account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this account.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of this account.
     *
     * @return the password of this account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this account.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the account type of this account.
     *
     * @return the account type of this account
     */
    public String getAccountType() {return this.accountType;}

    // 可能还有其他共通的方法
}