package ui;
import core.AccountManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import ui.GoalFrame;

import core.ParentAccount;
import core.ChildAccount;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

public class KidDetailsFrame extends ParentPageFrame {
    private static AccountManager accountManager;
    private static ParentAccount parentAccount;
    private static ChildAccount childAccount;
    private static DefaultTableModel goalsModel;
    private DefaultTableModel tasksModel;
    private JTable goalsTable;
    private JTable tasksTable;

    public KidDetailsFrame(AccountManager accountManager, ParentAccount parentAccount, ChildAccount childAccount) {
        super("Details of Kid's Account", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        this.childAccount = childAccount;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS)); // Set vertical box layout for lowerPanel
        lowerPanel.setBackground(Color.WHITE);

        addTitleAndInfoPanel();
        setupGoalsSection();
        setupTasksSection();

        this.contentPanel.setBackground(Color.WHITE);
        this.contentPanel.add(lowerPanel, BorderLayout.CENTER);

        // 添加返回按钮
        BigButton backButton = new BigButton("Return");
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            // 在这里添加返回上一页面的代码
            // 例如,如果上一页面是 KidsListManagementFrame 的实例:
            KidsListManagementFrame kidsListManagementFrame = new KidsListManagementFrame(accountManager, parentAccount);
            kidsListManagementFrame.setVisible(true);
            dispose(); // 关闭当前页面
        });
        lowerPanel.add(backButton, BorderLayout.SOUTH);
    }

    private void addTitleAndInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS)); // Horizontal layout for name and savings
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name: " + childAccount.getUsername());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setBackground(Color.WHITE);
        JLabel savingsLabel = new JLabel("Total Savings: " + childAccount.getSavings());
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        savingsLabel.setBackground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createHorizontalGlue()); // This will push the savings label to the right
        infoPanel.add(savingsLabel);

        lowerPanel.add(infoPanel, BorderLayout.NORTH); // Add infoPanel to the lowerPanel
    }

    private void setupGoalsSection() {
        JPanel goalsPanel = new JPanel(new BorderLayout());
        goalsPanel.setBackground(Color.WHITE);
        JLabel goalsLabel = new JLabel("Goals");
        goalsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalsLabel.setForeground(new Color(255, 105, 180));
        goalsLabel.setBackground(Color.WHITE);

        String[] goalColumns = {"Goal's Name", "Description", "Money Amount", "Award", "Progress"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        goalsTable = new JTable(goalsModel);
        goalsTable.setBackground(Color.WHITE);
        goalsTable.setGridColor(Color.WHITE);  // Set grid color to white to blend with background
        goalsTable.setShowGrid(true);

        // Load goals from childAccount and add to table
        childAccount.getSavingGoals().forEach(goal -> {
            double progress = (childAccount.getSavings() / goal.getTargetAmount()) * 100;
            String progressStr = String.format("%.1f%%", progress);
            goalsModel.addRow(new Object[]{goal.getName(), goal.getDescription(), goal.getTargetAmount(), goal.getReward(), progressStr, ""});
        });

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        goalsScrollPane.getViewport().setBackground(Color.WHITE);

        goalsPanel.add(goalsLabel, BorderLayout.NORTH);
        goalsPanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(goalsPanel, BorderLayout.WEST); // Add goalsPanel to the lowerPanel
    }

    private void setupTasksSection() {
        JPanel tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.setBackground(Color.WHITE);
        JLabel tasksLabel = new JLabel("Tasks");
        tasksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksLabel.setForeground(new Color(255, 105, 180));
        tasksLabel.setBackground(Color.WHITE);

        String[] taskColumns = {"Task's Name", "Description", "Award","deadline"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        tasksTable = new JTable(tasksModel);
        tasksTable.setBackground(Color.WHITE);
        tasksTable.setGridColor(Color.WHITE);  // Set grid color to white to blend with background
        tasksTable.setShowGrid(true);

        childAccount.getTasks().forEach(task -> {
            tasksModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getReward(), ""});
        });

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tasksScrollPane.getViewport().setBackground(Color.WHITE);

        tasksPanel.add(tasksLabel, BorderLayout.NORTH);
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tasksPanel, BorderLayout.EAST); // Add tasksPanel to the lowerPanel
    }
}

