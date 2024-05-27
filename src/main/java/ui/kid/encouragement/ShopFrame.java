package ui.kid.encouragement;

import core.AccountManager;
import core.ChildAccount;

import javax.swing.*;
import java.awt.*;
import ui.template.KidPageFrame;

/**
 * This class represents a frame for the shop where kids can buy items.
 *
 * It extends the KidPageFrame and provides a UI for displaying items that kids can purchase.
 *
 * @Author: Ruihang Zhang
 */
public class ShopFrame extends KidPageFrame {

    /**
     * Constructs a ShopFrame.
     *
     * @param accountManager
     *        the account manager to manage child accounts
     *
     * @param childAccount
     *        the child account associated with this frame
     */
    public ShopFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("My Shop", accountManager, childAccount);

        // Create a new panel to contain the main content
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set component spacing

        // Create title and description text
        JLabel descriptionLabel = new JLabel("I want to buy ...");
        descriptionLabel.setFont(new Font("Calibri", Font.PLAIN, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;

        mainContentPanel.add(descriptionLabel, gbc);

        // Resize piggy_bank icon and set position
        ImageIcon piggyBankIcon = new ImageIcon("src/main/java/ui/encouragement/piggyBox.png");
        Image piggyBankImage = piggyBankIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Resize icon
        piggyBankIcon = new ImageIcon(piggyBankImage);

        JLabel piggyBankLabel = new JLabel(piggyBankIcon);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Adjust anchor to bottom left

        mainContentPanel.add(piggyBankLabel, gbc);

        // Resize candy icon and set position
        ImageIcon candyIcon = new ImageIcon("src/main/java/ui/encouragement/candy.jpg");
        Image candyImage = candyIcon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH); // Resize icon
        candyIcon = new ImageIcon(candyImage);

        JLabel candyLabel = new JLabel(candyIcon);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(candyLabel, gbc);

        // Resize bear icon and set position
        ImageIcon bearIcon = new ImageIcon("src/main/java/ui/encouragement/bear.jpg");
        Image bearImage = bearIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize icon
        bearIcon = new ImageIcon(bearImage);

        JLabel bearLabel = new JLabel(bearIcon);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(bearLabel, gbc);

        // Resize car icon and set position
        ImageIcon carIcon = new ImageIcon("src/main/java/ui/encouragement/car.png");
        Image carImage = carIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH); // Resize icon
        carIcon = new ImageIcon(carImage);

        JLabel carLabel = new JLabel(carIcon);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(carLabel, gbc);

        // Add mainContentPanel to lowerPanel
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(mainContentPanel, BorderLayout.CENTER);

        revalidate();

        repaint();
    }
}
