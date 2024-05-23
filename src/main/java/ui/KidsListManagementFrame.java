package ui;
import core.Account;
import core.AccountManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.ChildAccount;
import core.ParentAccount;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

public class KidsListManagementFrame extends ParentPageFrame {
    private  AccountManager accountManager;
    private ParentAccount parentAccount;
    private DefaultTableModel model;
    private JTable table;

    public KidsListManagementFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Kids' List Management", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        initComponents();
    }

    private void initComponents() {
        // 初始化组件并定位到 lowerPanel
        lowerPanel.setLayout(new BorderLayout()); // 设置 lowerPanel 的布局为 BorderLayout

        // 表格模型
        String[] columnNames = {"Name", "Balance", "Savings", "Operations"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setRowHeight(30);

        for (String childAccountId : parentAccount.getChildAccountIds()) {
            Account account = accountManager.getAccount(childAccountId);
            if (account instanceof ChildAccount) {
                model.addRow(new Object[]{account.getUsername(), childAccountId, ((ChildAccount) account).getSavings(), ""});
            }
        }

        // Operations列
        table.getColumnModel().getColumn(3).setCellRenderer(new OperationsRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new OperationsEditor(new JCheckBox()));
        // 调整列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Name列
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Account列
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Savings列
        table.getColumnModel().getColumn(3).setPreferredWidth(400); // Operations列宽度调整

        JScrollPane scrollPane = new JScrollPane(table);
        lowerPanel.add(scrollPane, BorderLayout.CENTER); // 将表格添加到 lowerPanel 的中心

        // 添加按钮
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this::addAccountAction);
        lowerPanel.add(addButton, BorderLayout.SOUTH); // 将按钮添加到 lowerPanel 的南部

        this.contentPanel.add(lowerPanel, BorderLayout.CENTER); // 将 lowerPanel 添加到 contentPanel
    }


    private void addAccountAction(ActionEvent e) {
        JTextField accountIdField = new JTextField();
        Object[] message = {
                "Account ID:", accountIdField
        };

        int option;
        do {
            option = JOptionPane.showConfirmDialog(null, message, "Add Kid Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                // 检查账户 ID 字段是否已填写
                if (accountIdField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the Account ID field.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String accountId = accountIdField.getText().trim();
                    Account account = accountManager.getAccount(accountId);

                    if (account instanceof ChildAccount) {
                        // 将账户 ID 添加到 ParentAccount 的 childAccountIds 集合中
                        parentAccount.addChildAccount(accountId);

                        // 将 ParentAccount ID 添加到 ChildAccount 的 parentAccountIds 集合中
                        ((ChildAccount) account).addParentAccount(parentAccount.getAccountId());

                        // 保存更新后的账户信息
                        accountManager.saveAccount(parentAccount);
                        accountManager.saveAccount(account);

                        // 在表格中添加新行
                        model.addRow(new Object[]{account.getUsername(), accountId, ((ChildAccount) account).getSavings(), ""});
                        break; // 跳出循环
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Account ID. Please enter a valid Child Account ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                break; // 如果用户选择取消，也跳出循环
            }
        } while (option != JOptionPane.CANCEL_OPTION);
    }


    class OperationsRenderer extends JPanel implements TableCellRenderer {
        BigButton detailsButton, deleteButton;

        public OperationsRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));  // 使用 BoxLayout 管理布局
            detailsButton = new BigButton("For More Details");
            deleteButton = new BigButton("Delete");

            add(detailsButton);
            add(deleteButton);

            adjustButtonVisibility(true); // 确保按钮在初始化时都是可见的
        }

        private void adjustButtonVisibility(boolean isVisible) {
            detailsButton.setVisible(isVisible);
            deleteButton.setVisible(isVisible);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            adjustButtonVisibility(true);  // 确保渲染时按钮都可见
            return this;
        }
    }

    class OperationsEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected BigButton detailsButton, deleteButton;

        public OperationsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));  // 使用 BoxLayout
            detailsButton = new BigButton("For More Details");
            deleteButton = new BigButton("Delete");

            panel.add(detailsButton);
            panel.add(deleteButton);

            adjustButtonVisibility(true);

            // 配置详细信息按钮的监听器
            detailsButton.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String accountId = model.getValueAt(row, 1).toString(); // 获取选中行的Account ID
                    Account account = accountManager.getAccount(accountId);
                    if (account instanceof ChildAccount) {
                        ChildAccount childAccount = (ChildAccount) account;
                        KidDetailsFrame detailsFrame = new KidDetailsFrame(accountManager, parentAccount, childAccount);
                        detailsFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Account ID. Please select a valid Child Account.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            deleteButton.addActionListener(e -> deleteKid());
        }


        private void adjustButtonVisibility(boolean isVisible) {
            detailsButton.setVisible(isVisible);
            deleteButton.setVisible(isVisible);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            adjustButtonVisibility(true);  // 确保编辑时按钮都可见
            return panel;
        }
    }


    private void deleteKid() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String accountId = (String) table.getValueAt(selectedRow, 1);
            Account account = accountManager.getAccount(accountId);
            if (account instanceof ChildAccount) {
                // 解除关联
                parentAccount.removeChildAccount(accountId);
                ((ChildAccount) account).removeParentAccount(parentAccount.getAccountId());

                // 保存更新
                accountManager.saveAccount(parentAccount);
                accountManager.saveAccount(account);

                // 从表格中移除行
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Account selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No account selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}