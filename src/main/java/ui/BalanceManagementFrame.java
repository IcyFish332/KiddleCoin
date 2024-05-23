package ui;

import ui.template.KidPageFrame;
import core.ChildAccount;
import core.AccountManager;
import data.DataManager;

import javax.swing.*;
import java.awt.*;

public class BalanceManagementFrame extends KidPageFrame {
    private ChildAccount childAccount;
    private AccountManager accountManager;
    private JLabel totalSavingsLabel;
    private JLabel currentBalanceLabel;

    public BalanceManagementFrame(ChildAccount childAccount, AccountManager accountManager) {
        super("Manage My Balance", null, childAccount);
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new GridLayout(1, 2));
        balancePanel.setBackground(Color.WHITE);

        totalSavingsLabel = new JLabel("My total savings: $" + childAccount.getSavings());
        totalSavingsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        totalSavingsLabel.setForeground(new Color(0xF868B0));  // 设置粉红色字体
        totalSavingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balancePanel.add(totalSavingsLabel);

        currentBalanceLabel = new JLabel("My current balance: $" + childAccount.getBalance());
        currentBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        currentBalanceLabel.setForeground(new Color(0xF868B0));  // 设置粉红色字体
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balancePanel.add(currentBalanceLabel);

        panel.add(balancePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // 增加内容与按钮间的间距

        // Create and style buttons like BigButton and add action listeners for each
        JButton depositButton = createStyledButton("I want to deposit money");
        depositButton.addActionListener(e -> {
            DepositFrame depositFrame = new DepositFrame(childAccount, accountManager, this);
            depositFrame.setVisible(true);
        });
        panel.add(depositButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // 增加按钮间的间距

        JButton withdrawButton = createStyledButton("I want to withdraw money");
        withdrawButton.addActionListener(e -> {
            WithdrawalFrame withdrawalFrame = new WithdrawalFrame(childAccount, accountManager, this);
            withdrawalFrame.setVisible(true);
        });
        panel.add(withdrawButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // 增加按钮间的间距

        JButton saveGoalsButton = createStyledButton("Save money into my goals");
        saveGoalsButton.addActionListener(e -> {
            SaveGoalsFrame saveGoalsFrame = new SaveGoalsFrame(childAccount, accountManager, this);
            saveGoalsFrame.setVisible(true);
        });
        panel.add(saveGoalsButton);

        // 增加底部间距
        panel.add(Box.createRigidArea(new Dimension(0, 40)));  // 调整这个值来增加或减少间距

        // 添加自定义面板到 lowerPanel
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(panel, BorderLayout.CENTER);
    }

    public void updateLabels() {
        totalSavingsLabel.setText("My total savings: $" + childAccount.getSavings());
        currentBalanceLabel.setText("My current balance: $" + childAccount.getBalance());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int arcWidth = 20;
                int arcHeight = 20;

                // 绘制背景
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

                // 绘制文本
                g2d.setColor(getForeground());
                FontMetrics metrics = g2d.getFontMetrics(getFont());
                int textX = (width - metrics.stringWidth(getText())) / 2;
                int textY = (height - metrics.getHeight()) / 2 + metrics.getAscent();
                g2d.drawString(getText(), textX, textY);

                g2d.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int arcWidth = 20;
                int arcHeight = 20;

                g2d.setColor(new Color(0xF868B0));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, arcWidth, arcHeight);

                g2d.dispose();
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(new Color(0xF868B0));
        button.setBackground(new Color(0xFFEDF0));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 40));
        button.setPreferredSize(new Dimension(300, 40));
        button.setMinimumSize(new Dimension(300, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xFFCCE5));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xFFEDF0));
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建一个示例的ChildAccount对象
            ChildAccount childAccount = new ChildAccount("child1", "John", "password123");
            // 创建DataManager对象
            DataManager dataManager = new DataManager();
            // 创建AccountManager对象
            AccountManager accountManager = new AccountManager(dataManager);
            // 设置初始的balance和savings
            childAccount.setBalance(250);
            childAccount.setSavings(520);
            // 保存初始化的账户数据
            accountManager.saveAccount(childAccount);

            // 创建BalanceManagementFrame实例并传递ChildAccount和AccountManager对象
            BalanceManagementFrame frame = new BalanceManagementFrame(childAccount, accountManager);
            frame.setVisible(true);
        });
    }
}