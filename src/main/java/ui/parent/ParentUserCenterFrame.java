package ui.parent;

import core.AccountManager;
import core.ParentAccount;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Set;



/**
 * The `ParentUserCenterFrame` class is a graphical user interface component that extends the `ParentPageFrame` class.
 * It provides a frame for parent users to view their account information and change their password.
 * @author Ruihang Zhang
 */
public class ParentUserCenterFrame extends ParentPageFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;
    /**
     * Constructs a `ParentUserCenterFrame` instance with the specified `AccountManager` and `ParentAccount`.
     *
     * @param accountManager The `AccountManager` instance responsible for managing accounts
     * @param parentAccount The `ParentAccount` instance representing the parent account
     */

    /**
     * Constructs a ParentUserCenterFrame.
     *
     * @param accountManager
     *        the account manager to manage parent accounts
     *
     * @param parentAccount
     *        the parent account associated with this frame
     */
    public ParentUserCenterFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("User Center", accountManager, parentAccount);
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // *** Information Section ***
        // Set up heading for Information section
        JLabel infoHeading = new JLabel("Information");
        infoHeading.setFont(new Font("Calibri", Font.BOLD, 18));
        infoHeading.setForeground(new Color(0xF868B0)); // Pink color as shown in the sketch
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(infoHeading, gbc);

        // Set up panel for User name, ID, and account type labels
        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set margins

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.NORTHWEST; // Set component alignment within cell
        g.insets = new Insets(5, 5, 5, 5); // Set component spacing
        g.gridx = 0; // Initial x-coordinate
        g.gridy = 0; // Initial y-coordinate

        // Add labels
        addLabeledComponents(labelPanel, g, "User Name:", parentAccount.getUsername());
        addLabeledComponents(labelPanel, g, "ID:", parentAccount.getAccountId());
        Set<String> kidsAccountIds = parentAccount.getChildAccountIds();
        int count = 1;
        for (String accountID : kidsAccountIds) {
            addLabeledComponents(labelPanel, g, "Kids Account " + count, accountID);
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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 2;
        lowerPanel.add(passwordHeading, gbc);

        // Set up panel for Password labels and fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);

        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST; // Set component alignment within cell
        gb.insets = new Insets(5, 5, 5, 5);

        // Add old password field
        gb.gridx = 0;
        gb.gridy = 0;
        JLabel oldPassword = new JLabel("Old Password:");
        fieldsPanel.add(oldPassword, gb);
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
        saveButton.addActionListener(e -> validateAndChangePassword(accountManager, parentAccount));
        buttonPanel.add(saveButton);
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns
        lowerPanel.add(buttonPanel, gbc);

        setVisible(true);
    }

    /**
     * Adds labeled components (label and value) to the specified panel using the provided `GridBagConstraints`.
     *
     * @param panel The `JPanel` to which the labeled components will be added
     * @param gbc The `GridBagConstraints` used for positioning the components
     * @param labelText The text for the label
     * @param valueText The text for the value
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
     * Validates the entered passwords and changes the parent account's password if the validation is successful.
     *
     * @param accountManager The `AccountManager` instance responsible for managing accounts
     * @param parentAccount The `ParentAccount` instance representing the parent account
     */
    private void validateAndChangePassword(AccountManager accountManager, ParentAccount parentAccount) {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword1 = new String(newPasswordField1.getPassword());
        String newPassword2 = new String(newPasswordField2.getPassword());

        // Verify if the old password is correct
        if (!parentAccount.getPassword().equals(oldPassword)) {
            JOptionPane.showMessageDialog(this, "Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPassword1.equals("")) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify if the two new passwords match
        if (!newPassword1.equals(newPassword2)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform the password change operation
        parentAccount.setPassword(newPassword2);
        JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        accountManager.saveAccount(parentAccount);
    }
}
