package ui;

import core.AccountManager;
import core.ParentAccount;
import core.ChildAccount;
import core.SavingGoal;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

public class ManageGoalsFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;

    private DefaultTableModel goalsModel;
    private JTable goalsTable;
    private JLabel savingsLabel;

    public ManageGoalsFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Manage Kid's Goals", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));

        selectFirstChildAccount();
        addInfoPanel();
        addGoalsListPanel();
        addGoalsTable();

        setVisible(true);
    }

    private void selectFirstChildAccount() {
        Set<String> childAccountIds = parentAccount.getChildAccountIds();
        if (!childAccountIds.isEmpty()) {
            String firstChildAccountId = childAccountIds.iterator().next();
            this.childAccount = (ChildAccount) accountManager.getAccount(firstChildAccountId);
        }
    }

    private void addInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = new JLabel("Name: " + (childAccount != null ? childAccount.getUsername() : ""));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        savingsLabel = new JLabel("Total Savings: " + (childAccount != null ? childAccount.getSavings() : 0));
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(savingsLabel);

        lowerPanel.add(infoPanel);
    }

    private void addGoalsListPanel() {
        JPanel goalsListPanel = new JPanel();
        goalsListPanel.setLayout(new BorderLayout());
        goalsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel goalsListLabel = new JLabel("Goals List");
        goalsListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalsListLabel.setForeground(new Color(255, 105, 180));

        BigButton setGoalButton = new BigButton("+ Set a Goal");
        setGoalButton.setFont(new Font("Arial", Font.PLAIN, 14));
        setGoalButton.addActionListener(e -> openSetGoalFrame());
        goalsListPanel.add(goalsListLabel, BorderLayout.WEST);
        goalsListPanel.add(setGoalButton, BorderLayout.EAST);

        lowerPanel.add(goalsListPanel);
    }

    private void addGoalsTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] goalColumns = {"Goal's Name", "Description", "Target", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        goalsTable = new JTable(goalsModel);
        goalsTable.setRowHeight(30);

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        tablePanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);

        updateGoalsTable();
    }

    private void updateGoalsTable() {
        goalsModel.setRowCount(0); // Clear existing rows
        if (childAccount != null) {
            for (SavingGoal goal : childAccount.getSavingGoals()) {
                double progress = (childAccount.getSavings() / goal.getTargetAmount()) * 100;
                goalsModel.addRow(new Object[]{
                        goal.getName(),
                        goal.getDescription(),
                        goal.getTargetAmount(),
                        goal.getReward(),
                        String.format("%.2f%%", progress),
                        "Edit Move"
                });
            }
        }
    }

    private void openSetGoalFrame() {
        GoalFrame goalFrame = new GoalFrame(accountManager, parentAccount, this);
        goalFrame.setVisible(true);
    }

    public void updateRow(String goalsName, String target, String award, String description) {
        goalsModel.addRow(new Object[]{goalsName, description, target, award, "", "Edit Move"});
    }
}
