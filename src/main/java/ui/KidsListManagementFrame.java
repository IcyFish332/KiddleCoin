package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KidsListManagementFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public KidsListManagementFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Kids' List Management");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // 添加标题
        JLabel titleLabel = new JLabel("Kids' List Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180)); // 粉色字体
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // 表格模型
        String[] columnNames = {"Name", "Account", "Savings", "Operations"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setRowHeight(30);

        // Operations列
        table.getColumnModel().getColumn(3).setCellRenderer(new OperationsRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new OperationsEditor(new JCheckBox()));
        // 调整列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Name列
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Account列
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Savings列
        table.getColumnModel().getColumn(3).setPreferredWidth(400); // Operations列宽度调整

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 添加按钮
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this::addAccountAction);
        getContentPane().add(addButton, BorderLayout.SOUTH);
    }

    private void addAccountAction(ActionEvent e) {
        JTextField nameField = new JTextField();
        JTextField accountField = new JTextField();
        JTextField savingsField = new JTextField();
        Object[] message = {
                "Name:", nameField,
                "Account:", accountField,
                "Savings:", savingsField
        };

        int option;
        do {
            option = JOptionPane.showConfirmDialog(null, message, "Add Kid Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                // 检查所有输入字段是否已填写
                if (nameField.getText().trim().isEmpty() || accountField.getText().trim().isEmpty() || savingsField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 如果所有字段都已填写，则添加行并跳出循环
                    model.addRow(new Object[]{nameField.getText(), accountField.getText(), savingsField.getText(), ""});
                    break; // 跳出循环
                }
            } else {
                break; // 如果用户选择取消，也跳出循环
            }
        } while (option != JOptionPane.CANCEL_OPTION);
    }


    class OperationsRenderer extends JPanel implements TableCellRenderer {
        JButton detailsButton, editButton, deleteButton;

        public OperationsRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            detailsButton = new JButton("For More Details");
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");
            add(detailsButton);
            add(editButton);
            add(deleteButton);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class OperationsEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected JButton detailsButton, editButton, deleteButton;

        public OperationsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            detailsButton = new JButton("For More Details");
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            panel.add(detailsButton);
            panel.add(editButton);
            panel.add(deleteButton);

            // 配置详细信息按钮的监听器
            detailsButton.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String name = model.getValueAt(row, 0).toString();
                    String savings = model.getValueAt(row, 2).toString();
                    openDetailsFrame(name, savings);
                }
            });
            editButton.addActionListener(e -> editKid());
            deleteButton.addActionListener(e -> deleteKid());
        }

        private void openDetailsFrame(String name, String savings) {
            KidDetailsFrame detailsFrame = new KidDetailsFrame(name, savings);
            detailsFrame.setVisible(true);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }
    }


    private void editKid() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) table.getValueAt(selectedRow, 0);
            String accountId = (String) table.getValueAt(selectedRow, 1);
            String savings = (String) table.getValueAt(selectedRow, 2);

            JTextField nameField = new JTextField(name);
            JTextField accountIdField = new JTextField(accountId);
            JTextField savingsField = new JTextField(savings);
            JPanel dialogPanel = new JPanel(new GridLayout(0, 2));
            dialogPanel.add(new JLabel("Name:"));
            dialogPanel.add(nameField);
            dialogPanel.add(new JLabel("Account ID:"));
            dialogPanel.add(accountIdField);
            dialogPanel.add(new JLabel("Savings:"));
            dialogPanel.add(savingsField);

            int result = JOptionPane.showConfirmDialog(null, dialogPanel,
                    "Edit details for the selected kid account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                table.setValueAt(nameField.getText(), selectedRow, 0);
                table.setValueAt(accountIdField.getText(), selectedRow, 1);
                table.setValueAt(savingsField.getText(), selectedRow, 2);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No account selected!");
        }
    }

    private void deleteKid() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "No account selected!");
        }
    }

    public static void main(String[] args) {
        new KidsListManagementFrame().setVisible(true);
    }
}
