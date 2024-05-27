package core;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a parent's account with specific attributes and methods.
 * This class extends the Account class and includes features such as managing child accounts.
 *
 * @author Siyuan Lu
 */
public class ParentAccount extends Account {
    private Set<String> childAccountIds; // A set to store child account IDs

    /**
     * Constructs a new ParentAccount with the specified accountId, name, and password.
     *
     * @param accountId the unique identifier for this parent account
     * @param name the username associated with this parent account
     * @param password the password associated with this parent account
     */
    public ParentAccount(String accountId, String name, String password) {
        super(accountId, name, password);
        this.accountType = "Parent";
        this.childAccountIds = new HashSet<>();
    }

    /**
     * Adds a child account ID to the set of child accounts.
     *
     * @param accountId the child account ID to add
     */
    public void addChildAccount(String accountId) {
        childAccountIds.add(accountId);
    }

    /**
     * Removes a child account ID from the set of child accounts.
     *
     * @param accountId the child account ID to remove
     */
    public void removeChildAccount(String accountId) {
        childAccountIds.remove(accountId);
    }

    /**
     * Returns a set of all child account IDs associated with this parent account.
     *
     * @return a set of child account IDs
     */
    public Set<String> getChildAccountIds() {
        return new HashSet<>(childAccountIds);
    }

}
