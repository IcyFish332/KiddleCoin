package core;

import core.Account;
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
                return null; // 返回类型暂时不需要实现，因为这是一个抽象方法的测试
            }
        };
    }

    @Test
    public void testGetAccountId() {
        assertEquals("acc001", account.getAccountId());
    }

    @Test
    public void testSetAccountId() {
        account.setAccountId("acc002");
        assertEquals("acc002", account.getAccountId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("user", account.getUsername());
    }

    @Test
    public void testSetUsername() {
        account.setUsername("newUser");
        assertEquals("newUser", account.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", account.getPassword());
    }

    @Test
    public void testSetPassword() {
        account.setPassword("newPassword");
        assertEquals("newPassword", account.getPassword());
    }

    @Test
    public void testAbstractMethod() {
        // 测试抽象方法的默认实现
        assertNull(account.getAccountType());
    }
}


