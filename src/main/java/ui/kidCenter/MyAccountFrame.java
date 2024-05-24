package ui.kidCenter;

import core.AccountManager;
import core.ChildAccount;
import core.SavingGoal;
import core.Task;
import ui.template.KidPageFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class MyAccountFrame extends KidPageFrame {
    private JLabel totalSavingsLabel;
    private JLabel currentBalanceLabel;
    private JTable goalsTable;
    private JTable tasksTable;
    private AccountManager accountManager;
    private ChildAccount childAccount;

    public MyAccountFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("My Account", accountManager, childAccount);

        this.accountManager = accountManager;
        this.childAccount = childAccount;

        // Add components
        addComponents();
    }

    private void addComponents() {
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add My Total Savings and Current Balance panel
        JPanel balancePanel = new JPanel(new GridBagLayout());
        balancePanel.setBackground(new Color(0xFFF0F5));
        balancePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints balanceGbc = new GridBagConstraints();

        JLabel avatarLabel = new JLabel(new ImageIcon("src/main/java/ui/template/icon_Kid.png")); // Placeholder for avatar icon
        avatarLabel.setPreferredSize(new Dimension(80, 80)); // Adjust size as needed
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(new Color(0xFFE4E1)); // Light pink background for avatar
        balanceGbc.gridx = 0;
        balanceGbc.gridy = 0;
        balanceGbc.gridheight = 2;
        balanceGbc.insets = new Insets(0, 0, 0, 10);
        balanceGbc.anchor = GridBagConstraints.NORTHWEST;
        balancePanel.add(avatarLabel, balanceGbc);

        totalSavingsLabel = new JLabel("My Total Savings: ");
        totalSavingsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalSavingsLabel.setForeground(Color.BLACK);
        // 获取储蓄值
        double savings = childAccount.getSavings();
        totalSavingsLabel.setText(String.format("My Total Savings: %.2f", savings));
        balanceGbc.gridx = 1;
        balanceGbc.gridy = 0;
        balanceGbc.gridheight = 1;
        balanceGbc.anchor = GridBagConstraints.WEST;
        balancePanel.add(totalSavingsLabel, balanceGbc);

        currentBalanceLabel = new JLabel("My Current Balance: ");
        currentBalanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentBalanceLabel.setForeground(Color.BLACK);
        // 获取余额值
        double balance = childAccount.getBalance();
        currentBalanceLabel.setText(String.format("My Current Balance: %.2f", balance));
        balanceGbc.gridy = 1;
        balancePanel.add(currentBalanceLabel, balanceGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(balancePanel, gbc);


        // Add Go to see my goals title
        JLabel goalsTitleLabel = new JLabel("Go to see my goals");
        goalsTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        goalsTitleLabel.setForeground(new Color(0xF868B0));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        lowerPanel.add(goalsTitleLabel, gbc);

        // 获取储蓄目标数据并填充表格
        List<SavingGoal> savingGoals = childAccount.getSavingGoals();
        Vector<String> goalsColumnNames = new Vector<>();
        goalsColumnNames.add("Goal's Name");
        goalsColumnNames.add("Description");
        goalsColumnNames.add("Money Amount");
        goalsColumnNames.add("Award");

        // 使用 DefaultTableModel 直接从 savingGoals 获取数据
        DefaultTableModel goalsTableModel = new DefaultTableModel(goalsColumnNames, 0);
        for (SavingGoal goal : savingGoals) {
            goalsTableModel.addRow(new Object[]{goal.getName(), goal.getDescription(), goal.getTargetAmount(), goal.getReward()});
        }
        goalsTable = new JTable(goalsTableModel);
        goalsTable.setFillsViewportHeight(true);
        goalsTable.setPreferredScrollableViewportSize(new Dimension(450, 100));
        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        lowerPanel.add(goalsScrollPane, gbc);

        // Add See More link for goals
        JLabel seeMoreGoalsLabel = new JLabel("See more...");
        seeMoreGoalsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        seeMoreGoalsLabel.setForeground(new Color(255, 105, 180));
        seeMoreGoalsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreGoalsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open MyGoalsFrame
                MyGoalsFrame myGoalsFrame = new MyGoalsFrame(accountManager, childAccount);
                myGoalsFrame.setVisible(true);
                dispose();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(seeMoreGoalsLabel, gbc);

        // Add Go to see my tasks title
        JLabel tasksTitleLabel = new JLabel("Go to see my tasks");
        tasksTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tasksTitleLabel.setForeground(new Color(0xF868B0));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        lowerPanel.add(tasksTitleLabel, gbc);

        // 获取任务数据并填充表格
        List<Task> tasks = childAccount.getTasks();
        Vector<String> tasksColumnNames = new Vector<>();
        tasksColumnNames.add("Task's Name");
        tasksColumnNames.add("Description");
        tasksColumnNames.add("Award");

        // 使用 DefaultTableModel 直接从 tasks 获取数据
        DefaultTableModel tasksTableModel = new DefaultTableModel(tasksColumnNames, 0);
        for (Task task : tasks) {
            tasksTableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getReward()});
        }
        tasksTable = new JTable(tasksTableModel);
        tasksTable.setFillsViewportHeight(true);
        tasksTable.setPreferredScrollableViewportSize(new Dimension(450, 100));
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        lowerPanel.add(tasksScrollPane, gbc);

        // Add See More link for tasks
        JLabel seeMoreTasksLabel = new JLabel("See more...");
        seeMoreTasksLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        seeMoreTasksLabel.setForeground(new Color(255, 105, 180));
        seeMoreTasksLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreTasksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open MyTasksFrame
                MyTasksFrame myTasksFrame = new MyTasksFrame(accountManager, childAccount);
                myTasksFrame.setVisible(true);
                dispose();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(seeMoreTasksLabel, gbc);

        // Adjust frame
        pack();
        setLocationRelativeTo(null);
    }
}