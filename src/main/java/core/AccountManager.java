package core;

import data.DataManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Manages the creation, linking, and validation of accounts.
 * This class interacts with the DataManager to persist and retrieve account information.
 *
 * @author Siyuan Lu
 */
public class AccountManager {

    private DataManager dataManager;

    /**
     * Constructs an AccountManager with the specified DataManager.
     *
     * @param dataManager the DataManager used for account persistence
     */
    public AccountManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Creates a new ChildAccount with the specified name and password.
     *
     * @param name the name for the new ChildAccount
     * @param password the password for the new ChildAccount
     * @return the newly created ChildAccount
     */
    public ChildAccount createChildAccount(String name, String password) {
        String accountId = UUID.randomUUID().toString();
        ChildAccount newChildAccount = new ChildAccount(accountId, name, password);
        dataManager.saveAccount(newChildAccount); // 保存新账户
        return newChildAccount;
    }

    /**
     * Creates a new ParentAccount with the specified name and password.
     *
     * @param name the name for the new ParentAccount
     * @param password the password for the new ParentAccount
     * @return the newly created ParentAccount
     */
    public ParentAccount createParentAccount(String name, String password) {
        String accountId = UUID.randomUUID().toString();
        ParentAccount newParentAccount = new ParentAccount(accountId, name, password);
        dataManager.saveAccount(newParentAccount); // 保存新账户
        return newParentAccount;
    }

    /**
     * Links a ChildAccount to a ParentAccount.
     *
     * @param childAccountId the ID of the ChildAccount to link
     * @param parentAccountId the ID of the ParentAccount to link
     */
    public void linkChildToParent(String childAccountId, String parentAccountId) {
        Account childAccount = dataManager.getAccount(childAccountId);
        Account parentAccount = dataManager.getAccount(parentAccountId);

        if (childAccount instanceof ChildAccount && parentAccount instanceof ParentAccount) {
            ((ChildAccount) childAccount).addParentAccount(parentAccountId);
            ((ParentAccount) parentAccount).addChildAccount(childAccountId);
            dataManager.saveAccount(childAccount);
            dataManager.saveAccount(parentAccount);
        }
    }

    /**
     * Unlinks a ChildAccount from a ParentAccount.
     *
     * @param childAccountId the ID of the ChildAccount to unlink
     * @param parentAccountId the ID of the ParentAccount to unlink
     */
    public void unlinkChildFromParent(String childAccountId, String parentAccountId) {
        Account childAccount = dataManager.getAccount(childAccountId);
        Account parentAccount = dataManager.getAccount(parentAccountId);

        if (childAccount instanceof ChildAccount && parentAccount instanceof ParentAccount) {
            ((ChildAccount) childAccount).removeParentAccount(parentAccountId);
            ((ParentAccount) parentAccount).removeChildAccount(childAccountId);
            dataManager.saveAccount(childAccount); // 更新孩子账户
            dataManager.saveAccount(parentAccount); // 更新家长账户
        }
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the Account with the specified ID
     */
    public Account getAccount(String accountId) {
        return dataManager.getAccount(accountId);
    }

    /**
     * Saves an account.
     *
     * @param account the account to save
     */
    public void saveAccount(Account account) {
        dataManager.saveAccount(account);
    }

    /**
     * Retrieves an account by its username.
     *
     * @param username the username of the account to retrieve
     * @return the Account with the specified username, or null if not found
     */
    public Account getAccountByUsername(String username) {
        for (Account account : dataManager.getAccounts().values()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }

        return null;
    }

    /**
     * Retrieves all ChildAccounts.
     *
     * @return a Set of all ChildAccounts
     */
    public Set<ChildAccount> getChildAccounts() {
        Set<ChildAccount> children = new HashSet<>();
        for (Account account : dataManager.getAccounts().values()) {
            if (account instanceof ChildAccount) {
                children.add((ChildAccount) account);
            }
        }
        return children;
    }

    /**
     * Retrieves all ParentAccounts.
     *
     * @return a Set of all ParentAccounts
     */
    public Set<ParentAccount> getParentAccounts() {
        Set<ParentAccount> parents = new HashSet<>();
        for (Account account : dataManager.getAccounts().values()) {
            if (account instanceof ParentAccount) {
                parents.add((ParentAccount) account);
            }
        }
        return parents;
    }

    /**
     * Validates the credentials of an account.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @return true if the credentials are valid, false otherwise
     */
    public boolean validateCredentials(String username, String password) {
        for (Account account : dataManager.getAccounts().values()) {
            if (account.getUsername().equals(username) &&
                    account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a username already exists.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    public boolean isUsernameExists(String username) {
        for (Account account : dataManager.getAccounts().values()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}