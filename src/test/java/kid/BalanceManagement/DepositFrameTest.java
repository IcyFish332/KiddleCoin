package kid.BalanceManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JOptionPane;
import core.ChildAccount;
import core.AccountManager;
import data.DataManager;
import ui.kid.BalanceManagement.BalanceManagementFrame;
import ui.kid.BalanceManagement.DepositFrame;

public class DepositFrameTest {

    // 测试有效金额的存款操作
    @Test
    public void testValidDeposit() {
        // 创建 ChildAccount 对象
        ChildAccount childAccount = new ChildAccount("child01", "childUser", "password");

        // 创建 DataManager 对象并传递给 AccountManager
        DataManager dataManager = new DataManager();
        AccountManager accountManager = new AccountManager(dataManager);

        // 创建 BalanceManagementFrame 对象并传递给 DepositFrame
        BalanceManagementFrame balanceManagementFrame = new BalanceManagementFrame(childAccount, accountManager);

        // 创建 DepositFrame 对象，并将 ChildAccount 对象、AccountManager 对象和 BalanceManagementFrame 对象传递给构造函数
        DepositFrame depositFrame = new DepositFrame(childAccount, accountManager, balanceManagementFrame);

        // 模拟用户输入一个有效金额
        depositFrame.amountField.setText("100");

        // 模拟点击提交按钮
        depositFrame.onSubmit();

        // 检查是否显示了存款成功的消息框
        assertEquals(1, JOptionPane.getFrameForComponent(depositFrame).getComponents().length);
    }

    // 测试零金额的处理
    @Test
    public void testInvalidAmount_Zero() {
        // 模拟用户输入零金额
        DepositFrame depositFrame = new DepositFrame(null, null, null);
        depositFrame.amountField.setText("0");

        // 模拟点击提交按钮
        depositFrame.onSubmit();

        // 检查是否显示了无效输入的错误消息框
        assertEquals(1, JOptionPane.getFrameForComponent(depositFrame).getComponents().length);
    }

    // 测试负数金额的处理
    @Test
    public void testInvalidAmount_Negative() {
        // 模拟用户输入负数金额
        DepositFrame depositFrame = new DepositFrame(null, null, null);
        depositFrame.amountField.setText("-100");

        // 模拟点击提交按钮
        depositFrame.onSubmit();

        // 检查是否显示了无效输入的错误消息框
        assertEquals(1, JOptionPane.getFrameForComponent(depositFrame).getComponents().length);
    }

    // 测试无效金额格式的处理
    @Test
    public void testInvalidAmount_Format() {
        // 模拟用户输入无效的金额格式
        DepositFrame depositFrame = new DepositFrame(null, null, null);
        depositFrame.amountField.setText("abc");

        // 模拟点击提交按钮
        depositFrame.onSubmit();

        // 检查是否显示了无效输入的错误消息框
        assertEquals(1, JOptionPane.getFrameForComponent(depositFrame).getComponents().length);
    }
}
