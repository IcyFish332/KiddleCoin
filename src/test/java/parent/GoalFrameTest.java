package parent;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.SavingGoal;
import data.DataManager;
import ui.parent.GoalFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoalFrameTest {
    private GoalFrame goalFrame;
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;

    @BeforeEach
    void setUp() {
        DataManager dataManager = new DataManager();
        accountManager = new AccountManager(dataManager);
        parentAccount = new ParentAccount("parent1", "John Doe", "password");
        childAccount = new ChildAccount("child1", "Jane Doe", "password");
        accountManager.saveAccount(parentAccount);
        accountManager.saveAccount(childAccount);
        parentAccount.addChildAccount(childAccount.getAccountId());
        goalFrame = new GoalFrame(accountManager, parentAccount, childAccount);
    }

    @Test
    void testUpdateInformation_NewGoal() {
        goalFrame.goalnameField.setText("Goal 1");
        goalFrame.targetField.setText("1000.0");
        goalFrame.awardField.setText("100.0");
        goalFrame.descriptionArea.setText("Save for a new bike");

        goalFrame.updateInformation(accountManager, parentAccount, null);

        SavingGoal goal = childAccount.getSavingGoals().get(0);
        assertEquals("Goal 1", goal.getName());
        assertEquals(1000.0, goal.getTargetAmount());
        assertEquals(100.0, goal.getReward());
        assertEquals("Save for a new bike", goal.getDescription());
    }

    @Test
    void testUpdateInformation_ExistingGoal() {
        SavingGoal existingGoal = new SavingGoal("Old Goal", "Old description", 500.0, 50.0);
        childAccount.addSavingGoal(existingGoal);

        goalFrame.goalnameField.setText("New Goal");
        goalFrame.targetField.setText("2000.0");
        goalFrame.awardField.setText("200.0");
        goalFrame.descriptionArea.setText("Save for a new laptop");

        goalFrame.updateInformation(accountManager, parentAccount, existingGoal);

        assertEquals("New Goal", existingGoal.getName());
        assertEquals(2000.0, existingGoal.getTargetAmount());
        assertEquals(200.0, existingGoal.getReward());
        assertEquals("Save for a new laptop", existingGoal.getDescription());
    }

    @Test
    void testAnyTextFieldIsEmpty() {
        goalFrame.goalnameField.setText("Goal 1");
        goalFrame.targetField.setText("1000.0");
        goalFrame.awardField.setText("100.0");
        goalFrame.descriptionArea.setText("Save for a new bike");
        assertFalse(goalFrame.anyTextFieldIsEmpty());

        goalFrame.goalnameField.setText("");
        assertTrue(goalFrame.anyTextFieldIsEmpty());

        goalFrame.goalnameField.setText("Goal 1");
        goalFrame.targetField.setText("");
        assertTrue(goalFrame.anyTextFieldIsEmpty());

        goalFrame.targetField.setText("1000.0");
        goalFrame.awardField.setText("");
        assertTrue(goalFrame.anyTextFieldIsEmpty());

        goalFrame.awardField.setText("100.0");
        goalFrame.descriptionArea.setText("");
        assertTrue(goalFrame.anyTextFieldIsEmpty());
    }

    @Test
    void testShowInvalidInfoDialog() {
        // This test case cannot be automated because it involves a dialog box.
        // You would need to manually verify that the dialog box appears when expected.
        goalFrame.showInvalidInfoDialog();
    }

    // Add more test cases as needed
}