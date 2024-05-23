package ui;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.Task;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Set;

public class ManageTasksFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;

    private DefaultTableModel tasksModel;
    private JTable tasksTable;
    private JComboBox<String> childAccountComboBox;
    private JComboBox<String> savingsComboBox;

    public ManageTasksFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Manage Kid's Tasks", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));

        selectFirstChildAccount();
        addInfoPanel();
        addTasksListPanel();
        addTasksTable();

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
        savingsComboBox = new JComboBox<>();

        Set<String> childAccountIds = parentAccount.getChildAccountIds();
        for (String childAccountId : childAccountIds) {
            ChildAccount account = (ChildAccount) accountManager.getAccount(childAccountId);
            childAccountComboBox.addItem(account.getUsername());
            savingsComboBox.addItem(String.valueOf(account.getSavings()));
        }

        childAccountComboBox.addActionListener(e -> {
            String selectedChildUsername = (String) childAccountComboBox.getSelectedItem();
            if (selectedChildUsername != null) {
                childAccount = (ChildAccount) accountManager.getAccountByUsername(selectedChildUsername);
                savingsComboBox.setSelectedItem(String.valueOf(childAccount.getSavings()));
                updateTasksTable();
            }
        });

        infoPanel.add(new JLabel("Name: "));
        infoPanel.add(childAccountComboBox);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(new JLabel("Total Savings: "));
        infoPanel.add(savingsComboBox);

        lowerPanel.add(infoPanel);
    }

    private void addTasksListPanel() {
        JPanel tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BorderLayout());
        tasksListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel tasksListLabel = new JLabel("Tasks List");
        tasksListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksListLabel.setForeground(new Color(255, 105, 180));

        BigButton setTaskButton = new BigButton("+ Assign a Task");
        setTaskButton.setFont(new Font("Arial", Font.PLAIN, 14));
        setTaskButton.addActionListener(e -> openSetTaskFrame());
        tasksListPanel.add(tasksListLabel, BorderLayout.WEST);
        tasksListPanel.add(setTaskButton, BorderLayout.EAST);

        lowerPanel.add(tasksListPanel);
    }

    private void addTasksTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] taskColumns = {"Task's Name", "Description", "Award", "Operation"};
        tasksModel = new DefaultTableModel(null, taskColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only operation column is editable
            }
        };
        tasksTable = new JTable(tasksModel);
        tasksTable.setRowHeight(30);
        tasksTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tasksTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(tasksTable, "task"));

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tablePanel.add(tasksScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);
        updateTasksTable();
    }

    private void updateTasksTable() {
        tasksModel.setRowCount(0);
        if (childAccount != null) {
            for (Task task : childAccount.getTasks()) {
                tasksModel.addRow(new Object[]{
                        task.getName(),
                        task.getDescription(),
                        task.getReward(),
                        ""
                });
            }
        }
    }

    private void openSetTaskFrame() {
        AssignmentFrame taskFrame = new AssignmentFrame(accountManager, parentAccount, this);
        taskFrame.setVisible(true);
    }

    public void updateRow(String taskName, String description, String award, String s) {
        tasksModel.addRow(new Object[]{taskName, description, award, ""});
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
                editTask(row, model);
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
