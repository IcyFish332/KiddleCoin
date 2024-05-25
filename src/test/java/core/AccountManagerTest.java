package core;

import core.*;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {
    private AccountManager accountManager;
    private DataManager dataManager;

    @BeforeEach
    public void setUp() {
        dataManager = new DataManager();
        accountManager = new AccountManager(dataManager);
    }

    @Test// Test creating a child account
    public void testCreateChildAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        assertNotNull(childAccount);
        assertNotNull(dataManager.getAccount(childAccount.getAccountId()));
    }

    @Test// Test creating a parent account
    public void testCreateParentAccount() {
        ParentAccount parentAccount = accountManager.createParentAccount("ParentUser", "password");
        assertNotNull(parentAccount);
        assertNotNull(dataManager.getAccount(parentAccount.getAccountId()));
    }

    @Test
    public void testLinkChildToParent() {
        ParentAccount parentAccount = accountManager.createParentAccount("ParentUser", "password");
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");

        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount.getAccountId());

        assertTrue(parentAccount.getChildAccountIds().contains(childAccount.getAccountId()));
        assertTrue(childAccount.getParentAccountIds().contains(parentAccount.getAccountId()));
    }
    @Test//Test unlinking a child account from a parent account
    public void testUnlinkChildFromParent() {
        ParentAccount parentAccount = accountManager.createParentAccount("ParentUser", "password");
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");

        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount.getAccountId());
        accountManager.unlinkChildFromParent(childAccount.getAccountId(), parentAccount.getAccountId());

        assertFalse(parentAccount.getChildAccountIds().contains(childAccount.getAccountId()));
        assertFalse(childAccount.getParentAccountIds().contains(parentAccount.getAccountId()));
    }





    @Test// Test saving an account
    public void testSaveAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        childAccount.setUsername("NewUsername");
        accountManager.saveAccount(childAccount);

        Account retrievedAccount = accountManager.getAccount(childAccount.getAccountId());
        assertEquals("NewUsername", retrievedAccount.getUsername());
    }

    @Test// Test validating credentials
    public void testValidateCredentials() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        assertTrue(accountManager.validateCredentials("ChildUser", "password"));
        assertFalse(accountManager.validateCredentials("ChildUser", "wrongPassword"));
    }

    @Test// Test if a username is exists
    public void testIsUsernameExists() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        assertTrue(accountManager.isUsernameExists("ChildUser"));
        assertFalse(accountManager.isUsernameExists("NonExistingUser"));
    }

    // Boundary test for getting an account
    @Test
    public void testGetAccountBoundary() {
        // Test the boundary case of attempting to retrieve a non-existent account
        Account retrievedAccount = accountManager.getAccount("NonExistingID");
        assertNull(retrievedAccount);
    }

    @Test
    public void testGetAccountByUsernameBoundary() {
        // Test the boundary case of attempting to retrieve an account by a non-existent username
        Account retrievedAccount = accountManager.getAccountByUsername("NonExistingUsername");
        assertNull(retrievedAccount);
    }
}
