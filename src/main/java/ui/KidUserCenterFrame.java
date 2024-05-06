package ui;
import javax.swing.*;
import java.awt.*;

public class KidUserCenterFrame extends JFrame {
    public KidUserCenterFrame() {
        setTitle("User Center");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Upper Panel for user information display
        JPanel upperPanel = new JPanel(new GridLayout(3, 2, 20, 5));
        upperPanel.add(new JLabel("User Name:"));
        upperPanel.add(new JLabel("dsfuis"));
        upperPanel.add(new JLabel("ID:"));
        upperPanel.add(new JLabel("2489372598"));
        upperPanel.add(new JLabel("Parent Account"));
        upperPanel.add(new JLabel(""));


        // Lower Panel for user inputs and information label
        JPanel lowerPanel = new JPanel(new BorderLayout(10, 10));
        JLabel infoLabel = new JLabel("Information");
        lowerPanel.add(infoLabel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 50, 5, 20);

        // Nick Name
        gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Nick Name:"), gbc);
        JTextField nickNameField = new JTextField("Your name", 10);
        nickNameField.setBackground(new Color(255, 237, 240));
        fieldsPanel.add(nickNameField, gbc);

        // Phone Number
        gbc.gridy = 1;
        fieldsPanel.add(new JLabel("Phone Number:"), gbc);
        JTextField phoneField = new JTextField("Your phone number", 10);
        phoneField.setBackground(new Color(255, 237, 240));
        fieldsPanel.add(phoneField, gbc);

        // Bank Introduction
        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Bank Introduction:"), gbc);
        JTextField bankField = new JTextField("A brief introduction for your bank", 10);
        bankField.setBackground(new Color(255, 237, 240));
        fieldsPanel.add(bankField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Save"));
        buttonPanel.add(new JButton("Reset"));
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        fieldsPanel.add(buttonPanel, gbc);

        lowerPanel.add(fieldsPanel, BorderLayout.CENTER);

        // Adding panels to the frame
        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KidUserCenterFrame::new);
    }
}
