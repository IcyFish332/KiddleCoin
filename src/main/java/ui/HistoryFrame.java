package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistoryFrame extends JFrame {
    private DefaultTableModel historyModel;
    private JTable historyTable;
    private JScrollPane historyScrollPane;

    public HistoryFrame(String balance) {
        initComponents(balance);
    }

    private void initComponents(String balance) {
        setTitle("KiddleCoin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(Color.WHITE);
        JLabel balanceLabel = new JLabel("Balance: ");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balancePanel.add(balanceLabel);
        JLabel balanceValueLabel = new JLabel(balance);
        balanceValueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        balancePanel.add(balanceValueLabel);
        add(balancePanel, BorderLayout.CENTER);

        JPanel recordsPanel = new JPanel(new BorderLayout());
        recordsPanel.setBackground(Color.WHITE);
        JLabel recordsLabel = new JLabel("Income and expenditure records");
        recordsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        recordsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        recordsPanel.add(recordsLabel, BorderLayout.NORTH);

        String[] columnNames = {"Time", "Type", "Amount", "Balance"};
        historyModel = new DefaultTableModel(null, columnNames);
        historyTable = new JTable(historyModel);
        historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 添加垂直滚动条
        recordsPanel.add(historyScrollPane, BorderLayout.CENTER);

        add(recordsPanel, BorderLayout.CENTER);

        setColumnWidths();
    }

    private void setColumnWidths() {
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        //historyTable.getColumnModel().getColumn(4).setPreferredWidth(250);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistoryFrame frame = new HistoryFrame("$500");
            frame.setVisible(true);
        });
    }
}
