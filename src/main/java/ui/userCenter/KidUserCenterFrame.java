package ui.userCenter;
import core.AccountManager;
import core.ChildAccount;
import ui.template.BigButton;
import ui.template.KidPageFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class KidUserCenterFrame extends KidPageFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;

    public KidUserCenterFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("User Center", accountManager, childAccount); // 调用父类构造函数,设置标题
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // *** Information Section ***
        // Lower Panel for user inputs and information label
        JLabel infoHeading = new JLabel("Information");
        infoHeading.setFont(new Font("Calibri", Font.BOLD, 18));
        infoHeading.setForeground(new Color(0xF868B0)); // Red color as shown in the sketch
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        lowerPanel.add(infoHeading, gbc);

        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 设置边距

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.NORTHWEST; // 设置组件在单元格中的位置
        g.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距
        g.gridx = 0; // 初始x坐标
        g.gridy = 0; // 初始y坐标

        // 添加标签
        addLabeledComponents(labelPanel, g, "User Name:", childAccount.getUsername());
        addLabeledComponents(labelPanel, g, "ID:", childAccount.getAccountId());
        Set<String> parentAccountIds = childAccount.getParentAccountIds();
        int count = 1;
        for (String accountID: parentAccountIds) {
            addLabeledComponents(labelPanel, g, "Parent Account " + count, accountID);
            count++;
        }

        // 将labelPanel添加到lowerPanel的中部
        gbc.gridy = 1;
        lowerPanel.add(labelPanel, gbc);
        //lowerPanel.add(labelPanel, BorderLayout.CENTER); // 将labelPanel添加到lowerPanel的中心位置

        // *** Change Password Section ***
        // Set up heading for Change Password section
        JLabel passwordHeading = new JLabel("Change Password");
        passwordHeading.setFont(new Font("Calibri", Font.BOLD, 16));
        passwordHeading.setForeground(new Color(0xF868B0)); // Red color for this heading too
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

    private void validateAndChangePassword(AccountManager accountManager, ChildAccount childAccount) {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword1 = new String(newPasswordField1.getPassword());
        String newPassword2 = new String(newPasswordField2.getPassword());

        if(newPassword1.equals("")){
            JOptionPane.showMessageDialog(this, "Password can not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

//         验证旧密码是否正确
        if (!childAccount.getPassword().equals(oldPassword)) {
            JOptionPane.showMessageDialog(this, "Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 验证两次新密码输入是否一致
        if (!newPassword1.equals(newPassword2)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 执行密码修改操作
        childAccount.setPassword(newPassword2);
        JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        accountManager.saveAccount(childAccount);
    }

}
