package kid.BalanceManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import core.ChildAccount;
import core.AccountManager;
import data.DataManager; // 假设 DataManager 是 AccountManager 的依赖
import ui.kid.BalanceManagement.BalanceManagementFrame;
import ui.kid.BalanceManagement.SaveGoalsFrame;

public class SaveGoalsFrameTest {

    private ChildAccount childAccount;
    private AccountManager accountManager;
    private BalanceManagementFrame balanceManagementFrame;
    private SaveGoalsFrame saveGoalsFrame;

    @BeforeEach
    public void setUp() {
        // 假设 ChildAccount 需要三个字符串参数（用户名、密码、id）
        childAccount = new ChildAccount("testUser", "password", "id123");

        // 假设 AccountManager 需要一个 DataManager 实例
        DataManager dataManager = new DataManager();
        accountManager = new AccountManager(dataManager);

        // 创建一个 BalanceManagementFrame 实例
        balanceManagementFrame = new BalanceManagementFrame(childAccount, accountManager);

        // 初始化 SaveGoalsFrame
        saveGoalsFrame = new SaveGoalsFrame(childAccount, accountManager, balanceManagementFrame);
    }

    // 测试零金额的处理
    @Test
    public void testInvalidAmount_Zero() {
        // 模拟用户输入零金额
        saveGoalsFrame.amountField.setText("0");

        // 模拟点击提交按钮
        saveGoalsFrame.onSubmit();

        // 检查账户余额是否没有更新
        assertEquals(0, childAccount.getBalance());

        // 检查是否显示了无效输入的错误消息框
        assertEquals(1, JOptionPane.getFrameForComponent(saveGoalsFrame).getComponents().length);
    }

    // 测试负数金额的处理
    @Test
    public void testInvalidAmount_Negative() {
        // 模拟用户输入负数金额
        saveGoalsFrame.amountField.setText("-100");

        // 模拟点击提交按钮
        saveGoalsFrame.onSubmit();

        // 检查账户余额是否没有更新
        assertEquals(0, childAccount.getBalance());

        // 检查是否显示了无效输入的错误消息框
        assertEquals(1, JOptionPane.getFrameForComponent(saveGoalsFrame).getComponents().length);
    }

    // 测试无效金额格式的处理
    @Test
    public void testInvalidAmount_Format() {
        // 模拟用户输入无效的金额格式
        saveGoalsFrame.amountField.setText("abc");

        // 模拟点击提交按钮
        saveGoalsFrame.onSubmit();

        // 检查账户余额是否没有更新
        assertEquals(0, childAccount.getBalance());

        // 检查是否显示了无效输入的错误消息框
        assertEquals(1, JOptionPane.getFrameForComponent(saveGoalsFrame).getComponents().length);
    }
}
