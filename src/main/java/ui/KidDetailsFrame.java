package ui;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
        lowerPanel.setBackground(Color.WHITE);

        addTitleAndInfoPanel();
        setupGoalsSection();
        setupTasksSection();

        this.contentPanel.setBackground(Color.WHITE);
        this.contentPanel.add(lowerPanel, BorderLayout.CENTER);

        BigButton backButton = new BigButton("Return");
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            KidsListManagementFrame kidsListManagementFrame = new KidsListManagementFrame(accountManager, parentAccount);
            kidsListManagementFrame.setVisible(true);
            dispose();
        });
        lowerPanel.add(backButton, BorderLayout.SOUTH);
    }

    private void addTitleAndInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name: " + childAccount.getUsername());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setBackground(Color.WHITE);
        JLabel savingsLabel = new JLabel("Total Savings: " + childAccount.getSavings());
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        savingsLabel.setBackground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(savingsLabel);

        lowerPanel.add(infoPanel, BorderLayout.NORTH);
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
        goalsTable.setGridColor(Color.WHITE);
        goalsTable.setShowGrid(true);

        childAccount.getSavingGoals().forEach(goal -> {
            double progress = (childAccount.getSavings() / goal.getTargetAmount()) * 100;
            String progressStr = String.format("%.1f%%", progress);
            goalsModel.addRow(new Object[]{goal.getName(), goal.getDescription(), goal.getTargetAmount(), goal.getReward(), progressStr});
        });

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        goalsScrollPane.getViewport().setBackground(Color.WHITE);

        goalsPanel.add(goalsLabel, BorderLayout.NORTH);
        goalsPanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(goalsPanel, BorderLayout.WEST);
    }

    private void setupTasksSection() {
        JPanel tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.setBackground(Color.WHITE);
        JLabel tasksLabel = new JLabel("Tasks");
        tasksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksLabel.setForeground(new Color(255, 105, 180));
        tasksLabel.setBackground(Color.WHITE);

        String[] taskColumns = {"Task's Name", "Description", "Award", "Due Date"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        tasksTable = new JTable(tasksModel);
        tasksTable.setBackground(Color.WHITE);
        tasksTable.setGridColor(Color.WHITE);
        tasksTable.setShowGrid(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EEEE", Locale.ENGLISH);
        childAccount.getTasks().forEach(task -> {
            String formattedDueDate = dateFormat.format(task.getDueDate());
            tasksModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getReward(), formattedDueDate});
        });

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tasksScrollPane.getViewport().setBackground(Color.WHITE);

        tasksPanel.add(tasksLabel, BorderLayout.NORTH);
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tasksPanel, BorderLayout.EAST);
    }
}
