package ui.parent;

import core.Account;
import core.AccountManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;

import core.ChildAccount;
import core.ParentAccount;
import ui.template.ParentPageFrame;
import ui.template.BigButton;

/**
 * The ChildAccountManagementFrame serves as a graphical user interface for parents to manage their list of child accounts.
 *
 * This frame provides functionality to view, add, edit, or remove child accounts associated with the parent's account.
 *
 * @author Yifan Cao
 */

public class KidsListManagementFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private DefaultTableModel model;
    private JTable table;

    /**
     * Constructs a KidsListManagementFrame.
     *
     * @param accountManager The account manager.
     * @param parentAccount The parent account.
     */
    public KidsListManagementFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Kids' List Management", accountManager, parentAccount);
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        initComponents();
    }

    /**
     * Initializes the components of the frame.
     */
    private void initComponents() {
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setBackground(Color.WHITE);

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Kids' List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Add button
        BigButton addButton = new BigButton("Add");
        addButton.setBackground(Color.WHITE);
        addButton.addActionListener(this::addAccountAction);
        titlePanel.add(addButton, BorderLayout.EAST);

        lowerPanel.add(titlePanel, BorderLayout.NORTH);

        // Table model
        String[] columnNames = {"Name", "AccountID", "Savings", "Operations"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);

        for (String childAccountId : parentAccount.getChildAccountIds()) {
            Account account = accountManager.getAccount(childAccountId);
            if (account instanceof ChildAccount) {
                model.addRow(new Object[]{account.getUsername(), childAccountId, ((ChildAccount) account).getSavings(), ""});
            }
        }

        // Set table cell editor for savings
        table.getColumnModel().getColumn(2).setCellEditor(new SavingsEditor(new JTextField()));

        // Set cell renderer and editor for operations column
        table.getColumnModel().getColumn(3).setCellRenderer(new OperationsRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new OperationsEditor(new JCheckBox()));

        // Adjust column widths
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

    /**
     * Action performed when the add account button is clicked.
     *
     * @param e The action event.
     */
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

    /**
     * Custom cell editor for the savings column.
     */
    class SavingsEditor extends DefaultCellEditor {
        private JTextField textField;

        /**
         * Constructs a SavingsEditor.
         *
         * @param textField The text field to be used for editing.
         */
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

    /**
     * Custom cell renderer for the operations column.
     */
    class OperationsRenderer extends JPanel implements TableCellRenderer {
        private BigButton detailsButton, deleteButton;

        /**
         * Constructs an OperationsRenderer.
         */
        public OperationsRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(Color.WHITE);
            detailsButton = new BigButton("For More Details");
            deleteButton = new BigButton("Delete");

            detailsButton.setBackground(Color.WHITE);
            deleteButton.setBackground(Color.WHITE);

            add(detailsButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    /**
     * Custom cell editor for the operations column.
     */
    class OperationsEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected BigButton detailsButton, deleteButton;

        /**
         * Constructs an OperationsEditor.
         *
         * @param checkBox The checkbox used for editing.
         */
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

            detailsButton.addActionListener(e -> showKidDetails());
            deleteButton.addActionListener(e -> deleteKid());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        /**
         * Shows the details of the selected kid account.
         */
        private void showKidDetails() {
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
        }
    }

    /**
     * Deletes the selected kid account.
     */
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
