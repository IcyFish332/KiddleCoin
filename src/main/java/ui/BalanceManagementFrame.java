package ui;

import javax.swing.*;
import java.awt.*;

public class BalanceManagementFrame extends JFrame {
    private JLabel totalSavingsLabel;
    private JLabel currentBalanceLabel;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton saveGoalsButton;

    public BalanceManagementFrame() {
        setTitle("Manage My Balance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Manage my balance");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(headerLabel);

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new GridLayout(1, 2));
        balancePanel.setBackground(Color.WHITE);

        totalSavingsLabel = new JLabel("My total savings: $520");
        totalSavingsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        balancePanel.add(totalSavingsLabel);

        currentBalanceLabel = new JLabel("My current balance: $250");
        currentBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        balancePanel.add(currentBalanceLabel);

        panel.add(balancePanel);

        depositButton = new JButton("I want to deposit money");
        depositButton.setFont(new Font("Arial", Font.PLAIN, 16));
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositButton.addActionListener(e -> {
            DepositFrame depositFrame = new DepositFrame();
            depositFrame.setVisible(true);
        });
        panel.add(depositButton);

        withdrawButton = new JButton("I want to withdraw money");
        withdrawButton.setFont(new Font("Arial", Font.PLAIN, 16));
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawButton.addActionListener(e -> {
            WithdrawalFrame withdrawalFrame = new WithdrawalFrame();
            withdrawalFrame.setVisible(true);
        });
        panel.add(withdrawButton);

        saveGoalsButton = new JButton("Save money into my goals");
        saveGoalsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveGoalsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveGoalsButton.addActionListener(e -> {
            SaveGoalsFrame saveGoalsFrame = new SaveGoalsFrame();
            saveGoalsFrame.setVisible(true);
        });
        panel.add(saveGoalsButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BalanceManagementFrame frame = new BalanceManagementFrame();
            frame.setVisible(true);
        });
    }
}
