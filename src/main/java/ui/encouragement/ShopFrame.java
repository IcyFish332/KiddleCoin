package ui.encouragement;
import core.AccountManager;
import core.ChildAccount;

import javax.swing.*;
import java.awt.*;
import ui.template.KidPageFrame;

public class ShopFrame extends KidPageFrame {

    public ShopFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("My Shop", accountManager, childAccount);

        // 创建一个新的面板来包含图中间的内容
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 设置组件之间的间距

        // 创建标题和描述文本
        JLabel descriptionLabel = new JLabel("I want to buy ...");
        descriptionLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainContentPanel.add(descriptionLabel, gbc);

        // 调整piggy_bank图标大小并设置位置
        ImageIcon piggyBankIcon = new ImageIcon("src/main/java/ui/encouragement/piggyBox.png");
        Image piggyBankImage = piggyBankIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); // 调整图标大小
        piggyBankIcon = new ImageIcon(piggyBankImage);
        JLabel piggyBankLabel = new JLabel(piggyBankIcon);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // 调整锚点，使其靠左下
        mainContentPanel.add(piggyBankLabel, gbc);

        // 调整candy图标大小并设置位置
        ImageIcon candyIcon = new ImageIcon("src/main/java/ui/encouragement/candy.jpg");
        Image candyImage = candyIcon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH); // 调整图标大小
        candyIcon = new ImageIcon(candyImage);
        JLabel candyLabel = new JLabel(candyIcon);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainContentPanel.add(candyLabel, gbc);

        // 调整bear图标大小并设置位置
        ImageIcon bearIcon = new ImageIcon("src/main/java/ui/encouragement/bear.jpg");
        Image bearImage = bearIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 调整图标大小
        bearIcon = new ImageIcon(bearImage);
        JLabel bearLabel = new JLabel(bearIcon);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainContentPanel.add(bearLabel, gbc);

        // 调整car图标大小并设置位置
        ImageIcon carIcon = new ImageIcon("src/main/java/ui/encouragement/car.png");
        Image carImage = carIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH); // 调整图标大小
        carIcon = new ImageIcon(carImage);
        JLabel carLabel = new JLabel(carIcon);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainContentPanel.add(carLabel, gbc);

        // 将mainContentPanel添加到lowerPanel中
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(mainContentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }


}
