package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("acc001", "user", "password") {
            @Override
            public String getAccountType() {
                return null;
            }
        };
    }

    @Test//ensures that the getAccountId() method returns the expected account ID.
    public void testGetAccountId()
    {
        assertEquals("acc001", account.getAccountId());
    }

    @Test//verifies that the setAccountId() method correctly sets the account ID and that the value set can be retrieved using this method.
    public void testSetAccountId() {
        account.setAccountId("acc002");
        assertEquals("acc002", account.getAccountId());
    }

    @Test//checks that the setUsername() method correctly sets the username and can retrieve the set value through the getUsername() method.
    public void testGetUsername() {
        assertEquals("user", account.getUsername());
    }

    @Test//ensures that the setUsername() method correctly sets the username and can be retrieved using this method.
    public void testSetUsername() {
        account.setUsername("newUser");
        assertEquals("newUser", account.getUsername());
    }

    @Test//ensures that the getPassword() method returns the expected password.
    public void testGetPassword() {
        assertEquals("password", account.getPassword());
    }

    @Test//verifies that the setPassword() method correctly sets the password and can retrieve the set value through the getPassword() method.
    public void testSetPassword() {
        account.setPassword("newPassword");
        assertEquals("newPassword", account.getPassword());
    }

    @Test//tests whether the default implementation of the abstract method getAccountType() meets expectations, namely, returning null
    public void testAbstractMethod() {
        assertNull(account.getAccountType());
    }
}

