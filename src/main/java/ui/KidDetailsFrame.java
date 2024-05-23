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

        addTitleAndInfoPanel();
        setupGoalsSection();
        setupTasksSection();
        addBottomButtons();

        this.contentPanel.add(lowerPanel, BorderLayout.CENTER);
    }

    private void addTitleAndInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS)); // Horizontal layout for name and savings
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = new JLabel("Name: " + childAccount.getUsername());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel savingsLabel = new JLabel("Total Savings: " + childAccount.getSavings());
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createHorizontalGlue()); // This will push the savings label to the right
        infoPanel.add(savingsLabel);

        lowerPanel.add(infoPanel, BorderLayout.NORTH); // Add infoPanel to the lowerPanel

    }

    private void setupGoalsSection() {
        JPanel goalsPanel = new JPanel(new BorderLayout());
        JLabel goalsLabel = new JLabel("Goals");
        goalsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalsLabel.setForeground(new Color(255, 105, 180));

        String[] goalColumns = {"Goal's Name", "Description", "Money Amount", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        goalsTable = new JTable(goalsModel);
        goalsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        goalsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), goalsTable, "goal"));

        // Load goals from childAccount and add to table
        childAccount.getSavingGoals().forEach(goal -> {
            double progress = (childAccount.getSavings() / goal.getTargetAmount()) * 100;
            String progressStr = String.format("%.1f%%", progress);
            goalsModel.addRow(new Object[]{goal.getName(), goal.getDescription(), goal.getTargetAmount(), goal.getReward(), progressStr, ""});
        });

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);

        goalsPanel.add(goalsLabel, BorderLayout.NORTH);
        goalsPanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(goalsPanel, BorderLayout.WEST); // Add goalsPanel to the lowerPanel

    }

    private void setupTasksSection() {
        JPanel tasksPanel = new JPanel(new BorderLayout());
        JLabel tasksLabel = new JLabel("Tasks");
        tasksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksLabel.setForeground(new Color(255, 105, 180));

        String[] taskColumns = {"Task's Name", "Description", "Award", "Operation"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        tasksTable = new JTable(tasksModel);
        tasksTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tasksTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), tasksTable, "task"));

        childAccount.getTasks().forEach(task -> {
            tasksModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getReward(), ""});
        });

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);

        tasksPanel.add(tasksLabel, BorderLayout.NORTH);
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tasksPanel, BorderLayout.EAST); // Add tasksPanel to the lowerPanel
    }

    private void addBottomButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        BigButton setGoalButton = new BigButton("Set a Goal");
//        setGoalButton.setFont(new Font("Arial", Font.BOLD, 16));
//        setGoalButton.setForeground(new Color(255, 105, 180));
        setGoalButton.addActionListener(e -> openSetGoalFrame());
        buttonPanel.add(setGoalButton);

        BigButton assignTaskButton = new BigButton("Assign a Task");
//        assignTaskButton.setFont(new Font("Arial", Font.BOLD, 16));
//        assignTaskButton.setForeground(new Color(255, 105, 180));
        assignTaskButton.addActionListener(e -> openAssignTaskFrame());
        buttonPanel.add(assignTaskButton);

        lowerPanel.add(buttonPanel, BorderLayout.SOUTH); // Add buttonPanel to the lowerPanel
    }

    private void openSetGoalFrame() {
        GoalFrame goalFrame = new GoalFrame(accountManager, parentAccount, null); // 创建 GoalFrame 实例
        goalFrame.setVisible(true); // 显示 GoalFrame
    }

    private void openAssignTaskFrame() {
        AssignmentFrame taskFrame = new AssignmentFrame(accountManager, parentAccount, null); // 创建 GoalFrame 实例
        taskFrame.setVisible(true); // 显示 GoalFrame
    }


    static class ButtonRenderer extends JPanel implements TableCellRenderer {
        BigButton editButton, moveButton;

        public ButtonRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS)); // Use BoxLayout for horizontal alignment
            editButton = new BigButton("Edit");
            moveButton = new BigButton("Move");

            add(editButton);
            add(Box.createRigidArea(new Dimension(5, 0))); // Add space between buttons
            add(moveButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            // Ensure all buttons are visible
            editButton.setVisible(true);
            moveButton.setVisible(true);
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected BigButton editButton, moveButton;
        private JTable table;
        private String type;

        public ButtonEditor(JCheckBox checkBox, JTable table, String type) {
            super(checkBox);
            this.table = table;
            this.type = type;
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS)); // Use BoxLayout for horizontal alignment
            editButton = new BigButton("Edit");
            moveButton = new BigButton("Move");

            panel.add(editButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0))); // Add space between buttons
            panel.add(moveButton);

            editButton.addActionListener(e -> editItem());
            moveButton.addActionListener(e -> moveItem());
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
            String name = (String) model.getValueAt(row, 0);
            String description = (String) model.getValueAt(row, 1);
            String moneyAmount = (String) model.getValueAt(row, 2);
            String award = (String) model.getValueAt(row, 3);
            String progress = (String) model.getValueAt(row, 4);

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
            String name = (String) model.getValueAt(row, 0);
            String description = (String) model.getValueAt(row, 1);
            String award = (String) model.getValueAt(row, 2);

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
    }
}