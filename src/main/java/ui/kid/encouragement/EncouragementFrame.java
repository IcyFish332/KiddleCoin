package ui.kid.encouragement;

import core.AccountManager;
import core.ChildAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.template.BigButton;
import ui.template.KidPageFrame;

/**
 * This class represents a frame for daily encouragement for kids.
 * It extends the KidPageFrame and provides a UI for showing today's book,
 * today's courses, and a "My Shop" button.
 *
 * @author Ruihang Zhang
 */
public class EncouragementFrame extends KidPageFrame {

    /**
     * Constructs an EncouragementFrame.
     *
     * @param accountManager the account manager to manage child accounts
     * @param childAccount   the child account associated with this frame
     */
    public EncouragementFrame(AccountManager accountManager, ChildAccount childAccount) {
        super("Daily Encouragement", accountManager, childAccount);

        // Create a new panel to contain the main content
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create "Today's Book" section
        JLabel bookLabel = new JLabel("Today's Book");
        bookLabel.setFont(new Font("Calibri", Font.PLAIN, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 10, 10); // Set left margin to 30

        mainContentPanel.add(bookLabel, gbc);

        JTextField bookNameField = new JTextField("《Rich Dad, Poor Dad》");
        bookNameField.setFont(new Font("Calibri", Font.PLAIN, 14));
        bookNameField.setHorizontalAlignment(JTextField.CENTER); // Center text
        bookNameField.setPreferredSize(new Dimension(200, 60)); // Set preferred size

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // Restore default margins

        mainContentPanel.add(bookNameField, gbc);

        // Create "Today's Courses" section
        JLabel coursesLabel = new JLabel("Today's Courses");
        coursesLabel.setFont(new Font("Calibri", Font.PLAIN, 18));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 10, 10); // Set left margin to 30

        mainContentPanel.add(coursesLabel, gbc);

        JTextField coursesNameField = new JTextField("\"Fun with Finance\"");
        coursesNameField.setFont(new Font("Calibri", Font.PLAIN, 14));
        coursesNameField.setHorizontalAlignment(JTextField.CENTER); // Center text
        coursesNameField.setPreferredSize(new Dimension(200, 60)); // Set preferred size

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // Restore default margins

        mainContentPanel.add(coursesNameField, gbc);

        // Create "My Shop" button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        BigButton shopButton = new BigButton("My Shop");

        buttonPanel.add(shopButton);

        // Add event listener to "My Shop" button
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShopFrame(accountManager, childAccount).setVisible(true);
                dispose(); // Close the current window
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(shopButton, gbc);

        // Create "Give myself a thumbs-up" section
        JPanel thumbsUpPanel = new JPanel();
        thumbsUpPanel.setBackground(Color.WHITE);
        thumbsUpPanel.setLayout(new BoxLayout(thumbsUpPanel, BoxLayout.X_AXIS));

        JLabel thumbsUpLabel = new JLabel("Give myself a thumbs-up !");
        thumbsUpLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        thumbsUpLabel.setForeground(new Color(0xF868B0)); // Set font color to pink

        thumbsUpPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the label
        thumbsUpPanel.add(thumbsUpLabel);
        thumbsUpPanel.add(Box.createHorizontalStrut(20)); // Add horizontal spacing

        // Resize thumbs_up.png
        ImageIcon icon = new ImageIcon("src/main/java/ui/encouragement/thumb.jpg");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize icon
        icon = new ImageIcon(newImage);

        // Create label to display the icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // Set top margin to 15 pixels
        iconLabel.setOpaque(true); // Set label to opaque
        iconLabel.setBackground(Color.WHITE); // Set background color to white

        thumbsUpPanel.add(iconLabel);
        thumbsUpPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the label

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        mainContentPanel.add(thumbsUpPanel, gbc);

        // Add mainContentPanel to lowerPanel
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(mainContentPanel, BorderLayout.CENTER);

        revalidate();

        repaint();
    }

}
