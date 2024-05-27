package kid.BalanceManagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import core.ChildAccount;
import core.AccountManager;
import data.DataManager; // 假设有一个 DataManager 类
import ui.kid.BalanceManagement.WithdrawalFrame;

public class WithdrawalFrameTest {

    private ChildAccount childAccount;
    private AccountManager accountManager;

    @BeforeEach
    public void setUp() {
        DataManager dataManager = new DataManager(); // 假设有一个 DataManager 类
        accountManager = new AccountManager(dataManager);
        childAccount = new ChildAccount("testUser", "password", "testAccountId");
    }

    @Test
    public void testInvalidAmount_Zero() {
        // 设置账户余额
        childAccount.deposit(200);

        // 创建WithdrawalFrame实例
        WithdrawalFrame withdrawalFrame = new WithdrawalFrame(childAccount, accountManager, null);
        withdrawalFrame.amountField.setText("0");

        // 模拟点击提交按钮
        withdrawalFrame.onSubmit();

        // 检查余额是否保持不变
        assertEquals(200, childAccount.getBalance());
    }

    @Test
    public void testInvalidAmount_Negative() {
        // 设置账户余额
        childAccount.deposit(200);

        // 创建WithdrawalFrame实例
        WithdrawalFrame withdrawalFrame = new WithdrawalFrame(childAccount, accountManager, null);
        withdrawalFrame.amountField.setText("-100");

        // 模拟点击提交按钮
        withdrawalFrame.onSubmit();

        // 检查余额是否保持不变
        assertEquals(200, childAccount.getBalance());
    }

    @Test
    public void testInvalidAmount_Format() {
        // 设置账户余额
        childAccount.deposit(200);

        // 创建WithdrawalFrame实例
        WithdrawalFrame withdrawalFrame = new WithdrawalFrame(childAccount, accountManager, null);
        withdrawalFrame.amountField.setText("abc");

        // 模拟点击提交按钮
        withdrawalFrame.onSubmit();

        // 检查余额是否保持不变
        assertEquals(200, childAccount.getBalance());
    }

    @Test
    public void testInsufficientBalance() {
        // 设置账户余额
        childAccount.deposit(100);

        // 创建WithdrawalFrame实例
        WithdrawalFrame withdrawalFrame = new WithdrawalFrame(childAccount, accountManager, null);
        withdrawalFrame.amountField.setText("200");

        // 模拟点击提交按钮
        withdrawalFrame.onSubmit();

        // 检查余额是否保持不变
        assertEquals(100, childAccount.getBalance());
    }
}
