package ui.kid;

import core.AccountManager;
import core.ChildAccount;
import ui.template.BigButton;
import ui.template.KidPageFrame;
import utils.TransactionHistory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * HistoryFrame is a JFrame that displays and manages the transaction history for a child account.
 * It includes features for viewing the transaction history, paginating through records, and displaying the current balance.
 * @author Longyue Liu
 */

public class HistoryFrame extends KidPageFrame {
    private JLabel balanceLabel;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JPanel balancePanel;
    private ChildAccount childAccount;
    private int currentPageIndex = 0;
    private int pageSize = 10;
    /**
     * Constructs a HistoryFrame with the specified AccountManager and ChildAccount.
     *
     * @param accountManager the account manager managing the account
     * @param childAccount   the child account to display the transaction history for
     */


    public HistoryFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("History", accountManager, childAccount);
        this.childAccount = childAccount;
        // Set up the upper panel with title
        upperPanel.setLayout(new BorderLayout());

        // Set up the lower panel with table, balance, and pagination
        lowerPanel.setLayout(new BorderLayout());

        // Balance
        balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        balancePanel.setBackground(Color.WHITE);
        JLabel balanceText = new JLabel("Balance:");
        balanceText.setFont(new Font("Calibri", Font.PLAIN, 18));
        balanceLabel = new JLabel("$" + "0"); // Initially 0
        balanceLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        balancePanel.add(balanceText);
        balancePanel.add(balanceLabel);
        lowerPanel.add(balancePanel, BorderLayout.NORTH); // Added to the NORTH

        // Table
        String[] columnNames = {"Time", "Type", "Amount", "Balance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        historyTable.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane tableScrollPane = new JScrollPane(historyTable);
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
                    loadHistoryData(); // 加载前一页的数据
                }
            }
        });
        BigButton nextButton = new BigButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic for going to the next page
                int maxPageIndex = (int) Math.ceil((double) childAccount.getTransactionHistory().size() / pageSize) - 1;
                if (currentPageIndex < maxPageIndex) {
                    currentPageIndex++;
                    loadHistoryData(); // 加载下一页的数据
                }
            }
        });
        paginationPanel.add(previousButton);
        paginationPanel.add(nextButton);
        lowerPanel.add(paginationPanel, BorderLayout.SOUTH);

        // Load initial data
        loadHistoryData();

        setVisible(true);
    }
    /**
     * Loads the transaction history data from the backend into the table.
     */

    private void loadHistoryData() {
        // Get transaction history
        List<TransactionHistory> history = childAccount.getTransactionHistory();

        // Convert transaction history to table data
        List<String[]> historyData = new ArrayList<>();
        for (TransactionHistory transaction : history) {
            String[] rowData = {
                    transaction.getTimestamp(),
                    transaction.getType(),
                    String.valueOf(transaction.getAmount()),
                    String.valueOf(childAccount.getBalance())
            };
            historyData.add(rowData);
        }

        // Update the table model
        tableModel.setRowCount(0); // clear
        for (String[] rowData : historyData) {
            tableModel.addRow(rowData);
        }

        // Update balance
        balanceLabel.setText("$" + String.format("%.2f", childAccount.getBalance()));
    }
}