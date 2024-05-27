package parent;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.parent.AssignmentFrame;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentFrameTest {

    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;
    private DataManager dataManager;

    @BeforeEach
    public void setUp() {
        dataManager = new DataManager();
        accountManager = new AccountManager(dataManager);
        parentAccount = new ParentAccount("parent", "Parent", "password");
        childAccount = new ChildAccount("child", "Child", "password");
    }
    @Test
    void testEmptyFields() {
        AssignmentFrame assignmentFrame = new AssignmentFrame(accountManager, parentAccount, childAccount);
        assignmentFrame.taskNameField.setText("task");
        assignmentFrame.dueDateField.setText("");
        assignmentFrame.rewardField.setText("");
        assignmentFrame.descriptionArea.setText("");

        JButton submitButton = findButtonByText(assignmentFrame, "Submit");
        submitButton.doClick();

        assertFalse(isDialogVisible("Invalid Information"));
        assignmentFrame.dispose();
    }

    @Test
    public void testLongStrings() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            AssignmentFrame assignmentFrame = new AssignmentFrame(accountManager, parentAccount, childAccount);
            String longString = "a".repeat(1000);
            assignmentFrame.taskNameField.setText(longString);
            assignmentFrame.dueDateField.setText("2024-12-31");
            assignmentFrame.rewardField.setText("100");
            assignmentFrame.descriptionArea.setText(longString);

            JButton submitButton = findButtonByText(assignmentFrame, "Submit");
            submitButton.doClick();

            assertFalse(isDialogVisible("Invalid Information"), "Invalid Information dialog should not be visible");
            assertFalse(isDialogVisible("Invalid Date Format"), "Invalid Date Format dialog should not be visible");
            assignmentFrame.dispose();
        });
    }

    @Test
    public void testLargeReward() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            AssignmentFrame assignmentFrame = new AssignmentFrame(accountManager, parentAccount, childAccount);
            assignmentFrame.taskNameField.setText("Task");
            assignmentFrame.dueDateField.setText("2024-12-31");
            assignmentFrame.rewardField.setText(String.valueOf(Double.MAX_VALUE));
            assignmentFrame.descriptionArea.setText("Description");

            JButton submitButton = findButtonByText(assignmentFrame, "Submit");
            submitButton.doClick();

            assertFalse(isDialogVisible("Invalid Information"), "Invalid Information dialog should not be visible");
            assertFalse(isDialogVisible("Invalid Date Format"), "Invalid Date Format dialog should not be visible");
            assignmentFrame.dispose();
        });
    }


    @Test
    public void testExtremeValidDate() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            AssignmentFrame assignmentFrame = new AssignmentFrame(accountManager, parentAccount, childAccount);
            assignmentFrame.taskNameField.setText("Task");
            assignmentFrame.dueDateField.setText("9999-12-31");
            assignmentFrame.rewardField.setText("100");
            assignmentFrame.descriptionArea.setText("Description");

            JButton submitButton = findButtonByText(assignmentFrame, "Submit");
            submitButton.doClick();

            assertFalse(isDialogVisible("Invalid Information"), "Invalid Information dialog should not be visible");
            assertFalse(isDialogVisible("Invalid Date Format"), "Invalid Date Format dialog should not be visible");
            assignmentFrame.dispose();
        });
    }

    private JButton findButtonByText(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                if (button.getText().equals(text)) {
                    return button;
                }
            } else if (c instanceof Container) {
                JButton button = findButtonByText((Container) c, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }

    private boolean isDialogVisible(String dialogTitle) {
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getTitle().equals(dialogTitle) && dialog.isVisible()) {
                    return true;
                }
            }
        }
        return false;
    }
}
