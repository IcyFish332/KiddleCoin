package ui;

import javax.swing.*;
import java.awt.*;

public class SaveGoalsFrame extends JFrame {
    public SaveGoalsFrame() {
        setTitle("Save Money into My Goals");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
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
