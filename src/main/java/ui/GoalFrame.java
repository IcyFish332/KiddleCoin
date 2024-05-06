package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoalFrame extends JFrame {
    private JTextField moneyNameTextField;
    private JTextField amountTextField;
    private JTextField awardTextField;
    private JTextArea descriptionTextArea;

    public GoalFrame() {
        setTitle("Set Goals");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(new JLabel("Kids List"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(new JLabel("Manage Goals"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(new JLabel("Manage Tasks"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(new JLabel("User Center"));
        mainPanel.add(menuPanel, BorderLayout.WEST);

        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.add(menuPanel, BorderLayout.WEST);
        separatorPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        mainPanel.add(separatorPanel, BorderLayout.WEST);

        JLabel taskLabel = new JLabel("Set a Goal");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 25));
        taskLabel.setForeground(new Color(255, 105, 180));
        centerPanel.add(taskLabel, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(new JSeparator(), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // 创建 JLabel 并设置字体
        JLabel informationLabel = new JLabel("Information");
        Font font = informationLabel.getFont().deriveFont(Font.BOLD, 18.0f); // 设置字体为粗体,大小为16
        informationLabel.setFont(font);

        centerPanel.add(informationLabel, gbc);
        gbc.gridx++;
        centerPanel.add(new JLabel(""), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Money Name"), gbc);
        gbc.gridx++;
        moneyNameTextField = new JTextField(20);
        centerPanel.add(moneyNameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Amount"), gbc);

        // 创建一个新的 JPanel 来容纳美元符号和文本框
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 设置左对齐且无间距
        amountPanel.add(new JLabel("$"));
        amountTextField = new JTextField(19);
        amountPanel.add(amountTextField);

        gbc.gridx++;
        gbc.gridwidth = 2; // 让 amountPanel 占据两个单元格
        centerPanel.add(amountPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Award"), gbc);

        // 创建一个新的 JPanel 来容纳美元符号和文本框
        JPanel awardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 设置左对齐且无间距
        awardPanel.add(new JLabel("$"));
        awardTextField = new JTextField(19);
        awardPanel.add(awardTextField);

        gbc.gridx++;
        gbc.gridwidth = 2; // 让 amountPanel 占据两个单元格
        centerPanel.add(awardPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Description"), gbc);
        gbc.gridx++;
        descriptionTextArea = new JTextArea(5, 20);
        centerPanel.add(descriptionTextArea, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(255, 190, 193)); // 设置淡粉色背景
        submitButton.setForeground(new Color(255, 105, 180)); // 设置粉色字体
        submitButton.setOpaque(true); // 确保背景颜色可见
        submitButton.setBorderPainted(true); // 去除边框
        submitButton.addActionListener(new SubmitButtonListener());

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ReturnButtonListener());

        buttonPanel.add(submitButton);
        buttonPanel.add(returnButton);
        centerPanel.add(buttonPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private boolean anyTextFieldIsEmpty() {
        return moneyNameTextField.getText().isEmpty() ||
                amountTextField.getText().isEmpty() ||
                awardTextField.getText().isEmpty() ||
                descriptionTextArea.getText().isEmpty();
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

    private void handleReturn() {
        dispose(); // 关闭当前窗口
        // 如果需要返回到上一个界面,可以在这里添加相关代码
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (anyTextFieldIsEmpty()) {
                showInvalidInfoDialog();
            } else {
                // 执行提交操作
                // ...
            }
        }
    }

    private class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleReturn();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GoalFrame().setVisible(true));
    }
}