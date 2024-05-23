package ui;

import core.AccountManager;
import core.ChildAccount;
import core.SavingGoal;
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
import java.util.List;

public class MyGoalsFrame extends KidPageFrame {
    private DefaultTableModel tableModel;
    private JTable goalsTable;
    private JPanel balancePanel;
    private JLabel balanceLabel;
    private AccountManager accountManager;
    private ChildAccount childAccount;

    public MyGoalsFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("My Goals", accountManager, childAccount);
        this.childAccount = childAccount;
        this.accountManager = accountManager;

        // Set up the upper panel with title
        upperPanel.setLayout(new BorderLayout());

        // Set up the lower panel with table, balance, and pagination
        lowerPanel.setLayout(new BorderLayout());

        // Table
        String[] columnNames = {"Goal's Name", "Description", "Money Amount ($)", "Award ($)", "Progress ($)", "Save", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0);
        goalsTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Customize cell appearance based on column
                if (column == 5) { // "Save" column
                    c.setForeground(Color.BLUE); // Make text blue
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
                } else if (column == 6) { // "Delete" column
                    c.setForeground(Color.RED); // Make text red
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                return c;
            }
        };
        goalsTable.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(goalsTable);
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add Button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align to the right
        addButtonPanel.setBackground(Color.WHITE);
        BigButton addButton = new BigButton("+ADD");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a new row to the table model
                tableModel.addRow(new Object[]{"", "", "", "", "", "Save", "Delete"}); // Add "Save" and "Delete" as text
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
        goalsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = goalsTable.rowAtPoint(e.getPoint());
                    int col = goalsTable.columnAtPoint(e.getPoint());

                    if (col == 5) { // "Save" column
                        // Get data from the row
                        String goalName = (String) tableModel.getValueAt(row, 0);
                        String description = (String) tableModel.getValueAt(row, 1);
                        String targetAmountStr = (String) tableModel.getValueAt(row, 2);
                        String rewardStr = (String) tableModel.getValueAt(row, 3);

                        // Validate input
                        if (goalName.isEmpty() || description.isEmpty() || targetAmountStr.isEmpty() || rewardStr.isEmpty()) {
                            JOptionPane.showMessageDialog(MyGoalsFrame.this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Don't save if data is incomplete
                        }

                        double targetAmount;
                        double reward;
                        try {
                            targetAmount = Double.parseDouble(targetAmountStr);
                            reward = Double.parseDouble(rewardStr);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(MyGoalsFrame.this, "Invalid money amount!", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Don't save if money amount is invalid
                        }

                        // Create and save the SavingGoal object
                        SavingGoal newGoal = new SavingGoal(goalName, description, targetAmount, reward);
                        childAccount.addSavingGoal(newGoal);
                        accountManager.saveAccount(childAccount);

                        // Update the table row
                        tableModel.setValueAt(goalName, row, 0);
                        tableModel.setValueAt(description, row, 1);
                        tableModel.setValueAt(targetAmount, row, 2);
                        tableModel.setValueAt(reward, row, 3);
                    } else if (col == 6) { // "Delete" column
                        // Delete the row from the table model
                        tableModel.removeRow(row);

                        // Delete the corresponding goal from the backend
                        SavingGoal goalToDelete = childAccount.getSavingGoals().get(row);
                        childAccount.removeSavingGoal(goalToDelete);
                        accountManager.saveAccount(childAccount);
                    }
                }
            }
        });

        setVisible(true);
    }

    // Load data from the backend into the table
    private void loadDataIntoTable() {
        List<SavingGoal> goals = childAccount.getSavingGoals();
        for (SavingGoal goal : goals) {
            tableModel.addRow(new Object[]{
                    goal.getName(),
                    goal.getDescription(),
                    goal.getTargetAmount(),
                    goal.getReward(),
                    childAccount.getSavings(), // Assuming "Progress" is current savings
                    "Save",
                    "Delete"
            });
        }
    }
}