package ui.encouragement;
import core.AccountManager;
import core.ChildAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.template.BigButton;
import ui.template.KidPageFrame;
import ui.encouragement.ShopFrame;

public class EncouragementFrame extends KidPageFrame {

    public EncouragementFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("Daily Encouragement", accountManager, childAccount);

        // 创建一个新的面板来包含图中间的内容
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 创建“Today's Book”部分
        JLabel bookLabel = new JLabel("Today's Book");
        bookLabel.setFont(new Font("Calibri", Font.PLAIN, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 10, 10); // 设置左边距为30

        mainContentPanel.add(bookLabel, gbc);

        JTextField bookNameField = new JTextField("《Rich Dad, Poor Dad》");
        bookNameField.setFont(new Font("Calibri", Font.PLAIN, 14));
        bookNameField.setHorizontalAlignment(JTextField.CENTER); // 文本居中
        bookNameField.setPreferredSize(new Dimension(200, 60)); // 设置首选尺寸，宽度300，高度40

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // 恢复默认边距

        mainContentPanel.add(bookNameField, gbc);

        // 创建“Today's Courses”部分
        JLabel coursesLabel = new JLabel("Today's Courses");
        coursesLabel.setFont(new Font("Calibri", Font.PLAIN, 18));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 10, 10); // 设置左边距为30

        mainContentPanel.add(coursesLabel, gbc);

        JTextField coursesNameField = new JTextField("\"Fun with Finance\"");
        coursesNameField.setFont(new Font("Calibri", Font.PLAIN, 14));
        coursesNameField.setHorizontalAlignment(JTextField.CENTER); // 文本居中
        coursesNameField.setPreferredSize(new Dimension(200, 60)); // 设置首选尺寸，宽度300，高度40

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // 恢复默认边距

        mainContentPanel.add(coursesNameField, gbc);

        // 创建“My Shop”按钮

        // Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton shopButton = new BigButton("My Shop");

        buttonPanel.add(shopButton);

        // 添加事件监听器到“My Shop”按钮
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShopFrame(accountManager, childAccount).setVisible(true);
                dispose(); // 关闭当前窗口
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(shopButton, gbc);

        // 创建“Give myself a thumbs-up”部分
        JPanel thumbsUpPanel = new JPanel();
        thumbsUpPanel.setBackground(Color.WHITE);
        thumbsUpPanel.setLayout(new BoxLayout(thumbsUpPanel, BoxLayout.X_AXIS));

        JLabel thumbsUpLabel = new JLabel("Give myself a thumbs-up !");
        thumbsUpLabel.setFont(new Font("Calibri", Font.PLAIN, 18));

        thumbsUpLabel.setForeground(new Color(0xF868B0)); // 设置字体颜色为粉色
        thumbsUpPanel.add(Box.createHorizontalGlue()); // 添加水平填充，使得Label居中
        thumbsUpPanel.add(thumbsUpLabel);

        thumbsUpPanel.add(Box.createHorizontalStrut(20)); // 添加水平间距

        // 调整thumbs_up.png的大小
        ImageIcon icon = new ImageIcon("src/main/java/ui/encouragement/thumb.jpg");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 调整图标大小
        icon = new ImageIcon(newImage);

        // 创建标签显示图标
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // 设置上边距为20像素、

        iconLabel.setOpaque(true); // 设置标签为不透明
        iconLabel.setBackground(Color.WHITE); // 设置背景颜色为白色

        thumbsUpPanel.add(iconLabel);
        thumbsUpPanel.add(Box.createHorizontalGlue()); // 添加水平填充，使得Label居中

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(thumbsUpPanel, gbc);

        // 将mainContentPanel添加到lowerPanel中
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(mainContentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

}
