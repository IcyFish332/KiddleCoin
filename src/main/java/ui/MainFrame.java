package ui;

import core.Account;
import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import ui.userCenter.KidUserCenterFrame;
import ui.userCenter.ParentUserCenterFrame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private AccountManager accountManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton kidRadioButton;
    private JRadioButton parentRadioButton;

    public MainFrame(AccountManager accountManager) {
        this.accountManager = accountManager;

        // 设置窗口标题
        setTitle("KiddleCoin");

        // 设置关闭操作：当用户关闭窗口时退出应用程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口大小
        setSize(400, 500);

        // 设置窗口初始位置为屏幕中心
        setLocationRelativeTo(null);

        // 初始化界面组件
        initComponents();
    }

    private void initComponents() {
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // 创建登录表单面板
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 创建标题标签
        JLabel titleLabel = new JLabel("KiddleCoin");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180)); // 设置标题颜色为粉色
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        // 创建角色选择标签和单选按钮
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

        // 创建用户名标签和文本框
        JLabel usernameLabel = new JLabel("User Name");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(usernameField, gbc);

        // 创建密码标签和密码框
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(passwordField, gbc);

        // 创建登录按钮
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBackground(new Color(255, 192, 203)); // 设置按钮背景颜色为粉色
        loginButton.addActionListener(e -> login());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // 创建"创建账户"标签
        JButton createAccountButton = new JButton("Create an account");
        createAccountButton.setFont(new Font("Arial", Font.PLAIN, 16));
        createAccountButton.setForeground(new Color(255, 105, 180)); // 设置按钮前景色为粉色
        createAccountButton.setContentAreaFilled(false); // 设置按钮背景为透明
        createAccountButton.setBorderPainted(false); // 去除按钮边框
        createAccountButton.setFocusPainted(false); // 去除按钮焦点框
        createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> openRegistrationFrame());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(createAccountButton, gbc);

        // 将登录表单面板添加到主面板中央
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // 创建并添加背景图片标签
        ImageIcon backgroundImage = new ImageIcon("path/to/image.jpg"); // 替换为实际图片路径
        JLabel backgroundLabel = new JLabel(backgroundImage);
        mainPanel.add(backgroundLabel, BorderLayout.NORTH);

    }

    // 登录按钮的响应方法（需要实现具体的登录逻辑）
    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String accountType = kidRadioButton.isSelected() ? "Kid" : "Parent";

        // 验证逻辑（伪代码，需要实现验证方法）
        boolean isValid = accountManager.validateCredentials(username, new String(password));

        if (isValid) {
            // 登录成功,转到主视图
            JOptionPane.showMessageDialog(this, "Login successful.");
            openUserCenterFrame(accountManager, username);
            dispose();
            // 转到其他视图的代码...
        } else {
            // 登录失败,显示错误消息
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

        // 清除输入字段,以便下一次登录尝试
        usernameField.setText("");
        passwordField.setText("");

    }

    private void openRegistrationFrame() {
        RegistrationFrame registrationFrame = new RegistrationFrame(accountManager);
        registrationFrame.setVisible(true);
    }

    private void openUserCenterFrame(AccountManager accountManager, String username) {
        Account account = accountManager.getAccountByUsername(username);
        if(account.getAccountType().equals("Kid")){
            KidUserCenterFrame kidUserCenterFrame = new KidUserCenterFrame(accountManager, (ChildAccount)account);
            kidUserCenterFrame.setVisible(true);
        }
        else if(account.getAccountType().equals("Parent")){
            ParentUserCenterFrame ParentUserCenterFrame = new ParentUserCenterFrame(accountManager, (ParentAccount)account);
            ParentUserCenterFrame.setVisible(true);
        }

    }

}