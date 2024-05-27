package ui.kid;

import core.AccountManager;
import core.ChildAccount;
import core.SavingGoal;
import ui.template.BigButton;
import ui.template.KidPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.List;

public class MyGoalsFrame extends KidPageFrame {
    public DefaultTableModel tableModel;
    private JTable goalsTable;
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
        String[] columnNames = {"Goal's Name", "Description", "Money Amount ($)",
                "Award ($)", "Progress (%)", "Operations", ""}; // Operations column added
        tableModel = new DefaultTableModel(columnNames, 0);
        goalsTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (column == 5 || column == 6) { // "Save" and "Delete" columns
                    if (c instanceof JLabel) {
                        ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                    }
                }

                if (column == 5) {
                    c = new ButtonRenderer("Save");
                } else if (column == 6) {
                    c = new ButtonRenderer("Delete");
                }

                return c;
            }
        };
        goalsTable.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(goalsTable);
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add Button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.setBackground(Color.WHITE);
        BigButton addButton = new BigButton("+ADD");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{"", "", "", "", "", "", ""});
            }
        });
        addButtonPanel.add(addButton);
        lowerPanel.add(addButtonPanel, BorderLayout.NORTH);

        // Pagination (You'll need to implement the logic)
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBackground(Color.WHITE);
        BigButton previousButton = new BigButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic for going to the previous page
            }
        });
        BigButton nextButton = new BigButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic for going to the next page
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

                    if (col == 5) {
                        saveGoalFromTable(row);
                    } else if (col == 6) {
                        deleteGoalFromTable(row);
                    }
                }
            }
        });

        // Combine "Save" and "Delete" column headers
        TableColumnModel cm = goalsTable.getColumnModel();
        cm.getColumn(5).setHeaderValue("Operations");
        cm.getColumn(6).setHeaderValue("");
        cm.getColumn(6).setMaxWidth(70); // Adjust width as needed

        setVisible(true);
    }

    // Load data from the backend into the table
    public void loadDataIntoTable() {
        tableModel.setRowCount(0);
        List<SavingGoal> goals = childAccount.getSavingGoals();
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(1); // Display one decimal place for percentages

        for (SavingGoal goal : goals) {
            double progress = goal.getTargetAmount() > 0 ?
                    childAccount.getSavings() / goal.getTargetAmount() : 0;
            String progressStr = percentFormat.format(progress);

            tableModel.addRow(new Object[]{
                    goal.getName(),
                    goal.getDescription(),
                    goal.getTargetAmount(),
                    goal.getReward(),
                    progressStr,
                    "", // "Save" button will be rendered here
                    ""  // "Delete" button will be rendered here
            });
        }
    }

    // Method to save a goal from the table
    public void saveGoalFromTable(int row) {
        // Get data from the row
        String goalName = (String) tableModel.getValueAt(row, 0);
        String description = (String) tableModel.getValueAt(row, 1);
        String targetAmountStr = (String) tableModel.getValueAt(row, 2);
        String rewardStr = (String) tableModel.getValueAt(row, 3);

        // Validate input
        if (goalName.isEmpty() || description.isEmpty() || targetAmountStr.isEmpty() || rewardStr.isEmpty()) {
            JOptionPane.showMessageDialog(MyGoalsFrame.this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double targetAmount;
        double reward;
        try {
            targetAmount = Double.parseDouble(targetAmountStr);
            reward = Double.parseDouble(rewardStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(MyGoalsFrame.this, "Invalid money!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create and save the SavingGoal object
        SavingGoal newGoal = new SavingGoal(goalName, description, targetAmount, reward);
        childAccount.addSavingGoal(newGoal);
        accountManager.saveAccount(childAccount);

        // Update the table
        loadDataIntoTable();
    }


    // Method to delete a goal from the table
    public void deleteGoalFromTable(int row) {
        // Delete the corresponding goal from the backend
        SavingGoal goalToDelete = childAccount.getSavingGoals().get(row);
        childAccount.removeSavingGoal(goalToDelete);
        accountManager.saveAccount(childAccount);

        // Delete the row from the table model
        tableModel.removeRow(row);
    }

    // Inner class for rendering buttons
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        BigButton button;

        public ButtonRenderer(String buttonText) {
            setLayout(new BorderLayout());
            button = new BigButton(buttonText);
            add(button, BorderLayout.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
}