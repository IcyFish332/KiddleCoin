package ui;

import core.AccountManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private AccountManager accountManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    public MainFrame(AccountManager accountManager) {

        this.accountManager = accountManager;
        // 设置窗口标题
        setTitle("KiddleCoin Bank");

        // 设置关闭操作：当用户关闭窗口时退出应用程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口大小
        setSize(600, 400);

        // 设置窗口初始位置为屏幕中心
        setLocationRelativeTo(null);

        // 初始化界面组件
        initComponents();
    }

    private void initComponents() {
        // 创建顶部菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // 设置退出操作
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // 创建并添加其他组件，如按钮、标签、文本域等
        // 例如，创建一个标签和一个按钮并添加到主面板中
        JLabel welcomeLabel = new JLabel("Welcome to KiddleCoin Bank", SwingConstants.CENTER);
        JButton loginButton = new JButton("Login");
        // 添加按钮监听器，以执行登录操作（需要实现登录逻辑）
        loginButton.addActionListener(e -> login());

        // 将组件添加到主面板
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(loginButton, BorderLayout.SOUTH);

        // 添加登录表单
        JPanel loginPanel = new JPanel(new GridLayout(2, 2));
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // 修改登录按钮的监听器以实现登录逻辑
        loginButton.addActionListener(e -> login());
    }

    // 登录按钮的响应方法（需要实现具体的登录逻辑）
    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // 验证逻辑（伪代码，需要实现验证方法）
        boolean isValid = accountManager.validateCredentials(username, new String(password));

        if (isValid) {
            // 登录成功，转到主视图
            JOptionPane.showMessageDialog(this, "Login successful.");
            // 转到其他视图的代码...
        } else {
            // 登录失败，显示错误消息
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

        // 清除输入字段，以便下一次登录尝试
        usernameField.setText("");
        passwordField.setText("");
    }
}