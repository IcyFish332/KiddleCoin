package ui;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {
    private AccountManager accountManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> accountTypeComboBox;

    public RegistrationFrame(AccountManager accountManager) {
        this.accountManager = accountManager;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Registration");
        setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Create an Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(255, 105, 180));
        panel.add(titleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Username:"), constraints);

        constraints.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Password:"), constraints);

        constraints.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(new JLabel("Confirm Password:"), constraints);

        constraints.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(new JLabel("Account Type:"), constraints);

        constraints.gridx = 1;
        String[] accountTypes = {"Kid Account", "Parent Account"};
        accountTypeComboBox = new JComboBox<>(accountTypes);
        panel.add(accountTypeComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        registerButton.setBackground(new Color(255, 192, 203));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerAccount();
            }
        });
        panel.add(registerButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void registerAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String accountType = (String) accountTypeComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accountManager.isUsernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accountType.equals("Kid Account")) {
            ChildAccount childAccount = accountManager.createChildAccount(username, password);
            JOptionPane.showMessageDialog(this, "Kid account registered successfully!\nAccount ID: " + childAccount.getAccountId(), "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        } else if (accountType.equals("Parent Account")) {
            ParentAccount parentAccount = accountManager.createParentAccount(username, password);
            JOptionPane.showMessageDialog(this, "Parent account registered successfully!\nAccount ID: " + parentAccount.getAccountId(), "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        }

        dispose();
    }
}