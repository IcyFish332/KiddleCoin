package ui;

import ui.template.BigButton;
import core.ChildAccount;
import core.AccountManager;

import javax.swing.*;
import java.awt.*;

public class SaveGoalsFrame extends JFrame {

    private ChildAccount childAccount;
    private AccountManager accountManager;
    private BalanceManagementFrame balanceManagementFrame;
    private JTextField goalNameField;
    private JTextField amountField;

    public SaveGoalsFrame(ChildAccount childAccount, AccountManager accountManager, BalanceManagementFrame balanceManagementFrame) {
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        this.balanceManagementFrame = balanceManagementFrame;
        setTitle("Save Money into My Goals");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("The goal's name:"));
        goalNameField = new JTextField(15);
        panel.add(goalNameField);
        panel.add(new JLabel("Amount:"));
        amountField = new JTextField(15);
        panel.add(amountField);

        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");

        panel.add(submitButton);
        panel.add(returnButton);

        // 添加事件监听器，用于处理返回按钮的点击事件
        returnButton.addActionListener(e -> dispose());

        // 添加提交按钮的事件监听器
        submitButton.addActionListener(e -> onSubmit());

        getContentPane().add(panel);
    }

    // 处理提交按钮点击事件的方法
    private void onSubmit() {
        try {
            String goalName = goalNameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            childAccount.saveMoney(amount);
            // 保存更新后的账户数据
            accountManager.saveAccount(childAccount);
            JOptionPane.showMessageDialog(this, "Money saved to goal successfully!");
            balanceManagementFrame.updateLabels();  // 更新标签显示
            dispose();  // 关闭窗口
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }
}
