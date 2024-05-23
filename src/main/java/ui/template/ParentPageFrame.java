package ui.template;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.AccountManager;
import core.ParentAccount;
import ui.KidDetailsFrame;
import ui.KidsListManagementFrame;
import ui.ManageGoalsFrame;
import ui.ManageTasksFrame;
import ui.userCenter.ParentUserCenterFrame;

public class ParentPageFrame extends JFrame {
    protected JLabel titleLabel;
    protected JPanel sidebarPanel;
    protected JPanel contentPanel;
    protected JPanel upperPanel;
    protected JPanel lowerPanel;

    public ParentPageFrame(String title, AccountManager accountManager, ParentAccount parentAccount) {

        setTitle("KiddleCoin");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建主面板,使用BorderLayout布局
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建侧边栏面板
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 5), // 设置边距
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0xFFE0E4)) // 添加底部灰色边框
        ));

        // 创建一个中间面板来容纳图标和按钮面板
        JPanel middlePanel = new JPanel(new BorderLayout(0, 10)); // 垂直间距设为10像素
        middlePanel.setBackground(Color.WHITE);

        // 加载图标图像
        ImageIcon icon = new ImageIcon("src/main/java/ui/template/icon_Parent.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // 调整图标大小
        icon = new ImageIcon(newImage);

        // 创建标签显示图标
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // 设置上边距为20像素、
        iconLabel.setOpaque(true); // 设置标签为不透明
        iconLabel.setBackground(Color.WHITE); // 设置背景颜色为白色

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // 添加侧边栏按钮
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
                ManageTasksFrame parentUserCenterFrame = new ManageTasksFrame(accountManager, parentAccount,"name","1000",null);
                parentUserCenterFrame.setVisible(true);
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

        // 将按钮面板添加到侧边栏面板的中心
        sidebarPanel.add(middlePanel, BorderLayout.CENTER);

        // 创建右侧内容面板,使用BorderLayout布局
        contentPanel = new JPanel(new BorderLayout());

        // 创建上部面板
        upperPanel = new JPanel(new BorderLayout(10, 10));
        upperPanel.setPreferredSize(new Dimension(500, 130)); // 加大宽高值
        upperPanel.setBackground(Color.WHITE);
        upperPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40), // 设置边距
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY) // 添加底部灰色边框
        ));
         //Add spacing before the title

        //创建标题
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0xF868B0)); // 设置字体颜色为粉色

        upperPanel.add(titleLabel);
        contentPanel.add(upperPanel, BorderLayout.NORTH);

        // 创建下部信息面板
        lowerPanel = new JPanel();
        lowerPanel.setBackground(Color.WHITE);

        // 添加任务信息面板和按钮面板到contentPanel
        contentPanel.add(lowerPanel, BorderLayout.CENTER);

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private static class SidebarButton extends JButton {
        public SidebarButton(String text) {
            super(text);

            // 设置按钮大小
            Dimension maxBtnSize = new Dimension(150, 30);
            setMaximumSize(maxBtnSize);
            setPreferredSize(maxBtnSize);

            // 设置字体
            Font buttonFont = new Font("Calibri", Font.PLAIN, 14);
            setFont(buttonFont);

            // 设置按钮样式
            setFocusPainted(false);
            setBorderPainted(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setOpaque(true);
            setBackground(Color.WHITE);

            // 设置边距
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
    }

}