package ui.kid;

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
import java.text.SimpleDateFormat;
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
        String[] columnNames = {"Task's Name", "Description", "Award ($)", "Due date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        TasksTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                return c;
            }
        };
        TasksTable.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(TasksTable);
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

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

            });
        }
    }
}
