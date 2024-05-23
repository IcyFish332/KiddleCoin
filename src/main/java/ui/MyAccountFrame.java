package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


    }







        JButton depositButton = new JButton("I want to deposit money");
        JButton withdrawButton = new JButton("I want to withdraw money");




        JScrollPane goalsScrollPane = new JScrollPane(goalsTable);

        seeMoreGoalsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        seeMoreGoalsLabel.setForeground(new Color(255, 105, 180));
        seeMoreGoalsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreGoalsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open MyGoalsFrame
                myGoalsFrame.setVisible(true);
            }
        });



        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);

        seeMoreTasksLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        seeMoreTasksLabel.setForeground(new Color(255, 105, 180));
        seeMoreTasksLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreTasksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open MyTasksFrame
                myTasksFrame.setVisible(true);
            }
        });

    }
}
