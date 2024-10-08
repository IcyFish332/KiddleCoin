package ui.parent;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.SavingGoal;
import ui.parent.ManageGoalsFrame;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import java.awt.*;

/**
 * GoalFrame is a graphical user interface frame designed for setting or editing savings goals for a child's account.
 * Parents can use this frame to specify the details of the savings goal, including the goal amount, target date, and description.
 *
 * This class extends the ParentPageFrame class and is part of the parent UI package. It utilizes components from the core package for account management.
 *
 * @author Yilin Jin
 */

public class GoalFrame extends ParentPageFrame {
    private ChildAccount childAccount;
    private JTextField goalnameField;
    private JTextField targetField;
    private JTextField awardField;
    private JTextArea descriptionArea;
    private AccountManager accountManager;
    private ParentAccount parentAccount;

    /**
     * Constructs a GoalFrame for setting a new goal.
     *
     * @param accountManager The account manager
     * @param parentAccount The parent account
     * @param childAccount The child account
     */
    public GoalFrame(AccountManager accountManager, ParentAccount parentAccount, ChildAccount childAccount) {
        this(accountManager, parentAccount, childAccount, null);
    }

    /**
     * Constructs a GoalFrame for editing an existing goal.
     *
     * @param accountManager The account manager
     * @param parentAccount The parent account
     * @param childAccount The child account
     * @param goal The existing saving goal
     */
    public GoalFrame(AccountManager accountManager, ParentAccount parentAccount, ChildAccount childAccount, SavingGoal goal) {
        super(goal == null ? "Set a Goal" : "Edit Goal", accountManager, parentAccount);
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        setLocationRelativeTo(null);

        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel infoHeading = new JLabel("Information");
        infoHeading.setFont(new Font("Calibri", Font.BOLD, 18));
        infoHeading.setForeground(new Color(0xF868B0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(infoHeading, gbc);

        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.NORTHWEST;
        g.insets = new Insets(5, 5, 5, 5);
        g.gridx = 0;
        g.gridy = 0;

        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);

        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.insets = new Insets(5, 5, 5, 5);

        gb.gridx = 0;
        gb.gridy = 0;
        JLabel goalNameLabel = new JLabel("Goal Name");

        fieldsPanel.add(goalNameLabel, gb);
        gb.gridx = 1;
        goalnameField = new JTextField(20);
        fieldsPanel.add(goalnameField, gb);

        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("Target:"), gb);
        gb.gridx = 1;
        targetField = new JTextField(20);
        fieldsPanel.add(targetField, gb);

        gb.gridx = 0;
        gb.gridy = 2;
        fieldsPanel.add(new JLabel("Award:      $"), gb);
        gb.gridx = 1;
        awardField = new JTextField(20);
        fieldsPanel.add(awardField, gb);

        gb.gridx = 0;
        gb.gridy = 3;
        fieldsPanel.add(new JLabel("Description:"), gb);
        gb.gridx = 1;
        descriptionArea = new JTextArea(5, 20);
        fieldsPanel.add(descriptionArea, gb);

        if (goal != null) {
            goalnameField.setText(goal.getName());
            targetField.setText(String.valueOf(goal.getTargetAmount()));
            awardField.setText(String.valueOf(goal.getReward()));
            descriptionArea.setText(goal.getDescription());
        }

        gbc.gridy = 3;
        lowerPanel.add(fieldsPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");

        submitButton.addActionListener(e -> {
            if (anyTextFieldIsEmpty()) {
                showInvalidInfoDialog();
            } else {
                updateInformation(accountManager, parentAccount, goal);
                returnToManageGoalsFrame(accountManager, parentAccount);
            }
        });

        returnButton.addActionListener(e -> returnToManageGoalsFrame(accountManager, parentAccount));

        gbc.gridy = 4;
        gbc.gridwidth = 2;

        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(returnButton);

        lowerPanel.add(buttonPanel, gbc);
        setVisible(true);
    }

    /**
     * Updates the information of the goal.
     *
     * @param accountManager The account manager
     * @param parentAccount The parent account
     * @param goal The saving goal to be updated
     */
    private void updateInformation(AccountManager accountManager, ParentAccount parentAccount, SavingGoal goal) {
        String goalsName = goalnameField.getText();
        double target = Double.parseDouble(targetField.getText());
        double award = Double.parseDouble(awardField.getText());
        String description = descriptionArea.getText();

        if (goal == null) {
            goal = new SavingGoal(goalsName, description, target, award);
            childAccount.addSavingGoal(goal);
        } else {
            goal.setName(goalsName);
            goal.setDescription(description);
            goal.setTargetAmount(target);
            goal.setReward(award);
        }

        accountManager.saveAccount(childAccount);
        accountManager.saveAccount(parentAccount);
    }

    /**
     * Checks if any text field is empty.
     *
     * @return true if any text field is empty, false otherwise
     */
    private boolean anyTextFieldIsEmpty() {
        return goalnameField.getText().isEmpty() ||
                targetField.getText().isEmpty() ||
                awardField.getText().isEmpty() ||
                descriptionArea.getText().isEmpty();
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
     * Returns to the ManageGoalsFrame.
     *
     * @param accountManager The account manager
     * @param parentAccount The parent account
     */
    private void returnToManageGoalsFrame(AccountManager accountManager, ParentAccount parentAccount) {
        ManageGoalsFrame manageGoalsFrame = new ManageGoalsFrame(accountManager, parentAccount);
        manageGoalsFrame.setVisible(true);
        this.dispose();
    }
}
