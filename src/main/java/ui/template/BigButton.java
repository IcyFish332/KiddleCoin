package ui.template;

import java.awt.*;
import javax.swing.*;

public class BigButton extends JButton {
    private Color backgroundColor;
    private Color foregroundColor;
    private int arcWidth = 20;
    private int arcHeight = 20;

    public BigButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
//        setForeground(Color.WHITE);
//        setBackground(new Color(0xFFB6C1));
        setFont(new Font("Ariel", Font.BOLD, 15));

        if(text.equals("Submit")){
            setBackgroundColor(new Color(0xFFEDF0));
            setForegroundColor(new Color(0xF868B0));
        }
        else if(text.equals("Return")){
            setBackgroundColor(Color.gray);
            setForegroundColor(Color.BLACK);
        }
        else {
            // 默认颜色设置（可以根据需要调整）
            setBackgroundColor(new Color(0xFFEDF0));
            setForegroundColor(new Color(0xF868B0));
        }
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public void setForegroundColor(Color color) {
        foregroundColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

//        // 绘制阴影
//        g2d.setColor(new Color(108, 106, 106, 50)); // 半透明黑色
//        g2d.fillRoundRect(50, 50, width, height, arcWidth, arcHeight); // 右下角偏移5像素


        // 绘制背景
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

        // 绘制文本
        g2d.setColor(foregroundColor);
        FontMetrics metrics = g2d.getFontMetrics(getFont());
        int textX = (width - metrics.stringWidth(getText())) / 2;
        int textY = (height - metrics.getHeight()) / 2 + metrics.getAscent();
        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }


}

