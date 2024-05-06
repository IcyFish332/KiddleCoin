package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyAccountFrame extends JFrame {
    private DefaultTableModel goalsModel;
    private DefaultTableModel tasksModel;

    public MyAccountFrame(String username, double totalSavings, double currentBalance) {
        initComponents(username, totalSavings, currentBalance);
    }

    private void initComponents(String username, double totalSavings, double currentBalance) {
        setTitle("KiddleCoin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel(); // 创建面板用于容纳标题
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("My Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // 设置字体为粗体、字号为24
        titleLabel.setForeground(new Color(255, 105, 180));
        titlePanel.add(titleLabel); // 将标题标签添加到面板
        add(titlePanel, BorderLayout.NORTH);

        addUserInfoPanel(username, totalSavings, currentBalance);
        addGoalsSection();
        addTasksSection();
    }

    private void addUserInfoPanel(String username, double totalSavings, double currentBalance) {
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(3, 2));
        userInfoPanel.setBackground(new Color(255, 192, 203));
        userInfoPanel.add(new JLabel(username));
        userInfoPanel.add(new JLabel("\n"));
        userInfoPanel.add(new JLabel("\n"));
        userInfoPanel.add(new JLabel("\n"));

        userInfoPanel.add(new JLabel("My Total Savings:"));
        userInfoPanel.add(new JLabel(String.valueOf(totalSavings)));

        userInfoPanel.add(new JLabel("My Current Balance:"));
        userInfoPanel.add(new JLabel(String.valueOf(currentBalance)));

        JButton depositButton = new JButton("I want to deposit money");
        depositButton.setFont(new Font("Arial", Font.PLAIN, 16));
        depositButton.setForeground(new Color(255, 105, 180));
        depositButton.setBackground(Color.WHITE);
        JButton withdrawButton = new JButton("I want to withdraw money");
        withdrawButton.setFont(new Font("Arial", Font.PLAIN, 16));
        withdrawButton.setForeground(new Color(255, 105, 180));
        withdrawButton.setBackground(Color.WHITE);

        userInfoPanel.add(depositButton);
        userInfoPanel.add(withdrawButton);

        add(userInfoPanel);
    }

    private void addGoalsSection() {
        JPanel goalsPanel = new JPanel();
        goalsPanel.setLayout(new BorderLayout());
        goalsPanel.setBackground(Color.WHITE);

        JLabel goalsLabel = new JLabel("Go to see my goals");
        goalsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        goalsLabel.setForeground(new Color(255, 105, 180));
        goalsPanel.add(goalsLabel, BorderLayout.NORTH);

        String[] goalColumns = {"Goal's Name", "Description", "Money Amount", "Award"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        JTable goalsTable = new JTable(goalsModel);
        goalsTable.setBackground(Color.WHITE);
        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        goalsPanel.add(goalsScrollPane, BorderLayout.CENTER);

        // Add See More link
        JLabel seeMoreGoalsLabel = new JLabel("See More...");
        seeMoreGoalsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        seeMoreGoalsLabel.setForeground(new Color(255, 105, 180));
        seeMoreGoalsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreGoalsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open MyGoalsFrame
                MyGoalsFrame myGoalsFrame = new MyGoalsFrame();
                myGoalsFrame.setVisible(true);
            }
        });
        goalsPanel.add(seeMoreGoalsLabel, BorderLayout.SOUTH);

        add(goalsPanel);
    }

    private void addTasksSection() {
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BorderLayout());
        tasksPanel.setBackground(Color.WHITE);

        JLabel tasksLabel = new JLabel("Go to see my tasks");
        tasksLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tasksLabel.setForeground(new Color(255, 105, 180));
        tasksPanel.add(tasksLabel, BorderLayout.NORTH);

        String[] taskColumns = {"Task's Name", "Description", "Award"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        JTable tasksTable = new JTable(tasksModel);
        tasksTable.setBackground(Color.WHITE);
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        // Add See More link
        JLabel seeMoreTasksLabel = new JLabel("See More...");
        seeMoreTasksLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        seeMoreTasksLabel.setForeground(new Color(255, 105, 180));
        seeMoreTasksLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreTasksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open MyTasksFrame
                MyTasksFrame myTasksFrame = new MyTasksFrame();
                myTasksFrame.setVisible(true);
            }
        });
        tasksPanel.add(seeMoreTasksLabel, BorderLayout.SOUTH);

        add(tasksPanel);
    }


    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> new MyAccountFrame("Anna", 1000.0, 500.0).setVisible(true));
    //}
}
