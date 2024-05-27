package ui.template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.AccountManager;
import core.ParentAccount;
import ui.parent.KidsListManagementFrame;
import ui.parent.ManageGoalsFrame;
import ui.parent.ManageTasksFrame;
import ui.parent.ParentUserCenterFrame;

/**
 * This class represents a template frame for parent pages.
 *
 * It provides a UI template with a sidebar for navigation and content panels for displaying information.
 *
 * @Author: Ruihang Zhang
 */
public class ParentPageFrame extends JFrame {
    protected JLabel titleLabel;
    protected JPanel sidebarPanel;
    protected JPanel contentPanel;
    protected JPanel upperPanel;
    protected JPanel lowerPanel;

    /**
     * Constructs a ParentPageFrame.
     *
     * @param title
     *        the title of the frame
     *
     * @param accountManager
     *        the account manager to manage parent accounts
     *
     * @param parentAccount
     *        the parent account associated with this frame
     */
    public ParentPageFrame(String title, AccountManager accountManager, ParentAccount parentAccount) {

        setTitle("KiddleCoin");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel using BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the sidebar panel
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 5), // Set margins
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0xFFE0E4)) // Add bottom gray border
        ));

        // Create a middle panel to contain the icon and button panel
        JPanel middlePanel = new JPanel(new BorderLayout(0, 10)); // Vertical spacing set to 10 pixels
        middlePanel.setBackground(Color.WHITE);

        // Load the icon image
        ImageIcon icon = new ImageIcon("src/main/java/ui/template/icon_Parent.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Resize icon
        icon = new ImageIcon(newImage);

        // Create a label to display the icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // Set top margin to 15 pixels
        iconLabel.setOpaque(true); // Set label to opaque
        iconLabel.setBackground(Color.WHITE); // Set background color to white

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Add sidebar buttons
        SidebarButton button1 = new SidebarButton("Kid List");
        buttonPanel.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KidsListManagementFrame kidsListManagementFrame = new KidsListManagementFrame(accountManager, parentAccount);
                kidsListManagementFrame.setVisible(true);
                dispose();
            }
        });

        SidebarButton button2 = new SidebarButton("Manage Goals");
        buttonPanel.add(button2);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageGoalsFrame manageGoalsFrame = new ManageGoalsFrame(accountManager, parentAccount);
                manageGoalsFrame.setVisible(true);
                dispose();
            }
        });

        SidebarButton button3 = new SidebarButton("Manage Tasks");
        buttonPanel.add(button3);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageTasksFrame manageTasksFrame = new ManageTasksFrame(accountManager, parentAccount);
                manageTasksFrame.setVisible(true);
                dispose();
            }
        });

        SidebarButton button4 = new SidebarButton("User Center");
        buttonPanel.add(button4);
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParentUserCenterFrame parentUserCenterFrame = new ParentUserCenterFrame(accountManager, parentAccount);
                parentUserCenterFrame.setVisible(true);
                dispose();
            }
        });

        middlePanel.add(iconLabel, BorderLayout.NORTH);
        middlePanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the button panel to the sidebar panel's center
        sidebarPanel.add(middlePanel, BorderLayout.CENTER);

        // Create the right content panel using BorderLayout
        contentPanel = new JPanel(new BorderLayout());

        // Create the upper panel
        upperPanel = new JPanel(new BorderLayout(10, 10));
        upperPanel.setPreferredSize(new Dimension(500, 130)); // Increase width and height values
        upperPanel.setBackground(Color.WHITE);
        upperPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40), // Set margins
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY) // Add bottom gray border
        ));

        // Create the title
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0xF868B0)); // Set font color to pink

        upperPanel.add(titleLabel);
        contentPanel.add(upperPanel, BorderLayout.NORTH);

        // Create the lower info panel
        lowerPanel = new JPanel();
        lowerPanel.setBackground(Color.WHITE);

        // Add the lower info panel and button panel to the contentPanel
        contentPanel.add(lowerPanel, BorderLayout.CENTER);

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * This class represents a button in the sidebar.
     *
     * It provides a custom styled button for the sidebar.
     */
    private static class SidebarButton extends JButton {
        /**
         * Constructs a SidebarButton.
         *
         * @param text
         *        the text to display on the button
         */
        public SidebarButton(String text) {
            super(text);

            // Set button size
            Dimension maxBtnSize = new Dimension(200, 30);
            setMaximumSize(maxBtnSize);
            setPreferredSize(maxBtnSize);

            // Set font
            Font buttonFont = new Font("Calibri", Font.PLAIN, 14);
            setFont(buttonFont);

            // Set button style
            setFocusPainted(false);
            setBorderPainted(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setOpaque(true);
            setBackground(Color.WHITE);

            // Set margins
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
    }
}
