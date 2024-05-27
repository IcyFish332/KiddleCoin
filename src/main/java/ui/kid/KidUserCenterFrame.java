package ui.kid;

import core.AccountManager;
import core.ChildAccount;
import ui.template.BigButton;
import ui.template.KidPageFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * This class represents the user center frame for kids.
 *
 * It provides a UI for displaying user information and changing the password.
 *
 * @Author: Ruihang Zhang
 */
public class KidUserCenterFrame extends KidPageFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;

    /**
     * Constructs a KidUserCenterFrame.
     *
     * @param accountManager
     *        the account manager to manage child accounts
     *
     * @param childAccount
     *        the child account associated with this frame
     */
    public KidUserCenterFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("User Center", accountManager, childAccount); // Call parent constructor to set title
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // *** Information Section ***
        // Lower Panel for user inputs and information label
        JLabel infoHeading = new JLabel("Information");
        infoHeading.setFont(new Font("Calibri", Font.BOLD, 18));
        infoHeading.setForeground(new Color(0xF868B0)); // Pink color as shown in the sketch
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        lowerPanel.add(infoHeading, gbc);

        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set margins

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.NORTHWEST; // Set component alignment within cell
        g.insets = new Insets(5, 5, 5, 5); // Set component spacing
        g.gridx = 0; // Initial x-coordinate
        g.gridy = 0; // Initial y-coordinate

        // Add labels
        addLabeledComponents(labelPanel, g, "User Name:", childAccount.getUsername());
        addLabeledComponents(labelPanel, g, "ID:", childAccount.getAccountId());
        Set<String> parentAccountIds = childAccount.getParentAccountIds();
        int count = 1;
        for (String accountID : parentAccountIds) {
            addLabeledComponents(labelPanel, g, "Parent Account " + count, accountID);
            count++;
        }

        // Add labelPanel to the center of lowerPanel
        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);

        // *** Change Password Section ***
        // Set up heading for Change Password section
        JLabel passwordHeading = new JLabel("Change Password");
        passwordHeading.setFont(new Font("Calibri", Font.BOLD, 16));
        passwordHeading.setForeground(new Color(0xF868B0)); // Pink color for this heading too
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridy = 2;
        lowerPanel.add(passwordHeading, gbc);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gb = new GridBagConstraints();
        gb.fill = GridBagConstraints.HORIZONTAL;
        gb.weightx = 1.0;
        gb.insets = new Insets(5, 5, 5, 5);

        // Add old password field
        gb.gridx = 0;
        gb.gridy = 0;
        fieldsPanel.add(new JLabel("Old Password:"), gb);
        gb.gridx = 1;
        oldPasswordField = new JPasswordField(20);
        fieldsPanel.add(oldPasswordField, gb);

        // Add new password field 1
        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("New Password:"), gb);
        gb.gridx = 1;
        newPasswordField1 = new JPasswordField(20);
        fieldsPanel.add(newPasswordField1, gb);

        // Add new password field 2
        gb.gridx = 0;
        gb.gridy = 2;
        fieldsPanel.add(new JLabel("Confirm New Password:"), gb);
        gb.gridx = 1;
        newPasswordField2 = new JPasswordField(20);
        fieldsPanel.add(newPasswordField2, gb);

        gbc.gridy = 3;
        lowerPanel.add(fieldsPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton saveButton = new BigButton("Save");
        saveButton.addActionListener(e -> validateAndChangePassword(accountManager, childAccount));
        buttonPanel.add(saveButton);
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns
        fieldsPanel.add(buttonPanel, gbc);

        setVisible(true);
    }

    /**
     * Adds labeled components to a specified panel.
     *
     * @param panel
     *        the panel to add the components to
     *
     * @param gbc
     *        the GridBagConstraints for layout
     *
     * @param labelText
     *        the text for the label
     *
     * @param valueText
     *        the text for the value label
     */
    private void addLabeledComponents(JPanel panel, GridBagConstraints gbc, String labelText, String valueText) {
        gbc.gridx = 0; // Label in the first column
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.RIGHT); // Right-align the label
        panel.add(label, gbc);

        gbc.gridx = 1; // Value in the second column
        JLabel value = new JLabel(valueText);
        value.setHorizontalAlignment(SwingConstants.LEFT); // Left-align the value
        panel.add(value, gbc);

        gbc.gridy++; // Move to the next row
    }

    /**
     * Validates and changes the password for the child account.
     *
     * @param accountManager
     *        the account manager to manage child accounts
     *
     * @param childAccount
     *        the child account whose password will be changed
     */
    private void validateAndChangePassword(AccountManager accountManager, ChildAccount childAccount) {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword1 = new String(newPasswordField1.getPassword());
        String newPassword2 = new String(newPasswordField2.getPassword());

        if (newPassword1.equals("")) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify if the old password is correct
        if (!childAccount.getPassword().equals(oldPassword)) {
            JOptionPane.showMessageDialog(this, "Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify if the two new passwords match
        if (!newPassword1.equals(newPassword2)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform the password change operation
        childAccount.setPassword(newPassword2);
        JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        accountManager.saveAccount(childAccount);
    }
}
