package ui.BalanceManagement;

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

    // 提交按钮的事件处理逻辑
    // 提交按钮的事件处理逻辑
    private void onSubmit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            // 检查输入的金额是否大于零
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid input: Please enter a positive amount.");
                return; // 如果输入无效，停止执行后续代码
            }
            // 检查账户余额是否足够
            if (amount > childAccount.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
                return; // 如果余额不足，停止执行后续代码
            }
            childAccount.withdraw(amount);
            // 保存更新后的账户数据
            accountManager.saveAccount(childAccount);
            JOptionPane.showMessageDialog(this, "Withdrawal successful!");
            balanceManagementFrame.updateLabels();  // 更新标签显示
            dispose();  // 关闭窗口
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }

}
