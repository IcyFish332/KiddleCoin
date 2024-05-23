package ui;

import core.AccountManager;
import core.ChildAccount;
import core.Task;
import ui.template.BigButton;
import ui.template.KidPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyTasksFrame extends KidPageFrame {

    private DefaultTableModel tableModel;
    private JTable TasksTable;
    private JPanel balancePanel;
    private JLabel balanceLabel;
    private AccountManager accountManager;
    private ChildAccount childAccount;

    public MyTasksFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("My Tasks", accountManager, childAccount);
        this.childAccount = childAccount;
        this.accountManager = accountManager;

        // Set up the upper panel with title
        upperPanel.setLayout(new BorderLayout());

        // Set up the lower panel with table, balance, and pagination
        lowerPanel.setLayout(new BorderLayout());

        // Table
        String[] columnNames = {"Task's Name", "Description", "Award ($)", "Due date", "Save", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0);
        TasksTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Customize cell appearance based on column
                if (column == 4) { // "Save" column
                    c.setForeground(Color.BLUE); // Make text blue
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
                } else if (column == 5) { // "Delete" column
                    c.setForeground(Color.RED); // Make text red
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                return c;
            }
        };
        TasksTable.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(TasksTable);
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add Button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align to the right
        addButtonPanel.setBackground(Color.WHITE);
        BigButton addButton = new BigButton("+ADD");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a new row to the table model
                tableModel.addRow(new Object[]{"", "", "", "", "Save", "Delete"}); // Add "Save" and "Delete" as text
            }
        });
        addButtonPanel.add(addButton);
        lowerPanel.add(addButtonPanel, BorderLayout.NORTH);

        // Pagination
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBackground(Color.WHITE);
        BigButton previousButton = new BigButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic for going to the previous page
                // You'll likely need to manage page numbers or data fetching
            }
        });
        BigButton nextButton = new BigButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic for going to the next page
                // You'll likely need to manage page numbers or data fetching
            }
        });
        paginationPanel.add(previousButton);
        paginationPanel.add(nextButton);
        lowerPanel.add(paginationPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadDataIntoTable();

        // Add Mouse Listener to table
        TasksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = TasksTable.rowAtPoint(e.getPoint());
                    int col = TasksTable.columnAtPoint(e.getPoint());

                    if (col == 4) { // "Save" column
                        // Get data from the row
                        String taskName = (String) tableModel.getValueAt(row, 0);
                        String description = (String) tableModel.getValueAt(row, 1);
                        String rewardStr = (String) tableModel.getValueAt(row, 2);
                        String dueDateString = (String) tableModel.getValueAt(row, 3);

                        // Validate input
                        if (taskName.isEmpty() || description.isEmpty() || rewardStr.isEmpty() || dueDateString.isEmpty()) {
                            JOptionPane.showMessageDialog(MyTasksFrame.this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Don't save if data is incomplete
                        }

                        double reward;
                        Date dueDate;
                        try {
                            reward = Double.parseDouble(rewardStr);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Assuming due date format is YYYY-MM-DD
                            dueDate = formatter.parse(dueDateString);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(MyTasksFrame.this, "Invalid reward amount!", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Don't save if reward amount is invalid
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(MyTasksFrame.this, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Create and save the Task object
                        Task newTask = new Task(taskName, description, reward, dueDate);
                        childAccount.addTask(newTask);
                        accountManager.saveAccount(childAccount);

                        // Update the table row
                        tableModel.setValueAt(taskName, row, 0);
                        tableModel.setValueAt(description, row, 1);
                        tableModel.setValueAt(reward, row, 2);
                        tableModel.setValueAt(dueDateString, row, 3);
                    } else if (col == 5) { // "Delete" column
                        // Delete the row from the table model
                        tableModel.removeRow(row);

                        // Delete the corresponding Task from the backend
                        Task taskToDelete = childAccount.getTasks().get(row);
                        childAccount.removeTask(taskToDelete);
                        accountManager.saveAccount(childAccount);
                    }
                }
            }
        });

        setVisible(true);
    }

    // Load data from the backend into the table
    private void loadDataIntoTable() {
        List<Task> tasks = childAccount.getTasks();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getName(),
                    task.getDescription(),
                    task.getReward(),
                    formatter.format(task.getDueDate()),
                    "Save",
                    "Delete"
            });
        }
    }
}