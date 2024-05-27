package parent;


import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.Task;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.parent.ManageTasksFrame;


import static org.junit.jupiter.api.Assertions.*;

class ManageTasksFrameTest {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ManageTasksFrame manageTasksFrame;

    @BeforeEach
    void setUp() {
        DataManager dataManager= new DataManager();
        accountManager = new AccountManager(dataManager); // 不需要 DataManager，因此传入 null
        parentAccount = new ParentAccount("parent1", "John Doe", "password");
        accountManager.saveAccount(parentAccount);
        ChildAccount childAccount = new ChildAccount("child1", "Jane Doe", "password");
        parentAccount.addChildAccount(childAccount.getAccountId());
        accountManager.saveAccount(childAccount);
        manageTasksFrame = new ManageTasksFrame(accountManager, parentAccount);
    }

    @Test
    void testAddTask() {
        // Add a new task
        manageTasksFrame.openSetTaskFrame();
        assertNotNull(manageTasksFrame.childAccount); // 孩子账户应该不为 null
    }

    @Test
    void testEditTask() {
        // Add a sample task
        Task task = new Task("Test Task", "Description", 100.0, new java.util.Date());
        manageTasksFrame.childAccount.addTask(task);
        accountManager.saveAccount(manageTasksFrame.childAccount);

        // Edit the task
        int rowCountBeforeEdit = manageTasksFrame.tasksModel.getRowCount();
        manageTasksFrame.editTask(0);
        int rowCountAfterEdit = manageTasksFrame.tasksModel.getRowCount();
        assertEquals(rowCountBeforeEdit, rowCountAfterEdit); // 编辑后行数应该不变
    }

    @Test
    void testDeleteTask() {
        // Add a sample task
        Task task = new Task("Test Task", "Description", 100.0, new java.util.Date());
        manageTasksFrame.childAccount.addTask(task);
        accountManager.saveAccount(manageTasksFrame.childAccount);

        // Delete the task
        int rowCountBeforeDelete = manageTasksFrame.tasksModel.getRowCount();
        manageTasksFrame.deleteTask(0);
        int rowCountAfterDelete = manageTasksFrame.tasksModel.getRowCount();
        assertEquals(0, rowCountAfterDelete); // 删除后行数应该减少1
    }
}
