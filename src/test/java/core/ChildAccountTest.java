package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TransactionHistory;

import static org.junit.jupiter.api.Assertions.*;

class ChildAccountTest {
    private ChildAccount childAccount;

    @BeforeEach
    void setUp() {
        childAccount = new ChildAccount("C001", "Alice", "password");
    }

    @Test
    void testDeposit() {
        childAccount.deposit(100);
        assertEquals(100, childAccount.getBalance());
        assertEquals(1, childAccount.getTransactionHistory().size());
        assertEquals("Deposit", childAccount.getTransactionHistory().get(0).getType());
        assertEquals(100, childAccount.getTransactionHistory().get(0).getAmount());
        assertEquals("Balance", childAccount.getTransactionHistory().get(0).getCategory());
    }

    @Test
    void testWithdraw() {
        childAccount.setBalance(200);
        childAccount.withdraw(50);
        assertEquals(150, childAccount.getBalance());
        assertEquals(2, childAccount.getTransactionHistory().size());
        assertEquals("Withdraw", childAccount.getTransactionHistory().get(1).getType());
        assertEquals(-50, childAccount.getTransactionHistory().get(1).getAmount());
        assertEquals("Balance", childAccount.getTransactionHistory().get(1).getCategory());
    }

    @Test
    void testSetBalance() {
        childAccount.setBalance(500);
        assertEquals(500, childAccount.getBalance());
        assertEquals(1, childAccount.getTransactionHistory().size());
        assertEquals("Set Balance", childAccount.getTransactionHistory().get(0).getType());
        assertEquals(500, childAccount.getTransactionHistory().get(0).getAmount());
        assertEquals("Balance", childAccount.getTransactionHistory().get(0).getCategory());
    }

    @Test
    void testSaveMoney() {
        childAccount.saveMoney(200);
        assertEquals(200, childAccount.getSavings());
        assertEquals(1, childAccount.getTransactionHistory().size());
        assertEquals("Save Money", childAccount.getTransactionHistory().get(0).getType());
        assertEquals(200, childAccount.getTransactionHistory().get(0).getAmount());
        assertEquals("Savings", childAccount.getTransactionHistory().get(0).getCategory());
    }

    @Test
    void testSetSavings() {
        childAccount.setSavings(1000);
        assertEquals(1000, childAccount.getSavings());
        assertEquals(1, childAccount.getTransactionHistory().size());
        assertEquals("Set Savings", childAccount.getTransactionHistory().get(0).getType());
        assertEquals(1000, childAccount.getTransactionHistory().get(0).getAmount());
        assertEquals("Savings", childAccount.getTransactionHistory().get(0).getCategory());
    }
}