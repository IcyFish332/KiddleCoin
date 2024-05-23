package ui;
import core.AccountManager;
import core.ParentAccount;
import core.ChildAccount;
import ui.template.BigButton;
import ui.template.ParentPageFrame;
import ui.ManageGoalsFrame;
import core.SavingGoal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

public class GoalFrame extends ParentPageFrame {
    private ChildAccount childAccount;
    private ManageGoalsFrame manageGoalsFrame;
    private JTextField goalnameField;
    private JTextField targetField;
    private JTextField awardField;
    private JTextArea Description;

    private AccountManager accountManager;
    private ParentAccount parentAccount;

    public GoalFrame(AccountManager accountManager, ParentAccount parentAccount,ChildAccount childAccount) {
        super("Set a Goal", accountManager, parentAccount);
        this.childAccount=childAccount;
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

//        JLabel infoLabel = new JLabel("Information");
//        lowerPanel.add(infoLabel, BorderLayout.NORTH);

        // *** Information Section ***
        // Set up heading for Information section
        JLabel infoHeading = new JLabel("Information");
        infoHeading.setFont(new Font("Calibri", Font.BOLD, 18));
        infoHeading.setForeground(new Color(0xF868B0)); // Red color as shown in the sketch
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        lowerPanel.add(infoHeading, gbc);

        //set up panel for User name, ID, and account type labels
        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 设置边距

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.NORTHWEST; // 设置组件在单元格中的位置
        g.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距
        g.gridx = 0; // 初始x坐标
        g.gridy = 0; // 初始y坐标

        // 将labelPanel添加到lowerPanel的中部
        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);


        // Set up panel for Password labels and fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);

        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST; // 设置组件在单元格中的位置
        gb.insets = new Insets(5, 5, 5, 5);

        // Add old password field
        gb.gridx = 0;
        gb.gridy = 0;
        JLabel goalName = new JLabel("Goal Name");

        //oldPassword.setHorizontalAlignment(SwingConstants.LEFT);
        fieldsPanel.add(goalName, gb);
        gb.gridx = 1;
        goalnameField = new JTextField(20);
        fieldsPanel.add(goalnameField, gb);

        // Add new password field 1
        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("Target:"), gb);
        gb.gridx = 1;
        targetField = new JTextField(20);
        fieldsPanel.add(targetField, gb);

        // Add new password field 2
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
        Description = new JTextArea(5,20);
        fieldsPanel.add(Description, gb);

        gbc.gridy = 3;
        lowerPanel.add(fieldsPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton= new BigButton("Return");

        submitButton.addActionListener(e -> {
            // Validate input fields (pseudo-code)
            if (anyTextFieldIsEmpty()) {
                // Update information (pseudo-code)
                showInvalidInfoDialog();
            } else {
                updateInformation(accountManager,parentAccount);
                ManageGoalsFrame manageGoalsFrame = new ManageGoalsFrame(accountManager, parentAccount);
                manageGoalsFrame.setVisible(true);
                this.dispose();
            }
        });

        returnButton.addActionListener(e -> returnToManageGoalsFrame(accountManager, parentAccount));

        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns

        gbc.gridy = 4;
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(returnButton);
        gbc.gridwidth = 2; // Span across two columns

        lowerPanel.add(buttonPanel, gbc);
        accountManager.saveAccount(parentAccount);
        setVisible(true);
    }


    private void updateInformation(AccountManager accountManager,ParentAccount parentAccount) {
        String goalsName = goalnameField.getText();
        double target = Double.parseDouble(targetField.getText());
        double award = Double.parseDouble(awardField.getText());
        String description = Description.getText();

        SavingGoal newGoal = new SavingGoal(goalsName, description, target, award);
        newGoal.setName(goalsName);
        newGoal.setDescription(description);
        newGoal.setReward(award);
        newGoal.setTargetAmount(target);
        childAccount.addSavingGoal(newGoal);
        accountManager.saveAccount(childAccount);



        accountManager.saveAccount(parentAccount);
    }



    private boolean anyTextFieldIsEmpty() {
        return goalnameField.getText().isEmpty() ||
                targetField.getText().isEmpty() ||
                awardField.getText().isEmpty() ||
                Description.getText().isEmpty();
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

    private void returnToManageGoalsFrame(AccountManager accountManager, ParentAccount parentAccount) {
        // 关闭当前的 GoalFrame
        ManageGoalsFrame manageGoalsFrame = new ManageGoalsFrame(accountManager, parentAccount);
        manageGoalsFrame.setVisible(true);
        this.dispose();
    }
}
