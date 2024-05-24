package ui;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.SavingGoal;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;
import java.util.Set;

public class ManageGoalsFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;

    private DefaultTableModel goalsModel;
    private JTable goalsTable;
    private JComboBox<String> childAccountComboBox;
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

        childAccountComboBox = new JComboBox<>();
        savingsLabel = new JLabel();

        Set<String> childAccountIds = parentAccount.getChildAccountIds();
        for (String childAccountId : childAccountIds) {
            ChildAccount account = (ChildAccount) accountManager.getAccount(childAccountId);
            childAccountComboBox.addItem(account.getUsername());
        }

        childAccountComboBox.addActionListener(e -> {
            String selectedChildUsername = (String) childAccountComboBox.getSelectedItem();
            if (selectedChildUsername != null) {
                childAccount = (ChildAccount) accountManager.getAccountByUsername(selectedChildUsername);
                savingsLabel.setText(String.valueOf(childAccount.getSavings()));
                updateGoalsTable();
            }
        });

        // Set the initial value of the savings label
        if (childAccount != null) {
            savingsLabel.setText(String.valueOf(childAccount.getSavings()));
        }

        infoPanel.add(new JLabel("Name: "));
        infoPanel.add(childAccountComboBox);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(new JLabel("Total Savings: "));
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
        goalsModel = new DefaultTableModel(null, goalColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the Operation column is editable
            }
        };
        goalsTable = new JTable(goalsModel);
        goalsTable.setRowHeight(30);
        goalsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        goalsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        tablePanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);
        updateGoalsTable();
    }

    private void updateGoalsTable() {
        goalsModel.setRowCount(0);
        if (childAccount != null) {
            for (SavingGoal goal : childAccount.getSavingGoals()) {
                double progress = (childAccount.getSavings() / goal.getTargetAmount()) * 100;
                goalsModel.addRow(new Object[]{
                        goal.getName(),
                        goal.getDescription(),
                        goal.getTargetAmount(),
                        goal.getReward(),
                        String.format("%.2f%%", progress),
                        "Edit/Delete"
                });
            }
        }
    }

    private void openSetGoalFrame() {
        GoalFrame goalFrame = new GoalFrame(accountManager, parentAccount, childAccount);
        goalFrame.setVisible(true);
        this.dispose(); // 关闭当前窗口
    }

    private void editGoal(int row) {
        SavingGoal goal = childAccount.getSavingGoals().get(row);
        GoalFrame goalFrame = new GoalFrame(accountManager, parentAccount, childAccount, goal);
        goalFrame.setVisible(true);
        this.dispose(); // 关闭当前窗口
    }

    private void deleteGoal(int row) {
        if (row >= 0 && row < goalsModel.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SavingGoal goalToRemove = childAccount.getSavingGoals().get(row);
                childAccount.removeSavingGoal(goalToRemove);
                accountManager.saveAccount(childAccount);
                updateGoalsTable();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid row selected for deletion.");
        }
    }

    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");

            editButton.addActionListener(e -> editGoal(row));
            deleteButton.addActionListener(e -> deleteGoal(row));

            panel.add(editButton);
            panel.add(deleteButton);
            return panel;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private int currentRow;

        public ButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            editButton.addActionListener(e -> editGoal(currentRow));
            deleteButton.addActionListener(e -> deleteGoal(currentRow));

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row; // 保存当前行索引
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return true;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }
    }
}
