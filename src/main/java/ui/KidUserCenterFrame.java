package ui;
import core.AccountManager;
import core.ChildAccount;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class KidUserCenterFrame extends KidPageFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;
    public KidUserCenterFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("User Center"); // 调用父类构造函数,设置标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Lower Panel for user inputs and information label
        JLabel infoLabel = new JLabel("Information");
        lowerPanel.add(infoLabel, BorderLayout.NORTH);

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
        for (String accountID: parentAccountIds) {
            addLabeledComponents(labelPanel, g, "Parent Account", accountID);
        }

        lowerPanel.add(labelPanel, BorderLayout.CENTER); // 将labelPanel添加到lowerPanel的中心位置

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 50, 5, 20);

// Add old password field
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Old Password:"), gbc);
        gbc.gridx = 1;
        oldPasswordField = new JPasswordField(20);
        fieldsPanel.add(oldPasswordField, gbc);

// Add new password field 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        newPasswordField1 = new JPasswordField(20);
        fieldsPanel.add(newPasswordField1, gbc);

// Add new password field 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Confirm New Password:"), gbc);
        gbc.gridx = 1;
        newPasswordField2 = new JPasswordField(20);
        fieldsPanel.add(newPasswordField2, gbc);

// Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> validateAndChangePassword(accountManager, childAccount));
        buttonPanel.add(saveButton);
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        fieldsPanel.add(buttonPanel, gbc);

        lowerPanel.add(fieldsPanel, BorderLayout.CENTER);


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

//         验证旧密码是否正确
        if (!childAccount.getPassword().equals(oldPassword)) {
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
        childAccount.setPassword(newPassword2);
        JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        accountManager.saveAccount(childAccount);
    }

}
