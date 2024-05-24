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
import java.util.EventObject;
import java.util.Locale;
import java.util.Set;
import java.text.SimpleDateFormat;


public class ManageTasksFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;

    private DefaultTableModel tasksModel;
    private JTable tasksTable;
    private JComboBox<String> childAccountComboBox;
    private JLabel savingsLabel;

    public ManageTasksFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Manage Kid's Tasks", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
        lowerPanel.setBackground(Color.WHITE);

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
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        childAccountComboBox = new JComboBox<>();
        savingsLabel = new JLabel();
        savingsLabel.setBackground(Color.WHITE);

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
                updateTasksTable();
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

    private void addTasksListPanel() {
        JPanel tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BorderLayout());
        tasksListPanel.setBackground(Color.WHITE);
        tasksListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel tasksListLabel = new JLabel("Tasks List");
        tasksListLabel.setBackground(Color.WHITE);
        tasksListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksListLabel.setForeground(new Color(255, 105, 180));

        BigButton setTaskButton = new BigButton("+ Set a Task");
        setTaskButton.setFont(new Font("Arial", Font.PLAIN, 14));
        setTaskButton.addActionListener(e -> openSetTaskFrame());
        tasksListPanel.add(tasksListLabel, BorderLayout.WEST);
        tasksListPanel.add(setTaskButton, BorderLayout.EAST);

        lowerPanel.add(tasksListPanel);
    }

    private void addTasksTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        String[] taskColumns = {"Task's Name", "Description", "Award", "Due Date", "Operation"}; // Update column name to "Due Date"
        tasksModel = new DefaultTableModel(null, taskColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the Operation column is editable
            }
        };
        tasksTable = new JTable(tasksModel);
        tasksTable.setRowHeight(30);
        tasksTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer()); // Update column index
        tasksTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor()); // Update column index

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tablePanel.add(tasksScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);
        updateTasksTable();
    }


    private void updateTasksTable() {
        tasksModel.setRowCount(0);
        if (childAccount != null) {
            for (Task task : childAccount.getTasks()) {
                // Format the date as "Year-Month-Day (Day of the Week)"
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EEEE", Locale.ENGLISH);
                String formattedDate = dateFormat.format(task.getDueDate());

                tasksModel.addRow(new Object[]{
                        task.getName(),
                        task.getDescription(),
                        task.getReward(),
                        formattedDate, // Use the formatted date here
                        "Edit/Delete"
                });
            }
        }
    }


    private void openSetTaskFrame() {
        AssignmentFrame taskFrame = new AssignmentFrame(accountManager, parentAccount, childAccount);
        taskFrame.setVisible(true);
        this.dispose(); // 关闭当前窗口
    }

    private void editTask(int row) {
        Task task = childAccount.getTasks().get(row);
        AssignmentFrame assignmentFrame = new AssignmentFrame(accountManager, parentAccount, childAccount, task);
        assignmentFrame.setVisible(true);
        this.dispose(); // 关闭当前窗口
    }

    private void deleteTask(int row) {
        if (row >= 0 && row < tasksModel.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Task task = childAccount.getTasks().get(row);
                childAccount.removeTask(task);
                accountManager.saveAccount(childAccount);
                updateTasksTable();
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

            editButton.addActionListener(e -> editTask(row));
            deleteButton.addActionListener(e -> deleteTask(row));

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

            editButton.addActionListener(e -> editTask(currentRow));
            deleteButton.addActionListener(e -> deleteTask(currentRow));

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
