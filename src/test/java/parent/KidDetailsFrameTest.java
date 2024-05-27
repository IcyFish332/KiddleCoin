package parent;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.SavingGoal;
import core.Task;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.parent.KidDetailsFrame;

import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class KidDetailsFrameTest {
    private KidDetailsFrame kidDetailsFrame;
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

        // Add some sample data
        childAccount.setSavings(1000.0);
        childAccount.addSavingGoal(new SavingGoal("Goal 1", "Save for bike", 500.0, 50.0));
        childAccount.addSavingGoal(new SavingGoal("Goal 2", "Save for laptop", 1500.0, 100.0));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7); // Due in 7 days
        Date dueDate = cal.getTime();
        childAccount.addTask(new Task("Task 1", "Do homework", 10.0, dueDate));
        childAccount.addTask(new Task("Task 2", "Clean room", 15.0, dueDate));

        kidDetailsFrame = new KidDetailsFrame(accountManager, parentAccount, childAccount);
    }

    @Test
    void testInitComponents() {
        // Check if the components are initialized correctly
        assertNotNull(kidDetailsFrame.goalsTable);
        assertNotNull(kidDetailsFrame.tasksTable);

        // Check if the goals table is populated correctly
        DefaultTableModel goalsModel = (DefaultTableModel) kidDetailsFrame.goalsTable.getModel();
        assertEquals(2, goalsModel.getRowCount());
        assertEquals("Goal 1", goalsModel.getValueAt(0, 0));
        assertEquals("Save for bike", goalsModel.getValueAt(0, 1));
        assertEquals(500.0, goalsModel.getValueAt(0, 2));
        assertEquals(50.0, goalsModel.getValueAt(0, 3));
        assertEquals("200.0%", goalsModel.getValueAt(0, 4));

        // Check if the tasks table is populated correctly
        DefaultTableModel tasksModel = (DefaultTableModel) kidDetailsFrame.tasksTable.getModel();
        assertEquals(2, tasksModel.getRowCount());
        assertEquals("Task 1", tasksModel.getValueAt(0, 0));
        assertEquals("Do homework", tasksModel.getValueAt(0, 1));
        assertEquals(10.0, tasksModel.getValueAt(0, 2));
        assertNotNull(tasksModel.getValueAt(0, 3)); // Due date should not be null
    }

    // Add more test cases as needed
}