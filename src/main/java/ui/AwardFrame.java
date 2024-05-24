package ui;
import core.AccountManager;
import core.ParentAccount;
import ui.template.BigButton;
import ui.template.ParentPageFrame;
import ui.ManageTasksFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

public class AwardFrame extends ParentPageFrame {
    private  AccountManager accountManager;
    private  ParentAccount parentAccount;
    private DefaultTableModel goalsModel;
    private JTextField AwardnameField;
    private JTextField AmountField;


    public AwardFrame(AccountManager accountManager, ParentAccount parentAccount) {
        super("Give an Award", accountManager, parentAccount);
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
        JLabel AwardName = new JLabel("Award Name");

        //oldPassword.setHorizontalAlignment(SwingConstants.LEFT);
        fieldsPanel.add(AwardName, gb);
        gb.gridx = 1;
        AwardnameField = new JTextField(20);
        fieldsPanel.add(AwardnameField, gb);

        // Add new password field 1
        gb.gridx = 0;
        gb.gridy = 1;
        fieldsPanel.add(new JLabel("Award   $"), gb);
        gb.gridx = 1;
        AmountField = new JTextField(20);
        fieldsPanel.add(AmountField, gb);
        lowerPanel.add(fieldsPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton submitButton = new BigButton("Submit");
        BigButton returnButton = new BigButton("Return");

        submitButton.addActionListener(e -> {
            // Validate input fields (pseudo-code)
            if (anyTextFieldIsEmpty()) {
                showInvalidInfoDialog();

            } else {
                //update code
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




        lowerPanel.add(buttonPanel, gbc);


        setVisible(true);
    }




    // Method to update information



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

    private boolean anyTextFieldIsEmpty() {
        return AwardnameField.getText().isEmpty() ||
                AmountField.getText().isEmpty();

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

    private void returnToManageGoalsFrame() {
        // 关闭当前的 GoalFrame

        dispose();
    }

//public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new AwardFrame(accountManager, parentAccount).setVisible(true));
    // }

}