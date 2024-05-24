package ui;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import core.Task;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssignmentFrame extends ParentPageFrame {
    private JTextField targetnameField;
    private JTextField taskcontentField;
    private JTextField awardField;
    private JTextField deadlineField;
    private ChildAccount childAccount;
    private ManageTasksFrame manageTasksFrame;

    public AssignmentFrame(AccountManager accountManager, ParentAccount parentAccount, ChildAccount childAccount) {
        super("Assign a Task", accountManager, parentAccount);
        this.childAccount = childAccount;
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Information Section
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
        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);

        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.insets = new Insets(5, 5, 5, 5);

        gb.gridx = 0;
        gb.gridy = 0;
        JLabel goalName = new JLabel("Target Name");
        fieldsPanel.add(goalName, gb);
        gb.gridx = 1;
        targetnameField = new JTextField(20);
        fieldsPanel.add(targetnameField, gb);

        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("Task Content"), gb);
        gb.gridx = 1;
        taskcontentField = new JTextField(20);
        fieldsPanel.add(taskcontentField, gb);

        gb.gridx = 0;
        gb.gridy = 2;
        fieldsPanel.add(new JLabel("Award Amount:"), gb);
        gb.gridx = 1;
        awardField = new JTextField(20);
        fieldsPanel.add(awardField, gb);

        gb.gridx = 0;
        gb.gridy = 3;
        fieldsPanel.add(new JLabel("Deadline:"), gb);
        gb.gridx = 1;
        deadlineField = new JTextField(20);
        fieldsPanel.add(deadlineField, gb);

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
                updateInformation(accountManager,parentAccount);
                ManageTasksFrame manageTasksFrame = new ManageTasksFrame(accountManager, parentAccount);
                manageTasksFrame.setVisible(true);
                this.dispose();
            }
        });

        returnButton.addActionListener(e -> returnToManageTasksFrame(accountManager, parentAccount));
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(returnButton);
        lowerPanel.add(buttonPanel, gbc);

        setVisible(true);
    }

    private boolean anyTextFieldIsEmpty() {
        return targetnameField.getText().isEmpty() ||
                taskcontentField.getText().isEmpty() ||
                awardField.getText().isEmpty() ||
                deadlineField.getText().isEmpty();
    }

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

    private void returnToManageTasksFrame() {
        dispose();
    }

    private void updateInformation(AccountManager accountManager,ParentAccount parentAccount) {
        String taskName = targetnameField.getText();
        String description = taskcontentField.getText();
        double award;
        Date dueDate;

        // Parse the award amount
        try {
            award = Double.parseDouble(awardField.getText());
        } catch (NumberFormatException e) {
            showInvalidInfoDialog();
            return;
        }

        // Parse the deadline
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dueDate = dateFormat.parse(deadlineField.getText());
        } catch (ParseException e) {
            showInvalidInfoDialog();
            return;
        }

        // Create new Task
        Task newTask = new Task(taskName, description, award, dueDate);

        // Add the task to the child account
        childAccount.addTask(newTask);
        accountManager.saveAccount(childAccount);



        accountManager.saveAccount(parentAccount);

        // Update the ManageTasksFrame

        // Close the AssignmentFrame
        dispose();
    }

    private void returnToManageTasksFrame(AccountManager accountManager, ParentAccount parentAccount) {
        // 关闭当前的 GoalFrame
        ManageTasksFrame manageTasksFrame = new ManageTasksFrame(accountManager, parentAccount);
        manageTasksFrame.setVisible(true);
        this.dispose();
    }
}
