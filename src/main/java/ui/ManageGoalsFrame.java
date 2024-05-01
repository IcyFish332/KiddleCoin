package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ManageGoalsFrame extends JFrame {
    private DefaultTableModel goalsModel;
    private JTable goalsTable;

    public ManageGoalsFrame(String name, String totalSavings, DefaultTableModel receivedGoalsModel) {
        super("Manage Kid's Goals");
        this.goalsModel = receivedGoalsModel;  // 使用从 KidDetailsFrame 传递过来的模型
        initComponents(name, totalSavings);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(String name, String totalSavings) {
        setTitle("Manage Kid's Goals");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        addTitleAndInfoPanel(mainPanel, name, totalSavings);
        addGoalsListPanel(mainPanel);
        addGoalsTable(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTitleAndInfoPanel(JPanel panel, String name, String totalSavings) {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Manage Kid's Goals");
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

    private void addGoalsListPanel(JPanel panel) {
        JPanel goalsListPanel = new JPanel();
        goalsListPanel.setLayout(new BoxLayout(goalsListPanel, BoxLayout.LINE_AXIS));
        goalsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel goalsListLabel = new JLabel("Goals List");
        goalsListLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        goalsListLabel.setForeground(new Color(255, 105, 180));

        JButton setGoalButton = new JButton("+ Set a goal");
        setGoalButton.setFont(new Font("Arial", Font.PLAIN, 12));
        setGoalButton.setForeground(Color.WHITE);
        setGoalButton.setBackground(new Color(255, 105, 180));
        setGoalButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Set a Goal - Not yet implemented"));

        goalsListPanel.add(goalsListLabel);
        goalsListPanel.add(Box.createHorizontalGlue());
        goalsListPanel.add(setGoalButton);

        panel.add(goalsListPanel);
    }

    private void addGoalsTable(JPanel panel) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] goalColumns = {"Goal's Name", "Description", "Money Amount", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        goalsTable = new JTable(goalsModel);
        goalsTable.setRowHeight(30);
        goalsTable.getColumnModel().getColumn(5).setCellRenderer(new KidDetailsFrame.ButtonRenderer());
        goalsTable.getColumnModel().getColumn(5).setCellEditor(new KidDetailsFrame.ButtonEditor(new JCheckBox(), goalsTable, "goal"));

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        tablePanel.add(goalsScrollPane, BorderLayout.CENTER);

        panel.add(tablePanel);
    }
}
