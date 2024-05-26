package ParentBoundaryTest;


import core.AccountManager;
import core.ParentAccount;
import data.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.parent.ParentUserCenterFrame;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ParentUserCenterFrameTest {
    private ParentUserCenterFrame parentUserCenterFrame;
    private AccountManager accountManager;
    private ParentAccount parentAccount;

    @BeforeEach
    void setUp() {
        DataManager dataManager = new DataManager();
        accountManager = new AccountManager(dataManager); // 在此示例中，我们不需要 DataManager
        parentAccount = new ParentAccount("parent1", "John Doe", "password");
        accountManager.saveAccount(parentAccount);
        parentUserCenterFrame = new ParentUserCenterFrame(accountManager, parentAccount);
    }

    @Test
    void testChangePassword() {
        // Test with correct old password, new password, and confirmation
        parentUserCenterFrame.oldPasswordField.setText("password");
        parentUserCenterFrame.newPasswordField1.setText("newPassword");
        parentUserCenterFrame.newPasswordField2.setText("newPassword");
        parentUserCenterFrame.validateAndChangePassword(accountManager, parentAccount);
        assertEquals("newPassword", parentAccount.getPassword());


        // Test with empty new password
        parentUserCenterFrame.oldPasswordField.setText("password");
        parentUserCenterFrame.newPasswordField1.setText("");
        parentUserCenterFrame.newPasswordField2.setText("newPassword");
        parentUserCenterFrame.validateAndChangePassword(accountManager, parentAccount);
        assertNotEquals("", parentAccount.getPassword());

        // Test with mismatched new passwords
        parentUserCenterFrame.oldPasswordField.setText("password");
        parentUserCenterFrame.newPasswordField1.setText("newPassword1");
        parentUserCenterFrame.newPasswordField2.setText("newPassword2");
        parentUserCenterFrame.validateAndChangePassword(accountManager, parentAccount);
        assertNotEquals("newPassword2", parentAccount.getPassword());
    }
}
