package ui.BalanceManagement;

import ui.template.BigButton;
import core.ChildAccount;
import core.AccountManager;

import javax.swing.*;
import java.awt.*;

public class SaveGoalsFrame extends JFrame {

    private ChildAccount childAccount;
    private AccountManager accountManager;
    private BalanceManagementFrame balanceManagementFrame;
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
        // 使用 BoxLayout 来创建一个垂直布局的主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建一个用于输入金额的面板
        JPanel amountPanel = new JPanel(new BorderLayout(10, 10));
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(15);
        amountPanel.add(amountLabel, BorderLayout.WEST);
        amountPanel.add(amountField, BorderLayout.CENTER);
        mainPanel.add(amountPanel);

        // 在金额面板和按钮面板之间添加垂直间距
        mainPanel.add(Box.createVerticalStrut(20));

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");
        buttonPanel.add(submitButton);
        buttonPanel.add(returnButton);
        mainPanel.add(buttonPanel);

        // 添加事件监听器到submitButton
        submitButton.addActionListener(e -> onSubmit());
        returnButton.addActionListener(e -> dispose());

        // 将主面板添加到框架
        getContentPane().add(mainPanel);
    }

    // 处理提交按钮点击事件的方法
    private void onSubmit() {
        try {
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
