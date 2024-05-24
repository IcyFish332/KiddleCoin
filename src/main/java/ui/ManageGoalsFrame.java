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
                return column == 5; // Only operation column is editable
            }
        };
        goalsTable = new JTable(goalsModel);
        goalsTable.setRowHeight(30);
        goalsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        goalsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(goalsTable, "goal"));

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
                        ""
                });
            }
        }
    }

    private void openSetGoalFrame() {
        GoalFrame goalFrame = new GoalFrame(accountManager, parentAccount, childAccount);
        goalFrame.setVisible(true);
    }

    static class ButtonRenderer extends JPanel implements TableCellRenderer {
        protected JButton editButton, moveButton;

        public ButtonRenderer() {
            super(new FlowLayout(FlowLayout.LEFT));
            editButton = new JButton("Edit");
            moveButton = new JButton("Move");
            add(editButton);
            add(moveButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        protected JPanel panel;
        protected JButton editButton, moveButton;
        private JTable table;
        private String type;

        public ButtonEditor(JTable table, String type) {
            this.table = table;
            this.type = type;
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            editButton = new JButton("Edit");
            moveButton = new JButton("Move");

            editButton.addActionListener(e -> {
                fireEditingStopped(); // Make sure to fire event to stop editing
                editItem();
            });

            moveButton.addActionListener(e -> {
                fireEditingStopped(); // Ensure to fire event to stop editing
                moveItem();
            });

            panel.add(editButton);
            panel.add(moveButton);
        }

        private void editItem() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if ("goal".equals(type)) {
                    editGoal(row, model);
                } else {
                    editTask(row, model);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No item selected!");
            }
        }

        private void moveItem() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No item selected!");
            }
        }

        private void editGoal(int row, DefaultTableModel model) {
            String name = String.valueOf(model.getValueAt(row, 0));
            String description = String.valueOf(model.getValueAt(row, 1));
            String moneyAmount = String.valueOf(model.getValueAt(row, 2));
            String award = String.valueOf(model.getValueAt(row, 3));
            String progress = String.valueOf(model.getValueAt(row, 4));

            JTextField nameField = new JTextField(name);
            JTextField descriptionField = new JTextField(description);
            JTextField moneyAmountField = new JTextField(moneyAmount);
            JTextField awardField = new JTextField(award);
            JTextField progressField = new JTextField(progress);

            Object[] message = {
                    "Name:", nameField,
                    "Description:", descriptionField,
                    "Money Amount:", moneyAmountField,
                    "Award:", awardField,
                    "Progress:", progressField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit Goal", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                model.setValueAt(nameField.getText(), row, 0);
                model.setValueAt(descriptionField.getText(), row, 1);
                model.setValueAt(moneyAmountField.getText(), row, 2);
                model.setValueAt(awardField.getText(), row, 3);
                model.setValueAt(progressField.getText(), row, 4);
            }
        }

        private void editTask(int row, DefaultTableModel model) {
            String name = String.valueOf(model.getValueAt(row, 0));
            String description = String.valueOf(model.getValueAt(row, 1));
            String award = String.valueOf(model.getValueAt(row, 2));

            JTextField nameField = new JTextField(name);
            JTextField descriptionField = new JTextField(description);
            JTextField awardField = new JTextField(award);

            Object[] message = {
                    "Name:", nameField,
                    "Description:", descriptionField,
                    "Award:", awardField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit Task", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                model.setValueAt(nameField.getText(), row, 0);
                model.setValueAt(descriptionField.getText(), row, 1);
                model.setValueAt(awardField.getText(), row, 2);
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}

