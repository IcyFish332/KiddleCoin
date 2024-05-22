package ui;

import core.AccountManager;
import core.ParentAccount;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageGoalsFrame extends ParentPageFrame {
    private  AccountManager accountManager;
    private  ParentAccount parentAccount;
    private  DefaultTableModel goalsModel;
    private JTable goalsTable;
    private int currentRowIndex = 0; // 初始化为0，以从第一行开始

    public ManageGoalsFrame(AccountManager accountManager, ParentAccount parentAccount, String name, String totalSavings, DefaultTableModel receivedGoalsModel) {
        super("Manage Kid's Goals", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        this.goalsModel = receivedGoalsModel;
        initComponents(name, totalSavings);
    }

    private void initComponents(String name, String totalSavings) {
        setTitle("Manage Kid's Goals");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));

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
        infoPanel.add(Box.createHorizontalStrut(20));
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
        setGoalButton.addActionListener(e -> openSetGoalFrame());
        goalsListPanel.add(goalsListLabel, BorderLayout.WEST);
        goalsListPanel.add(setGoalButton, BorderLayout.EAST);

        lowerPanel.add(goalsListPanel);
    }

    private void addGoalsTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] goalColumns = {"Goal's Name", "Description", "Target", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        goalsTable = new JTable(goalsModel);
        goalsTable.setRowHeight(30);

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        tablePanel.add(goalsScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);
    }

    private void openSetGoalFrame() {
        GoalFrame goalFrame = new GoalFrame(accountManager, parentAccount,this);
        goalFrame.setVisible(true);
    }

    public void updateRow(String goalsName, String target, String award, String description) {
        if (currentRowIndex >= goalsModel.getRowCount()) {
            goalsModel.addRow(new Object[]{"", "", "", "", "", ""});
        }
        goalsModel.setValueAt(goalsName, currentRowIndex, 0);
        goalsModel.setValueAt(description, currentRowIndex, 1);
        goalsModel.setValueAt(target, currentRowIndex, 2);
        goalsModel.setValueAt(award, currentRowIndex, 3);
        currentRowIndex++; // 更新完整行后递增行索引
    }


    //public static void main(String[] args) {
    //   SwingUtilities.invokeLater(() -> new ManageGoalsFrame(accountManager, parentAccount, "Name", "1000", goalsModel).setVisible(true));
    //}示例主函数

}