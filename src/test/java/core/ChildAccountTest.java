package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TransactionHistory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChildAccountTest {
    private ChildAccount childAccount;

    @BeforeEach
    public void setUp() {
        childAccount = new ChildAccount("child01", "childUser", "password");
    }

    @Test
    public void testDeposit() {
        childAccount.deposit(100);
        assertEquals(100, childAccount.getBalance(), 0.01);
    }

    @Test
    public void testDepositNegativeAmount() {
        childAccount.deposit(-50);
        assertEquals(0, childAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw() {
        childAccount.deposit(100);
        childAccount.withdraw(50);
        assertEquals(50, childAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawNegativeAmount() {
        childAccount.deposit(100);
        childAccount.withdraw(-50);
        assertEquals(100, childAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawMoreThanBalance() {
        childAccount.deposit(100);
        childAccount.withdraw(150);
        assertEquals(100, childAccount.getBalance(), 0.01);
    }

    @Test
    public void testSaveMoney() {
        childAccount.saveMoney(100);
        assertEquals(100, childAccount.getSavings(), 0.01);
    }

    @Test
    public void testAddParentAccount() {
        childAccount.addParentAccount("parent01");
        assertTrue(childAccount.getParentAccountIds().contains("parent01"));
    }

    @Test
    public void testAddDuplicateParentAccount() {
        childAccount.addParentAccount("parent01");
        childAccount.addParentAccount("parent01");
        assertEquals(1, childAccount.getParentAccountIds().size());
    }

    @Test
    public void testRemoveParentAccount() {
        childAccount.addParentAccount("parent01");
        childAccount.removeParentAccount("parent01");
        assertFalse(childAccount.getParentAccountIds().contains("parent01"));
    }

    @Test
    public void testRemoveNonExistingParentAccount() {
        childAccount.removeParentAccount("parent01");
        assertTrue(childAccount.getParentAccountIds().isEmpty());
    }

    @Test
    public void testTransactionHistory() {
        childAccount.deposit(100);
        childAccount.withdraw(50);
        List<TransactionHistory> history = childAccount.getTransactionHistory();
        assertEquals(2, history.size());
        assertEquals("Deposit", history.get(0).getType());
        assertEquals(100, history.get(0).getAmount(), 0.01);
        assertEquals("Withdraw", history.get(1).getType());
        assertEquals(-50, history.get(1).getAmount(), 0.01);
    }

    @Test
    public void testAddSavingGoal() {
        SavingGoal goal = new SavingGoal("Goal1", "Save for a bike", 200, 20);
        childAccount.addSavingGoal(goal);
        assertTrue(childAccount.getSavingGoals().contains(goal));
    }

    @Test
    public void testRemoveSavingGoal() {
        SavingGoal goal = new SavingGoal("Goal1", "Save for a bike", 200, 20);
        childAccount.addSavingGoal(goal);
        childAccount.removeSavingGoal(goal);
        assertFalse(childAccount.getSavingGoals().contains(goal));
    }

    @Test
    public void testRemoveNonExistingSavingGoal() {
        SavingGoal goal = new SavingGoal("Goal1", "Save for a bike", 200, 20);
        childAccount.removeSavingGoal(goal);
        assertTrue(childAccount.getSavingGoals().isEmpty());
    }

    @Test
    public void testCompleteTask() {
        Task task = new Task("Task1", "Do homework", 10, null);
        childAccount.addTask(task);
        childAccount.completeTask(task);
        assertTrue(task.isTaskCompleted());
        assertEquals(10, childAccount.getBalance(), 0.01);
    }

    @Test
    public void testCompleteNonExistingTask() {
        Task task = new Task("Task1", "Do homework", 10, null);
        childAccount.completeTask(task);
        assertFalse(task.isTaskCompleted());
    }

    @Test // 新增
    public void testNullTaskDueDate() {
        Task task = new Task("Task1", "Do homework", 10, null);
        childAccount.addTask(task);
        childAccount.completeTask(task);
        assertNull(task.getDueDate());

    }

}