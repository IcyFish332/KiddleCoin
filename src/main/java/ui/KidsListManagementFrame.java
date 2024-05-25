package ui;

import core.Account;
import core.AccountManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.ChildAccount;
import core.ParentAccount;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

public class KidsListManagementFrame extends ParentPageFrame {
    private AccountManager accountManager;
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
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setBackground(Color.WHITE);

        // 标题行
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel();
        titleLabel.setBackground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // 添加按钮
        BigButton addButton = new BigButton("Add");
        addButton.setBackground(Color.WHITE);
        addButton.addActionListener(this::addAccountAction);
        titlePanel.add(addButton, BorderLayout.EAST);

        lowerPanel.add(titlePanel, BorderLayout.NORTH);

        // 表格模型
        String[] columnNames = {"Name", "AccountID", "Savings", "Operations"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.WHITE);  // Set grid color to white to blend with background
        table.setShowGrid(true);

        for (String childAccountId : parentAccount.getChildAccountIds()) {
            Account account = accountManager.getAccount(childAccountId);
            if (account instanceof ChildAccount) {
                model.addRow(new Object[]{account.getUsername(), childAccountId, ((ChildAccount) account).getSavings(), ""});
            }
        }

        // 设置表格列的单元格编辑器
        table.getColumnModel().getColumn(2).setCellEditor(new SavingsEditor(new JTextField()));

        // Operations列
        table.getColumnModel().getColumn(3).setCellRenderer(new OperationsRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new OperationsEditor(new JCheckBox()));

        // 调整列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(400);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        lowerPanel.add(scrollPane, BorderLayout.CENTER);

        this.contentPanel.setBackground(Color.WHITE);
        this.contentPanel.add(lowerPanel, BorderLayout.CENTER);
    }

    private void addAccountAction(ActionEvent e) {
        JTextField accountIdField = new JTextField();
        accountIdField.setBackground(Color.WHITE);
        Object[] message = {
                "Account ID:", accountIdField
        };

        int option;
        do {
            option = JOptionPane.showConfirmDialog(null, message, "Add Kid Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                if (accountIdField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the Account ID field.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String accountId = accountIdField.getText().trim();
                    Account account = accountManager.getAccount(accountId);

                    if (account instanceof ChildAccount) {
                        parentAccount.addChildAccount(accountId);
                        ((ChildAccount) account).addParentAccount(parentAccount.getAccountId());

                        accountManager.saveAccount(parentAccount);
                        accountManager.saveAccount(account);

                        model.addRow(new Object[]{account.getUsername(), accountId, ((ChildAccount) account).getSavings(), ""});
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Account ID. Please enter a valid Child Account ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                break;
            }
        } while (option != JOptionPane.CANCEL_OPTION);
    }

    class SavingsEditor extends DefaultCellEditor {
        private JTextField textField;

        public SavingsEditor(JTextField textField) {
            super(textField);
            this.textField = textField;
            this.textField.setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            textField.setText(value != null ? value.toString() : "");
            return textField;
        }

        @Override
        public Object getCellEditorValue() {
            return textField.getText();
        }

        @Override
        public boolean stopCellEditing() {
            int row = table.getSelectedRow();
            String accountId = (String) table.getValueAt(row, 1);
            Account account = accountManager.getAccount(accountId);

            if (account instanceof ChildAccount) {
                try {
                    double savings = Double.parseDouble(textField.getText());
                    ((ChildAccount) account).setSavings(savings);
                    accountManager.saveAccount(account);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return super.stopCellEditing();
        }
    }

    class OperationsRenderer extends JPanel implements TableCellRenderer {
        BigButton detailsButton, deleteButton;

        public OperationsRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(Color.WHITE);
            detailsButton = new BigButton("For More Details");
            deleteButton = new BigButton("Delete");

            detailsButton.setBackground(Color.WHITE);
            deleteButton.setBackground(Color.WHITE);

            add(detailsButton);
            add(deleteButton);

            adjustButtonVisibility(true);
        }

        private void adjustButtonVisibility(boolean isVisible) {
            detailsButton.setVisible(isVisible);
            deleteButton.setVisible(isVisible);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            adjustButtonVisibility(true);
            return this;
        }
    }

    class OperationsEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected BigButton detailsButton, deleteButton;

        public OperationsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setBackground(Color.WHITE);
            detailsButton = new BigButton("For More Details");
            deleteButton = new BigButton("Delete");

            detailsButton.setBackground(Color.WHITE);
            deleteButton.setBackground(Color.WHITE);

            panel.add(detailsButton);
            panel.add(deleteButton);

            adjustButtonVisibility(true);

            detailsButton.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String accountId = model.getValueAt(row, 1).toString();
                    Account account = accountManager.getAccount(accountId);
                    if (account instanceof ChildAccount) {
                        ChildAccount childAccount = (ChildAccount) account;
                        KidDetailsFrame detailsFrame = new KidDetailsFrame(accountManager, parentAccount, childAccount);
                        detailsFrame.setVisible(true);
                        dispose();
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
            adjustButtonVisibility(true);
            return panel;
        }
    }

    private void deleteKid() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String accountId = (String) table.getValueAt(selectedRow, 1);
            Account account = accountManager.getAccount(accountId);
            if (account instanceof ChildAccount) {
                parentAccount.removeChildAccount(accountId);
                ((ChildAccount) account).removeParentAccount(parentAccount.getAccountId());

                accountManager.saveAccount(parentAccount);
                accountManager.saveAccount(account);

                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Account selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No account selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
