package ui.kid.BalanceManagement;

import ui.template.BigButton;
import core.ChildAccount;
import core.AccountManager;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code SaveGoalsFrame} class provides a user interface for saving money
 * into a child's savings goals. It allows the user to input an amount and submit the savings.
 *
 * Author: YaoZelei
 */
public class SaveGoalsFrame extends JFrame {

    private ChildAccount childAccount;
    private AccountManager accountManager;
    private BalanceManagementFrame balanceManagementFrame;
    private JTextField amountField;

    /**
     * Constructs a new {@code SaveGoalsFrame} with the specified child account, account manager,
     * and balance management frame.
     *
     * @param childAccount The child account to save money into.
     * @param accountManager The account manager responsible for handling account-related operations.
     * @param balanceManagementFrame The parent frame that manages the balance and savings.
     */
    public SaveGoalsFrame(ChildAccount childAccount, AccountManager accountManager, BalanceManagementFrame balanceManagementFrame) {
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        this.balanceManagementFrame = balanceManagementFrame;
        setTitle("Save Money into My Goals");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    /**
     * Initializes the user interface components for the save goals frame.
     */
    private void initUI() {
        // Use BoxLayout to create a vertically arranged main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for inputting the amount
        JPanel amountPanel = new JPanel(new BorderLayout(10, 10));
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(15);
        amountPanel.add(amountLabel, BorderLayout.WEST);
        amountPanel.add(amountField, BorderLayout.CENTER);
        mainPanel.add(amountPanel);

        // Add vertical spacing between the amount panel and the button panel
        mainPanel.add(Box.createVerticalStrut(20));

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");
        buttonPanel.add(submitButton);
        buttonPanel.add(returnButton);
        mainPanel.add(buttonPanel);

        // Add action listeners to the buttons
        submitButton.addActionListener(e -> onSubmit());
        returnButton.addActionListener(e -> dispose());

        // Add the main panel to the frame
        getContentPane().add(mainPanel);
    }

    /**
     * Handles the action to be performed when the submit button is clicked.
     * Reads the amount from the input field, saves it into the child's savings goals,
     * updates the account data, and notifies the user of the successful saving.
     */
    private void onSubmit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            childAccount.saveMoney(amount);
            // Save the updated account data
            accountManager.saveAccount(childAccount);
            JOptionPane.showMessageDialog(this, "Money saved to goal successfully!");
            balanceManagementFrame.updateLabels();  // Update the labels display
            dispose();  // Close the window
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }
}