package ui;

import ui.template.BigButton;
import core.ChildAccount;
import core.AccountManager;

import javax.swing.*;
import java.awt.*;

public class WithdrawalFrame extends JFrame {

    private ChildAccount childAccount;
    private AccountManager accountManager;
    private BalanceManagementFrame balanceManagementFrame;
    private JTextField amountField;

    public WithdrawalFrame(ChildAccount childAccount, AccountManager accountManager, BalanceManagementFrame balanceManagementFrame) {
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        this.balanceManagementFrame = balanceManagementFrame;
        setTitle("Make a Withdrawal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("The goal's name:"));
        panel.add(new JTextField(15));
        panel.add(new JLabel("Amount:"));
        amountField = new JTextField(15);
        panel.add(amountField);

        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");

        panel.add(submitButton);
        panel.add(returnButton);

        // 添加事件监听器
        submitButton.addActionListener(e -> onSubmit());
        returnButton.addActionListener(e -> dispose());

        getContentPane().add(panel);
    }

    // 提交按钮的事件处理逻辑
    private void onSubmit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            childAccount.withdraw(amount);
            // 保存更新后的账户数据
            accountManager.saveAccount(childAccount);
            JOptionPane.showMessageDialog(this, "Withdrawal successful!");
            balanceManagementFrame.updateLabels();  // 更新标签显示
            dispose();  // 关闭窗口
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.");
        }
    }
}
