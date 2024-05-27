package Integration;

import core.*;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
public class IntegrationTest {

    private AccountManager accountManager;
    private DataManager dataManager;


    @BeforeEach
    public void setUp() {
        dataManager = new DataManager();
        accountManager = new AccountManager(dataManager);
    }

    @Test//test transactions
    public void testChildAccountTransactions() {
        ChildAccount childAccount = accountManager.createChildAccount("Child3", "password5");
        childAccount.deposit(100.0);
        ((ChildAccount) childAccount).withdraw(50.0);
        childAccount.saveMoney(25.0);

        assertFalse(childAccount.getTransactionHistory().isEmpty());
    }
    @Test
    public void testSaveAccount() {
        Account account1 = new ChildAccount("child01", "childUser1", "password1");
        dataManager.saveAccount(account1);
        assertNotNull(dataManager.getAccount("child01"));

    }

    @Test
    public void testGetChildAccounts() {
        // 创建多个子账户
        DataManager daatamanager = new DataManager();
        AccountManager aacountmanager = new AccountManager(daatamanager);
        aacountmanager.createChildAccount("Child1", "password1");
        aacountmanager.createChildAccount("Child2", "password2");
        aacountmanager.createChildAccount("Child3", "password3");

        // 获取所有子账户
        Set<ChildAccount> childAccounts = aacountmanager.getChildAccounts();


        // 验证返回的子账户包含预期的账户
        assertTrue(childAccounts.stream().anyMatch(account -> "Child1".equals(account.getUsername())), "Child1 should exist");
        assertTrue(childAccounts.stream().anyMatch(account -> "Child2".equals(account.getUsername())), "Child2 should exist");
        assertTrue(childAccounts.stream().anyMatch(account -> "Child3".equals(account.getUsername())), "Child3 should exist");

    }



    @Test
    public void testCreateChildAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("Child", "password");
        assertNotNull(childAccount.getAccountId());
        assertEquals("Child", childAccount.getUsername());
        assertEquals("Kid", childAccount.getAccountType());
    }

    @Test
    public void testCreateParentAccount() {
        ParentAccount parentAccount = accountManager.createParentAccount("Parent", "password");
        assertNotNull(parentAccount.getAccountId());
        assertEquals("Parent", parentAccount.getUsername());
        assertEquals("Parent", parentAccount.getAccountType());
    }


    @Test
    public void testUnlinkChildFromParent() {
        ChildAccount childAccount = accountManager.createChildAccount("Child", "password");
        ParentAccount parentAccount = accountManager.createParentAccount("Parent", "password");

        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount.getAccountId());
        accountManager.unlinkChildFromParent(childAccount.getAccountId(), parentAccount.getAccountId());

        Set<String> parentIds = childAccount.getParentAccountIds();
        assertFalse(parentIds.contains(parentAccount.getAccountId()));

        Set<String> childIds = parentAccount.getChildAccountIds();
        assertFalse(childIds.contains(childAccount.getAccountId()));
    }

    @Test
    public void testGetAccountByUsername() {
        ChildAccount childAccount = accountManager.createChildAccount("Child", "password");
        Account account = accountManager.getAccountByUsername("Child");
        assertNotNull(account);
        assertEquals("Child", account.getUsername());
    }


    @Test
    public void testValidateCredentials() {
        accountManager.createChildAccount("Child", "password");
        assertTrue(accountManager.validateCredentials("Child", "password"));
        assertFalse(accountManager.validateCredentials("Child", "wrong_password"));
    }

    @Test
    public void testIsUsernameExists() {
        accountManager.createChildAccount("Child", "password");
        assertTrue(accountManager.isUsernameExists("Child"));
        assertFalse(accountManager.isUsernameExists("NonexistentUser"));
    }

    @Test
    public void testSavingGoalsAndTasksForChildAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("Child4", "password6");
        Date dueDate = new Date();
        Task task = new Task("Task1", "Test Task", 10.0, dueDate);
        SavingGoal goal = new SavingGoal("Goal1", "Test Goal", 500.0, 50.0);

        childAccount.addTask(task);
        childAccount.addSavingGoal(goal);
        childAccount.deposit(1000.0);
        childAccount.saveMoney(500.0);

        assertTrue(childAccount.getTasks().contains(task));
        assertTrue(childAccount.getSavingGoals().isEmpty());
        assertEquals(1550.0, childAccount.getBalance());
    }

    @Test
    public void testMultipleParentsForChildAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("Child5", "password7");
        ParentAccount parentAccount1 = accountManager.createParentAccount("Parent3", "password8");
        ParentAccount parentAccount2 = accountManager.createParentAccount("Parent4", "password9");

        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount1.getAccountId());
        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount2.getAccountId());

        Set<String> parentIds = childAccount.getParentAccountIds();
        assertFalse(parentIds.contains(parentAccount1.getAccountId()));
        assertFalse(parentIds.contains(parentAccount2.getAccountId()));
    }

    @Test
    public void testParentAccountAddRemoveChild() {
        ParentAccount parentAccount = accountManager.createParentAccount("Parent", "password");
        ChildAccount childAccount1 = accountManager.createChildAccount("Child1", "password1");
        ChildAccount childAccount2 = accountManager.createChildAccount("Child2", "password2");
        parentAccount.addChildAccount(childAccount1.getAccountId());
        parentAccount.addChildAccount(childAccount2.getAccountId());
        assertTrue(parentAccount.getChildAccountIds().contains(childAccount1.getAccountId()));
        assertTrue(parentAccount.getChildAccountIds().contains(childAccount2.getAccountId()));
        parentAccount.removeChildAccount(childAccount1.getAccountId());
        assertFalse(parentAccount.getChildAccountIds().contains(childAccount1.getAccountId()));
        assertTrue(parentAccount.getChildAccountIds().contains(childAccount2.getAccountId()));
    }

    @Test
    public void testCompleteTaskForChildAccount() {
        ChildAccount childAccount = accountManager.createChildAccount("Child", "password");
        Date dueDate = new Date();
        Task task = new Task("Task1", "Test Task", 10.0, dueDate);
        childAccount.addTask(task);
        childAccount.deposit(100.0);
        double balanceBefore = childAccount.getBalance();
        childAccount.completeTask(task);
        double balanceAfter = childAccount.getBalance();
        assertEquals(balanceBefore + 10.0, balanceAfter);
        assertTrue(task.isTaskCompleted());
    }

    @Test
    public void testParentSettingTasksAndSavingGoalsForChild() {
        // 创建家长和孩子账户
        ParentAccount parentAccount = accountManager.createParentAccount("ParentUser", "password");
        ChildAccount childAccount = accountManager.createChildAccount("ChildUser", "password");

        // 链接家长和孩子账户
        accountManager.linkChildToParent(childAccount.getAccountId(), parentAccount.getAccountId());

        // 家长为孩子设立任务和储蓄目标
        Date dueDate = new Date();
        Task task = new Task("Task1", "Test Task", 10.0, dueDate);
        SavingGoal goal = new SavingGoal("Goal1", "Test Goal", 500.0, 50.0);

        childAccount.addTask(task);
        childAccount.addSavingGoal(goal);

        // 验证任务和储蓄目标是否被正确添加到孩子账户
        assertTrue(childAccount.getTasks().contains(task));
        assertTrue(childAccount.getSavingGoals().contains(goal));
    }
}
