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

    @Test
    public void testCreateChildAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        assertNotNull(childAccount);
        assertNotNull(dataManager.getAccount(childAccount.getAccountId()));
    }

    @Test
    public void testCreateParentAccount() {
        ParentAccount parentAccount = accountManager.createParentAccount("ParentUser", "password");
        assertNotNull(parentAccount);
        assertNotNull(dataManager.getAccount(parentAccount.getAccountId()));
    }

    @Test
    public void testUnlinkChildFromParent() {
        ParentAccount parentAccount = accountManager.createParentAccount("ParentUser", "password");
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");

        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount.getAccountId());
        accountManager.unlinkChildFromParent(childAccount.getAccountId(), parentAccount.getAccountId());

        assertFalse(parentAccount.getChildAccountIds().contains(childAccount.getAccountId()));
        assertFalse(childAccount.getParentAccountIds().contains(parentAccount.getAccountId()));
    }


    @Test
    public void testSaveAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        childAccount.setUsername("NewUsername");
        accountManager.saveAccount(childAccount);

        Account retrievedAccount = accountManager.getAccount(childAccount.getAccountId());
        assertEquals("NewUsername", retrievedAccount.getUsername());
    }

    @Test
    public void testValidateCredentials() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        assertTrue(accountManager.validateCredentials("ChildUser", "password"));
        assertFalse(accountManager.validateCredentials("ChildUser", "wrongPassword"));
    }

    @Test
    public void testIsUsernameExists() {
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");
        assertTrue(accountManager.isUsernameExists("ChildUser"));
        assertFalse(accountManager.isUsernameExists("NonExistingUser"));
    }

    // 边界测试
    @Test
    public void testGetAccountBoundary() {
        // 测试尝试获取不存在的账户时的边界情况
        Account retrievedAccount = accountManager.getAccount("NonExistingID");
        assertNull(retrievedAccount); // 应返回空
    }

    @Test
    public void testGetAccountByUsernameBoundary() {
        // 测试尝试根据不存在的用户名获取账户时的边界情况
        Account retrievedAccount = accountManager.getAccountByUsername("NonExistingUsername");
        assertNull(retrievedAccount); // 应返回空
    }
}