package ui.BalanceManagement;

import ui.template.BigButton;
import core.ChildAccount;
import core.AccountManager;

import javax.swing.*;
import java.awt.*;

public class DepositFrame extends JFrame {

    private ChildAccount childAccount;
    private AccountManager accountManager;
    private BalanceManagementFrame balanceManagementFrame;
    private JTextField amountField;

    public DepositFrame(ChildAccount childAccount, AccountManager accountManager, BalanceManagementFrame balanceManagementFrame) {
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        this.balanceManagementFrame = balanceManagementFrame;
        setTitle("Make a Deposit");
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
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(15);
        inputPanel.add(amountLabel, BorderLayout.WEST);
        inputPanel.add(amountField, BorderLayout.CENTER);

        mainPanel.add(inputPanel);

        // 在输入面板和按钮面板之间添加垂直间距
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

    // 这个方法将在点击Submit按钮时被调用
    // 这个方法将在点击Submit按钮时被调用
    private void onSubmit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            // 检查输入的金额是否大于零
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid input: Please enter a positive amount.");
                return; // 如果输入无效，停止执行后续代码
            }
            childAccount.deposit(amount);
            // 保存更新后的账户数据
            accountManager.saveAccount(childAccount);
            JOptionPane.showMessageDialog(this, "Deposit successful!");
            balanceManagementFrame.updateLabels();  // 更新标签显示
            dispose();  // 关闭窗口
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }
}
