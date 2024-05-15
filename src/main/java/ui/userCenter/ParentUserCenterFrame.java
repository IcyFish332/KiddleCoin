package ui.userCenter;
import core.AccountManager;
import core.ParentAccount;
import ui.template.BigButton;
import ui.template.ParentPageFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ParentUserCenterFrame extends ParentPageFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;

    public ParentUserCenterFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("User Center", accountManager, parentAccount);
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

        // 添加标签
        // Add the information labels to the panel
        addLabeledComponents(labelPanel, g, "User Name:", parentAccount.getUsername());
        addLabeledComponents(labelPanel, g, "ID:", parentAccount.getAccountId());
        Set<String> kidsAccountIds = parentAccount.getChildAccountIds();
        int count = 1;
        for (String accountID: kidsAccountIds) {
            addLabeledComponents(labelPanel, g, "Kids Account" + count, accountID);
            count++;
        }

        // 将labelPanel添加到lowerPanel的中部
        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);

        // *** Change Password Section ***
        // Set up heading for Change Password section
        JLabel passwordHeading = new JLabel("Change Password");
        passwordHeading.setFont(new Font("Calibri", Font.BOLD, 16));
        passwordHeading.setForeground(new Color(0xF868B0)); // Red color for this heading too
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 2;
        lowerPanel.add(passwordHeading, gbc);


        // Set up panel for Password labels and fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);

        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST; // 设置组件在单元格中的位置
        gb.insets = new Insets(5, 5, 5, 5);

        // Add old password field
        gb.gridx = 0;
        gb.gridy = 0;
        JLabel oldPassword = new JLabel("Old Password:");

        //oldPassword.setHorizontalAlignment(SwingConstants.LEFT);
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

    private void addLabeledComponents(JPanel panel, GridBagConstraints gbc, String labelText, String valueText) {
        gbc.gridx = 0; // 标签位于第一列
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.RIGHT); // 标签右对齐
        panel.add(label, gbc);

        gbc.gridx = 1; // 值位于第二列
        JLabel value = new JLabel(valueText);
        value.setHorizontalAlignment(SwingConstants.LEFT); // 值左对齐
        panel.add(value, gbc);

        gbc.gridy++; // 移动到下一行
    }

    private void validateAndChangePassword(AccountManager accountManager, ParentAccount parentAccount) {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword1 = new String(newPasswordField1.getPassword());
        String newPassword2 = new String(newPasswordField2.getPassword());

//         验证旧密码是否正确
        if (!parentAccount.getPassword().equals(oldPassword)) {
            JOptionPane.showMessageDialog(this, "Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(newPassword1.equals("")){
            JOptionPane.showMessageDialog(this, "Password can not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 验证两次新密码输入是否一致
        if (!newPassword1.equals(newPassword2)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 执行密码修改操作
        parentAccount.setPassword(newPassword2);
        JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        accountManager.saveAccount(parentAccount);
    }


}
