package ui.parent;

import core.*;
import ui.parent.ManageTasksFrame;
import ui.template.BigButton;
import ui.template.ParentPageFrame;
import java.util.Date;
import core.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.awt.*;

public class AssignmentFrame extends ParentPageFrame {
    private ChildAccount childAccount;
    public JTextField taskNameField;
    public JTextField rewardField;
    public JTextArea descriptionArea;
    public JTextField dueDateField;

    private AccountManager accountManager;
    private ParentAccount parentAccount;
    public ManageTasksFrame manageTasksFrame;

    public AssignmentFrame(AccountManager accountManager, ParentAccount parentAccount, ChildAccount childAccount) {
        this(accountManager, parentAccount, childAccount, null);
    }

    public AssignmentFrame(AccountManager accountManager, ParentAccount parentAccount, ChildAccount childAccount, Task task) {
        super(task == null ? "Set a Task" : "Edit Task", accountManager, parentAccount);
        this.childAccount = childAccount;
        this.accountManager = accountManager;
        this.parentAccount = parentAccount;
        setLocationRelativeTo(null);


        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel infoHeading = new JLabel("Task Information");
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
        JLabel taskNameLabel = new JLabel("Task Name");

        fieldsPanel.add(taskNameLabel, gb);
        gb.gridx = 1;
        taskNameField = new JTextField(20);
        fieldsPanel.add(taskNameField, gb);

        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("Due Date (yyyy-MM-dd):"), gb);
        gb.gridx = 1;
        dueDateField = new JTextField(20);
        fieldsPanel.add(dueDateField, gb);

        gb.gridx = 0;
        gb.gridy = 2;
        fieldsPanel.add(new JLabel("Reward:      $"), gb);
        gb.gridx = 1;
        rewardField = new JTextField(20);
        fieldsPanel.add(rewardField, gb);

        gb.gridx = 0;
        gb.gridy = 3;
        fieldsPanel.add(new JLabel("Description:"), gb);
        gb.gridx = 1;
        descriptionArea = new JTextArea(5, 20);
        fieldsPanel.add(descriptionArea, gb);
        if (task != null) {
            taskNameField.setText(task.getName());
            dueDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate()));
            rewardField.setText(String.valueOf(task.getReward()));
            descriptionArea.setText(task.getDescription());
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
                updateInformation(accountManager, parentAccount, task);
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

    public void updateInformation(AccountManager accountManager, ParentAccount parentAccount, Task task) {
        String taskName = taskNameField.getText();
        String description = descriptionArea.getText();
        double award;
        Date dueDate;

        // 解析日期字符串并创建 Date 对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            award = Double.parseDouble(rewardField.getText());
        } catch (NumberFormatException e) {
            showInvalidInfoDialog();
            return;
        }

        // Parse the deadline
        try {
            dueDate = dateFormat.parse(dueDateField.getText());
        } catch (ParseException e) {
            showInvalidDateFormatDialog();
            return;
        }
        if (task == null) {
            task = new Task(taskName, description, award, dueDate);
            childAccount.addTask(task);
        } else {
            task.setName(taskName);
            task.setDescription(description);
            task.setReward(award);
            task.setDueDate(dueDate);
        }

        accountManager.saveAccount(childAccount);
        accountManager.saveAccount(parentAccount);
        dispose();
    }

    private boolean anyTextFieldIsEmpty() {
        return taskNameField.getText().isEmpty() ||
                rewardField.getText().isEmpty() ||
                descriptionArea.getText().isEmpty();
    }
    private void showInvalidDateFormatDialog() {
        JDialog dialog = new JDialog(this, "Invalid Date Format", true);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel("Invalid date format. Please use yyyy-MM-dd.");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(label);
        dialog.add(okButton);
        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
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

    private void returnToManageTasksFrame(AccountManager accountManager, ParentAccount parentAccount) {
        ManageTasksFrame manageTasksFrame = new ManageTasksFrame(accountManager, parentAccount);
        manageTasksFrame.setVisible(true);
        this.dispose();
    }


}