package ui;
import core.AccountManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;



public class KidDetailsFrame extends JFrame {
    private AccountManager accountManager;
    private DefaultTableModel goalsModel;
    private DefaultTableModel tasksModel;
    private JTable goalsTable;
    private JTable tasksTable;

    public KidDetailsFrame(AccountManager accountManager,String name, String totalSavings) {
        this.accountManager = accountManager;
        initComponents(name, totalSavings);
    }

    private void initComponents(String name, String totalSavings) {
        setTitle("Details of Kid's Account");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addTitleAndInfoPanel(name, totalSavings);
        setupGoalsSection(name, totalSavings);
        setupTasksSection(name, totalSavings);
        addBottomButtons();
    }

    private void addTitleAndInfoPanel(String name, String totalSavings) {
        JLabel titleLabel = new JLabel("Manage kid's Goals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        namePanel.add(new JLabel(name));

        JPanel savingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        savingsPanel.add(new JLabel("Total Savings:"));
        savingsPanel.add(new JLabel(totalSavings));

        infoPanel.add(namePanel);
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(savingsPanel);

        add(infoPanel);
    }

    private void setupGoalsSection(String name, String totalSavings) {
        JPanel goalsPanel = new JPanel();
        goalsPanel.setLayout(new BorderLayout());
        JLabel goalsLabel = new JLabel("Goals");
        goalsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalsLabel.setForeground(new Color(255, 105, 180));
        goalsPanel.add(goalsLabel, BorderLayout.NORTH);

        String[] goalColumns = {"Goal's Name", "Description", "Money Amount", "Award", "Progress", "Operation"};
        goalsModel = new DefaultTableModel(null, goalColumns);
        goalsTable = new JTable(goalsModel);
        //goalsModel.addRow(new Object[]{"My Game", "For a computer game", "$30", "$10", "$17", ""});

        goalsTable.setRowHeight(30);
        goalsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        goalsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), goalsTable, "goal"));

        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);
        goalsPanel.add(goalsScrollPane, BorderLayout.CENTER);

        JButton manageGoalsButton = new JButton("See details and manage goals...");
        manageGoalsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        manageGoalsButton.setForeground(new Color(255, 105, 180));
        // 确保在用户点击 'See details and manage goals...' 按钮时传递正确的模型
        manageGoalsButton.addActionListener(e -> {
            ManageGoalsFrame manageGoalsFrame = new ManageGoalsFrame(accountManager,name, totalSavings, goalsModel);
            manageGoalsFrame.setVisible(true);
        });


        goalsPanel.add(manageGoalsButton, BorderLayout.SOUTH);

        add(goalsPanel);
    }

    private void setupTasksSection(String name, String totalSavings) {
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BorderLayout());
        JLabel tasksLabel = new JLabel("Tasks");
        tasksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tasksLabel.setForeground(new Color(255, 105, 180));
        tasksPanel.add(tasksLabel, BorderLayout.NORTH);

        String[] taskColumns = {"Task's Name", "Description", "Award", "Operation"};
        tasksModel = new DefaultTableModel(null, taskColumns);
        tasksTable = new JTable(tasksModel);
        //tasksModel.addRow(new Object[]{"Plates", "Wash plates once a day", "$30", ""});

        tasksTable.setRowHeight(30);
        tasksTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tasksTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), tasksTable, "task"));

        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        JButton manageTasksButton = new JButton("See details and manage tasks...");
        manageTasksButton.setFont(new Font("Arial", Font.PLAIN, 12));
        manageTasksButton.setForeground(new Color(255, 105, 180));
        manageTasksButton.addActionListener(e -> {
            ManageTasksFrame manageTasksFrame = new ManageTasksFrame(accountManager,name, totalSavings, tasksModel);
            manageTasksFrame.setVisible(true);
        });
        tasksPanel.add(manageTasksButton, BorderLayout.SOUTH);

        add(tasksPanel);
    }

    private void addBottomButtons() {
        // 创建包含按钮的面板，使用 GridLayout 以确保按钮均匀分布
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 1行2列的网格，水平和垂直间隔为10
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 添加边距

        // 创建 "Set a Goal" 按钮，并为其添加事件监听器
        JButton setGoalButton = new JButton("Set a Goal");
        setGoalButton.setFont(new Font("Arial", Font.BOLD, 16));
        setGoalButton.setForeground(new Color(255, 105, 180));
        setGoalButton.addActionListener(e -> {
            // TODO: 实现跳转到设置目标的页面
            JOptionPane.showMessageDialog(this, "This will open the Set a Goal page.");
        });

        // 创建 "Assign a Task" 按钮，并为其添加事件监听器
        JButton assignTaskButton = new JButton("Assign a Task");
        assignTaskButton.setFont(new Font("Arial", Font.BOLD, 16));
        assignTaskButton.setForeground(new Color(255, 105, 180));
        assignTaskButton.addActionListener(e -> {
            // TODO: 实现跳转到分配任务的页面
            JOptionPane.showMessageDialog(this, "This will open the Assign a Task page.");
        });

        // 向面板添加按钮，并为每个按钮添加边框以形成视觉上的分隔
        setGoalButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        assignTaskButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        buttonPanel.add(setGoalButton);
        buttonPanel.add(assignTaskButton);

        add(buttonPanel);
    }







    static class ButtonRenderer extends JPanel implements TableCellRenderer {
        JButton editButton, moveButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            editButton = new JButton("Edit");
            moveButton = new JButton("Move");
            add(editButton);
            add(moveButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected JButton editButton, moveButton;
        private JTable table;
        private String type;

        public ButtonEditor(JCheckBox checkBox, JTable table, String type) {
            super(checkBox);
            this.table = table;
            this.type = type;
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            editButton = new JButton("Edit");
            moveButton = new JButton("Move");

            panel.add(editButton);
            panel.add(moveButton);

            editButton.addActionListener(e -> editItem());
            moveButton.addActionListener(e -> moveItem());
        }

        private void editItem() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if ("goal".equals(type)) {
                    editGoal(row, model);
                } else {
                    editTask(row, model);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No item selected!");
            }
        }

        private void moveItem() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No item selected!");
            }
        }

        private void editGoal(int row, DefaultTableModel model) {
            String name = (String) model.getValueAt(row, 0);
            String description = (String) model.getValueAt(row, 1);
            String moneyAmount = (String) model.getValueAt(row, 2);
            String award = (String) model.getValueAt(row, 3);
            String progress = (String) model.getValueAt(row, 4);

            JTextField nameField = new JTextField(name);
            JTextField descriptionField = new JTextField(description);
            JTextField moneyAmountField = new JTextField(moneyAmount);
            JTextField awardField = new JTextField(award);
            JTextField progressField = new JTextField(progress);

            Object[] message = {
                    "Name:", nameField,
                    "Description:", descriptionField,
                    "Money Amount:", moneyAmountField,
                    "Award:", awardField,
                    "Progress:", progressField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit Goal", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                model.setValueAt(nameField.getText(), row, 0);
                model.setValueAt(descriptionField.getText(), row, 1);
                model.setValueAt(moneyAmountField.getText(), row, 2);
                model.setValueAt(awardField.getText(), row, 3);
                model.setValueAt(progressField.getText(), row, 4);
            }
        }

        private void editTask(int row, DefaultTableModel model) {
            String name = (String) model.getValueAt(row, 0);
            String description = (String) model.getValueAt(row, 1);
            String award = (String) model.getValueAt(row, 2);

            JTextField nameField = new JTextField(name);
            JTextField descriptionField =  new JTextField(description);
            JTextField awardField = new JTextField(award);

            Object[] message = {
                    "Name:", nameField,
                    "Description:", descriptionField,
                    "Award:", awardField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit Task", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                model.setValueAt(nameField.getText(), row, 0);
                model.setValueAt(descriptionField.getText(), row, 1);
                model.setValueAt(awardField.getText(), row, 2);
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new KidDetailsFrame("Lucy", "$500").setVisible(true));
//    }
}