package ui;
import core.AccountManager;
import core.ParentAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

public class ManageGoalsFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private DefaultTableModel goalsModel;
    private JTable goalsTable;

    public ManageGoalsFrame(AccountManager accountManager, ParentAccount parentAccount, String name, String totalSavings, DefaultTableModel receivedGoalsModel) {
        super("Manage Kid's Goals", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        this.goalsModel = receivedGoalsModel; // 使用从 KidDetailsFrame 传递过来的模型
        initComponents(name, totalSavings);
    }

    private void initComponents(String name, String totalSavings) {
        setTitle("Manage Kid's Goals");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS)); // 使用继承的 lowerPanel 并设置垂直布局

        addInfoPanel(name, totalSavings);
        addGoalsListPanel();
        addGoalsTable();

        setVisible(true);
    }

    private void addInfoPanel(String name, String totalSavings) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = new JLabel("Name: " + name);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel savingsLabel = new JLabel("Total Savings: " + totalSavings);
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createHorizontalStrut(20)); // Space between name and savings
        infoPanel.add(savingsLabel);

        lowerPanel.add(infoPanel);
    }

    private void addGoalsListPanel() {
        JPanel goalsListPanel = new JPanel();
        goalsListPanel.setLayout(new BorderLayout());
        goalsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel goalsListLabel = new JLabel("Goals List");
        goalsListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalsListLabel.setForeground(new Color(255, 105, 180));

        BigButton setGoalButton = new BigButton("+ Set a Goal");
        setGoalButton.setFont(new Font("Arial", Font.PLAIN, 14));
        setGoalButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Set a Goal - Not yet implemented"));

        goalsListPanel.add(goalsListLabel, BorderLayout.WEST);
        goalsListPanel.add(setGoalButton, BorderLayout.EAST);

        lowerPanel.add(goalsListPanel);
    }

    private void addGoalsTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] goalColumns = {"Goal's Name", "Description", "Money Amount", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, goalColumns); // Assuming this is filled elsewhere or passed in
        goalsTable = new JTable(goalsModel);
        goalsTable.setRowHeight(30);

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        tablePanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);
    }
}
