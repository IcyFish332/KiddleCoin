package ui;
import core.AccountManager;
import core.ParentAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

public class ManageTasksFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private DefaultTableModel tasksModel;
    private JTable tasksTable;

    public ManageTasksFrame(AccountManager accountManager, ParentAccount parentAccount, String name, String totalSavings, DefaultTableModel receivedTasksModel) {
        super("Manage Kid's Tasks", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        this.tasksModel = receivedTasksModel;  // Using the model passed from KidDetailsFrame
        initComponents(name, totalSavings);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(String name, String totalSavings) {
        setTitle("Manage Kid's Tasks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));  // Set vertical box layout for lowerPanel

        addTitleAndInfoPanel(name, totalSavings);
        addTasksListPanel();
        addTasksTable();
    }

    private void addTitleAndInfoPanel(String name, String totalSavings) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = new JLabel("Name: " + name);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel savingsLabel = new JLabel("Total Savings: " + totalSavings);
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createHorizontalStrut(20));  // Space between name and savings
        infoPanel.add(savingsLabel);

        lowerPanel.add(infoPanel);  // Add infoPanel to the lowerPanel
    }

    private void addTasksListPanel() {
        JPanel tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BoxLayout(tasksListPanel, BoxLayout.X_AXIS));
        tasksListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel tasksListLabel = new JLabel("Tasks List");
        tasksListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksListLabel.setForeground(new Color(255, 105, 180));

        BigButton setTaskButton = new BigButton("+ Assign a Task");
        setTaskButton.setFont(new Font("Arial", Font.PLAIN, 14));
        setTaskButton.addActionListener(e -> openAssignTaskFrame());

        tasksListPanel.add(tasksListLabel);
        tasksListPanel.add(Box.createHorizontalGlue());
        tasksListPanel.add(setTaskButton);

        lowerPanel.add(tasksListPanel);  // Add tasksListPanel to the lowerPanel
    }
    private void openAssignTaskFrame() {
        openAssignTaskFrame taskFrame = new openAssignTaskFrame(); // 创建 GoalFrame 实例
        taskFrame.setVisible(true); // 显示 GoalFrame
    }

    private void addTasksTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] taskColumns = {"Task's Name", "Description", "Award", "Operation"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        tasksTable = new JTable(tasksModel);
        tasksTable.setRowHeight(30);

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tablePanel.add(tasksScrollPane, BorderLayout.CENTER);

        lowerPanel.add(tablePanel);  // Add tablePanel to the lowerPanel
    }
}
