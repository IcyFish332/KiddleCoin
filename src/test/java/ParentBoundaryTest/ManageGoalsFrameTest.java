package ParentBoundaryTest;


import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.SavingGoal;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.parent.ManageGoalsFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.*;

class ManageGoalsFrameTest {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ManageGoalsFrame manageGoalsFrame;

    @BeforeEach
    void setUp() {
        DataManager dataManager = new DataManager();
        accountManager = new AccountManager(dataManager); // 不需要 DataManager，因此传入 null
        parentAccount = new ParentAccount("parent1", "John Doe", "password");
        accountManager.saveAccount(parentAccount);
        ChildAccount childAccount = new ChildAccount("child1", "Jane Doe", "password");
        parentAccount.addChildAccount(childAccount.getAccountId());
        accountManager.saveAccount(childAccount);
        manageGoalsFrame = new ManageGoalsFrame(accountManager, parentAccount);
    }

    @Test
    void testAddGoal() {
        // Add a new goal
        manageGoalsFrame.openSetGoalFrame();
        assertNotNull(manageGoalsFrame.childAccount); // 孩子账户应该不为 null
    }

    @Test
    void testEditGoal() {
        // Add a sample goal
        SavingGoal goal = new SavingGoal("Test Goal", "Description", 1000.0, 100.0);
        manageGoalsFrame.childAccount.addSavingGoal(goal);
        accountManager.saveAccount(manageGoalsFrame.childAccount);

        // Edit the goal
        int rowCountBeforeEdit = manageGoalsFrame.goalsModel.getRowCount();
        manageGoalsFrame.editGoal(0);
        int rowCountAfterEdit = manageGoalsFrame.goalsModel.getRowCount();
        assertEquals(rowCountBeforeEdit, rowCountAfterEdit); // 编辑后行数应该不变
    }

    @Test
    void testDeleteGoal() {
        // Add a sample goal
        SavingGoal goal = new SavingGoal("Test Goal", "Description", 1000.0, 100.0);
        manageGoalsFrame.childAccount.addSavingGoal(goal);
        accountManager.saveAccount(manageGoalsFrame.childAccount);

        // Delete the goal
        int rowCountBeforeDelete = manageGoalsFrame.goalsModel.getRowCount();
        manageGoalsFrame.deleteGoal(0);
        int rowCountAfterDelete = manageGoalsFrame.goalsModel.getRowCount();
        assertEquals(0, rowCountAfterDelete);
    }
}
