package ui;

import core.Account;
import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import ui.kid.KidUserCenterFrame;
import ui.parent.ParentUserCenterFrame;

import javax.swing.*;
import java.awt.*;

/**
 * The main frame of the application where users can log in or create a new account.
 * This frame provides the UI for entering login credentials and selecting account type.
 *
 * @author Siyuan Lu
 */
public class MainFrame extends JFrame {
    private AccountManager accountManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton kidRadioButton;
    private JRadioButton parentRadioButton;

    /**
     * Constructs a new MainFrame with the specified AccountManager.
     *
     * @param accountManager the account manager used to handle account operations
     */
    public MainFrame(AccountManager accountManager) {
        this.accountManager = accountManager;

        // Set window title
        setTitle("KiddleCoin");

        // Set close operation: exit application when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size
        setSize(400, 500);

        // Set window initial location to center of the screen
        setLocationRelativeTo(null);

        // Initialize UI components
        initComponents();
    }

    /**
     * Initializes the components of the main frame.
     */
    private void initComponents() {
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Create login form panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create title label
        JLabel titleLabel = new JLabel("KiddleCoin");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180)); // Set title color to pink
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        // Create role selection label and radio buttons
        JLabel roleLabel = new JLabel("Account Type:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(roleLabel, gbc);

        kidRadioButton = new JRadioButton("Kid");
        parentRadioButton = new JRadioButton("Parent");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(kidRadioButton);
        buttonGroup.add(parentRadioButton);

        JPanel radioButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioButtonPanel.setBackground(Color.WHITE);
        radioButtonPanel.add(kidRadioButton);
        radioButtonPanel.add(parentRadioButton);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(radioButtonPanel, gbc);

        // Create username label and text field
        JLabel usernameLabel = new JLabel("User Name");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(usernameField, gbc);

        // Create password label and password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(passwordField, gbc);

        // Create login button
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBackground(new Color(255, 192, 203)); // Set button background color to pink
        loginButton.addActionListener(e -> login());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Create "Create an account" button
        JButton createAccountButton = new JButton("Create an account");
        createAccountButton.setFont(new Font("Arial", Font.PLAIN, 16));
        createAccountButton.setForeground(new Color(255, 105, 180)); // Set button foreground color to pink
        createAccountButton.setContentAreaFilled(false); // Set button background to transparent
        createAccountButton.setBorderPainted(false); // Remove button border
        createAccountButton.setFocusPainted(false); // Remove button focus border
        createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> openRegistrationFrame());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(createAccountButton, gbc);

        // Add login form panel to the center of the main panel
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Create and add background image label
        ImageIcon backgroundImage = new ImageIcon("path/to/image.jpg"); // Replace with actual image path
        JLabel backgroundLabel = new JLabel(backgroundImage);
        mainPanel.add(backgroundLabel, BorderLayout.NORTH);
    }

    /**
     * Handles the login button action.
     * Validates the user credentials and opens the appropriate user center frame upon successful login.
     */
    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String accountType = kidRadioButton.isSelected() ? "Kid" : "Parent";

        // Validate credentials (pseudo-code, needs actual implementation)
        boolean isValid = accountManager.validateCredentials(username, new String(password));

        if (isValid) {
            // Login successful, proceed to user center view
            JOptionPane.showMessageDialog(this, "Login successful.");
            openUserCenterFrame(accountManager, username);
            dispose();
            // Code to switch to other views...
        } else {
            // Login failed, show error message
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

        // Clear input fields for the next login attempt
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Opens the registration frame to create a new account.
     */
    private void openRegistrationFrame() {
        RegistrationFrame registrationFrame = new RegistrationFrame(accountManager);
        registrationFrame.setVisible(true);
    }

    /**
     * Opens the appropriate user center frame based on the account type.
     *
     * @param accountManager the account manager used to handle account operations
     * @param username the username of the logged-in account
     */
    private void openUserCenterFrame(AccountManager accountManager, String username) {
        Account account = accountManager.getAccountByUsername(username);
        if (account.getAccountType().equals("Kid")) {
            KidUserCenterFrame kidUserCenterFrame = new KidUserCenterFrame(accountManager, (ChildAccount) account);
            kidUserCenterFrame.setVisible(true);
        } else if (account.getAccountType().equals("Parent")) {
            ParentUserCenterFrame parentUserCenterFrame = new ParentUserCenterFrame(accountManager, (ParentAccount) account);
            parentUserCenterFrame.setVisible(true);
        }
    }
}
