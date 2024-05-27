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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MyTasksFrame is a JFrame that displays and manages tasks for a child account.
 * It includes features for viewing and paginating through tasks.
 * @author Longyue Liu
 */

public class MyTasksFrame extends KidPageFrame {

    private DefaultTableModel tableModel;
    private JTable TasksTable;
    private JPanel balancePanel;
    private JLabel balanceLabel;
    private AccountManager accountManager;
    private ChildAccount childAccount;
    private int currentPageIndex = 0;
    private int pageSize = 10;
    /**
     * Constructs a MyTasksFrame with the specified AccountManager and ChildAccount.
     *
     * @param accountManager the account manager managing the account
     * @param childAccount   the child account to display tasks for
     */

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
                if (currentPageIndex > 0) {
                    currentPageIndex--;
                    loadDataIntoTable();
                }
            }
        });
        BigButton nextButton = new BigButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic for going to the next page
                int maxPageIndex = (int) Math.ceil((double) childAccount.getTasks().size() / pageSize) - 1;
                if (currentPageIndex < maxPageIndex) {
                    currentPageIndex++;
                    loadDataIntoTable();
                }
            }
        });
        paginationPanel.add(previousButton);
        paginationPanel.add(nextButton);
        lowerPanel.add(paginationPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadDataIntoTable();



        setVisible(true);
    }

    /**
     * Loads data from the backend into the table.
     */
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
