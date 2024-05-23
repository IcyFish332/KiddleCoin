package ui.kidCenter;

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

public class HistoryFrame extends KidPageFrame {
    private JLabel balanceLabel;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JPanel balancePanel;
    private ChildAccount childAccount;

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

        // Load initial data
        loadHistoryData();

        setVisible(true);
    }

    private void loadHistoryData() {
        // 从后端获取交易历史记录
        List<TransactionHistory> history = childAccount.getTransactionHistory();

        // 将交易历史记录转换为表格数据
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

        // 更新表格模型
        tableModel.setRowCount(0); // 清空之前的表格数据
        for (String[] rowData : historyData) {
            tableModel.addRow(rowData);
        }

        // 更新余额
        balanceLabel.setText("$" + String.format("%.2f", childAccount.getBalance()));
    }
}