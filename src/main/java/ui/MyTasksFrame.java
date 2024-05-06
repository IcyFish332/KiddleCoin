package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTasksFrame extends JFrame {
    private DefaultTableModel tasksModel;
    private JTable tasksTable;
    private JScrollPane tasksScrollPane;

    public MyTasksFrame() {
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
        JLabel titleLabel = new JLabel("My Tasks");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel tasksListPanel = new JPanel(new BorderLayout());
        tasksListPanel.setBackground(Color.WHITE);
        JLabel tasksListLabel = new JLabel("My Tasks List");
        tasksListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksListLabel.setForeground(new Color(255, 105, 180));
        tasksListLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        tasksListPanel.add(tasksListLabel, BorderLayout.NORTH);

        String[] columnNames = {"Select", "Task's Name", "Description", "Award", "Operation"};
        tasksModel = new DefaultTableModel(null, columnNames);
        tasksTable = new JTable(tasksModel) {
            @Override
            public Class getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class;
                } else {
                    return Object.class;
                }
            }
        };
        tasksTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        tasksTable.getColumnModel().getColumn(0).setMaxWidth(50);
        tasksScrollPane = new JScrollPane(tasksTable);
        tasksListPanel.add(tasksScrollPane, BorderLayout.CENTER);

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

        tasksListPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("+ADD");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setForeground(new Color(255, 105, 180));
        addButton.setBackground(Color.WHITE);
        addButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180)));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasksModel.addRow(new Object[]{false, "", "", "$", ""});
            }
        });
        tasksListPanel.add(addButton, BorderLayout.WEST);

        add(tasksListPanel, BorderLayout.CENTER);

        setColumnWidths();
    }

    private void setColumnWidths() {
        tasksTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        tasksTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        tasksTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        tasksTable.getColumnModel().getColumn(4).setPreferredWidth(150);
    }


    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> {
    //        MyTasksFrame frame = new MyTasksFrame();
    //        frame.setVisible(true);
    //    });
    //}
}

