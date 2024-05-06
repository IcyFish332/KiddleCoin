package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGoalsFrame extends JFrame {
    private DefaultTableModel goalsModel;
    private JTable goalsTable;
    private JScrollPane goalsScrollPane;

    public MyGoalsFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("KiddleCoin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("My Goals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel goalsListPanel = new JPanel(new BorderLayout());
        goalsListPanel.setBackground(Color.WHITE);
        JLabel goalsListLabel = new JLabel("My Goals List");
        goalsListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalsListLabel.setForeground(new Color(255, 105, 180));
        goalsListLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        goalsListPanel.add(goalsListLabel, BorderLayout.NORTH);

        String[] columnNames = {"Select", "Goal's Name", "Description", "Money Amount", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, columnNames);
        goalsTable = new JTable(goalsModel) {
            @Override
            public Class getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class;
                } else {
                    return Object.class;
                }
            }
        };
        goalsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        goalsTable.getColumnModel().getColumn(0).setMaxWidth(50);
        goalsScrollPane = new JScrollPane(goalsTable);
        goalsListPanel.add(goalsScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle edit action
            }
        });
        buttonsPanel.add(editButton);

        JButton moveButton = new JButton("Move");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle move action
            }
        });
        buttonsPanel.add(moveButton);

        goalsListPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("+ADD");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setForeground(new Color(255, 105, 180));
        addButton.setBackground(Color.WHITE);
        addButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180)));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goalsModel.addRow(new Object[]{false, "", "", "$", "$", "$", ""});
            }
        });
        goalsListPanel.add(addButton, BorderLayout.WEST);

        add(goalsListPanel, BorderLayout.CENTER);

        setColumnWidths();
    }

    private void setColumnWidths() {
        goalsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        goalsTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        goalsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        goalsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        goalsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        goalsTable.getColumnModel().getColumn(6).setPreferredWidth(150);
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> {
    //        MyGoalsFrame frame = new MyGoalsFrame();
    //        frame.setVisible(true);
    //    });
    //}
}
