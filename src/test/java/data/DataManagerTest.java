package data;

import core.Account;
import core.ChildAccount;
import core.ParentAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {
    private DataManager dataManager;
    private ParentAccount parentAccount;

    @BeforeEach
    public void setUp() {
        dataManager = new DataManager();
    }

    @Test
    public void testSaveAccountAndGetAccount() {
        Account account = new ChildAccount("child01", "childUser", "password");
        dataManager.saveAccount(account);
        Account retrievedAccount = dataManager.getAccount("child01");
        assertNotNull(retrievedAccount);
        assertEquals("child01", retrievedAccount.getAccountId());
    }

    @Test
    public void testLoadData() {
        File file = new File("data/accounts.json");
        if (file.exists()) {
            file.delete();
        }

        Map<String, Account> accounts = dataManager.getAccounts();
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testSaveData() {
        Account account = new ChildAccount("child01", "childUser", "password");
        dataManager.saveAccount(account);

        Map<String, Account> accounts = dataManager.getAccounts();
        assertFalse(accounts.isEmpty());
        assertTrue(accounts.containsKey("child01"));
    }

    @Test
    public void testLoadDataFileNotExist() {
        File file = new File("data/accounts.json");
        if (file.exists()) {
            file.delete();
        }

        Map<String, Account> accounts = dataManager.getAccounts();
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testSaveMultipleAccounts() {
        Account account1 = new ChildAccount("child01", "childUser1", "password1");
        Account account2 = new ParentAccount("parent01", "parentUser1", "password2");
        Account account3 = new ChildAccount("child02", "childUser2", "password3");

        dataManager.saveAccount(account1);
        dataManager.saveAccount(account2);
        dataManager.saveAccount(account3);

        assertNotNull(dataManager.getAccount("child01"));
        assertNotNull(dataManager.getAccount("parent01"));
        assertNotNull(dataManager.getAccount("child02"));
    }
}


