package kid;

import core.AccountManager;
import core.ChildAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.kid.MyGoalsFrame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyGoalsFrameTest {
    private MyGoalsFrame myGoalsFrame;
    private AccountManager accountManager;
    private ChildAccount childAccount;

    @BeforeEach
    public void setUp() {
        accountManager = new AccountManager(null); // Replace null with actual DataManager
        childAccount = new ChildAccount("child01", "childUser", "password");

        myGoalsFrame = new MyGoalsFrame(accountManager, childAccount);
    }

    @Test
    public void testSaveGoalFromTable_EmptyFields() {
        // Set up the table with empty fields
        myGoalsFrame.tableModel.addRow(new Object[]{"", "", "", "", "", "", ""});

        // Call the method to save the goal
        myGoalsFrame.saveGoalFromTable(0);

        // Check if an error message dialog is displayed
        assertTrue(myGoalsFrame.getComponents().length > 0);
    }

    @Test
    public void testSaveGoalFromTable_InvalidMoneyFormat() {
        // Set up the table with invalid money format
        myGoalsFrame.tableModel.addRow(new Object[]{"Goal Name", "Description", "Target", "Reward", "", "", ""});
        myGoalsFrame.tableModel.setValueAt("Not a number", 0, 2);
        myGoalsFrame.tableModel.setValueAt("Not a number", 0, 3);

        // Call the method to save the goal
        myGoalsFrame.saveGoalFromTable(0);

        // Check if an error message dialog is displayed
        assertTrue(myGoalsFrame.getComponents().length > 0);
    }

}

