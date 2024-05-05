package ui;

import javax.swing.*;
import java.awt.*;

public class DepositFrame extends JFrame {
    public DepositFrame() {
        setTitle("Make a Deposit");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("The goal's name:"));
        panel.add(new JTextField(15));
        panel.add(new JLabel("Amount:"));
        panel.add(new JTextField(15));
        JButton submitButton = new JButton("Submit");
        JButton returnButton = new JButton("Return");
        panel.add(submitButton);
        panel.add(returnButton);
        returnButton.addActionListener(e -> dispose());
        getContentPane().add(panel);
    }
}
