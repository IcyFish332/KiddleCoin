package ui.parent;

import core.AccountManager;
import core.ParentAccount;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * A frame for giving an award to a child account.
 */
public class AwardFrame extends ParentPageFrame {
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private DefaultTableModel goalsModel;
    private JTextField awardNameField;
    private JTextField amountField;

    /**
     * Constructs an AwardFrame for giving an award.
     *
     * @param accountManager The account manager
     * @param parentAccount The parent account
     */
    public AwardFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Give an Award", accountManager, parentAccount);
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Information section heading
        JLabel infoHeading = new JLabel("Information");
        infoHeading.setFont(new Font("Calibri", Font.BOLD, 18));
        infoHeading.setForeground(new Color(0xF868B0)); // Red color as shown in the sketch
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(infoHeading, gbc);

        // Panel for user name, ID, and account type labels
        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.NORTHWEST;
        g.insets = new Insets(5, 5, 5, 5);
        g.gridx = 0;
        g.gridy = 0;

        // Add label panel to lower panel
        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);

        // Panel for award name and amount fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);

        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.insets = new Insets(5, 5, 5, 5);

        // Add award name field
        gb.gridx = 0;
        gb.gridy = 0;
        JLabel awardNameLabel = new JLabel("Award Name");
        fieldsPanel.add(awardNameLabel, gb);
        gb.gridx = 1;
        awardNameField = new JTextField(20);
        fieldsPanel.add(awardNameField, gb);

        // Add award amount field
        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("Award   $"), gb);
        gb.gridx = 1;
        amountField = new JTextField(20);
        fieldsPanel.add(amountField, gb);
        lowerPanel.add(fieldsPanel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");

        submitButton.addActionListener(e -> {
            if (anyTextFieldIsEmpty()) {
                showInvalidInfoDialog();
            } else {
                // Update code (implementation not shown)
                this.dispose();
            }
        });

        returnButton.addActionListener(e -> returnToManageGoalsFrame());
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns

        gbc.gridy = 4;
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(returnButton);
        gbc.gridwidth = 2; // Span across two columns

        lowerPanel.add(buttonPanel, gbc);
        setVisible(true);
    }

    /**
     * Adds labeled components to the specified panel.
     *
     * @param panel The panel to which components are added
     * @param gbc The GridBagConstraints for layout
     * @param labelText The text for the label
     * @param valueText The text for the value
     */
    private void addLabeledComponents(JPanel panel, GridBagConstraints gbc, String labelText, String valueText) {
        gbc.gridx = 0; // Label in the first column
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.RIGHT); // Right-align label
        panel.add(label, gbc);

        gbc.gridx = 1; // Value in the second column
        JLabel value = new JLabel(valueText);
        value.setHorizontalAlignment(SwingConstants.LEFT); // Left-align value
        panel.add(value, gbc);

        gbc.gridy++; // Move to the next row
    }

    /**
     * Checks if any text field is empty.
     *
     * @return true if any text field is empty, false otherwise
     */
    private boolean anyTextFieldIsEmpty() {
        return awardNameField.getText().isEmpty() ||
                amountField.getText().isEmpty();
    }

    /**
     * Shows a dialog indicating invalid information.
     */
    private void showInvalidInfoDialog() {
        JDialog dialog = new JDialog(this, "Invalid Information", true);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel("Invalid Information");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(label);
        dialog.add(okButton);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Returns to the manage goals frame.
     */
    private void returnToManageGoalsFrame() {
        dispose();
    }
}
