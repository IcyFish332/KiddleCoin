package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ManageTasksFrame extends JFrame {
    private DefaultTableModel tasksModel;
    private JTable tasksTable;

    public ManageTasksFrame(String name, String totalSavings, DefaultTableModel receivedTasksModel) {
        super("Manage Kid's Tasks");
        this.tasksModel = receivedTasksModel;  // 使用从 KidDetailsFrame 传递过来的模型
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
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        addTitleAndInfoPanel(mainPanel, name, totalSavings);
        addTasksListPanel(mainPanel);
        addTasksTable(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTitleAndInfoPanel(JPanel panel, String name, String totalSavings) {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Manage Kid's Tasks");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        titlePanel.add(titleLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel nameValueLabel = new JLabel(name);
        nameValueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel savingsLabel = new JLabel("Total Savings: ");
        savingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel savingsValueLabel = new JLabel(totalSavings);
        savingsValueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(nameLabel);
        infoPanel.add(nameValueLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(savingsLabel);
        infoPanel.add(savingsValueLabel);
        infoPanel.add(Box.createHorizontalGlue());

        panel.add(titlePanel);
        panel.add(infoPanel);
    }

    private void addTasksListPanel(JPanel panel) {
        JPanel tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BoxLayout(tasksListPanel, BoxLayout.LINE_AXIS));
        tasksListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel tasksListLabel = new JLabel("Tasks List");
        tasksListLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tasksListLabel.setForeground(new Color(255, 105, 180));

        JButton setTaskButton = new JButton("+ Assign a task");
        setTaskButton.setFont(new Font("Arial", Font.PLAIN, 12));
        setTaskButton.setForeground(Color.WHITE);
        setTaskButton.setBackground(new Color(255, 105, 180));
        setTaskButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Assign a task - Not yet implemented"));

        tasksListPanel.add(tasksListLabel);
        tasksListPanel.add(Box.createHorizontalGlue());
        tasksListPanel.add(setTaskButton);

        panel.add(tasksListPanel);
    }

    private void addTasksTable(JPanel panel) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] taskColumns = {"Task's Name", "Description", "Award", "Operation"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        tasksTable = new JTable(tasksModel);
        tasksTable.setRowHeight(30);
        tasksTable.getColumnModel().getColumn(3).setCellRenderer(new KidDetailsFrame.ButtonRenderer());
        tasksTable.getColumnModel().getColumn(3).setCellEditor(new KidDetailsFrame.ButtonEditor(new JCheckBox(), tasksTable, "goal"));

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tablePanel.add(tasksScrollPane, BorderLayout.CENTER);

        panel.add(tablePanel);
    }
}
