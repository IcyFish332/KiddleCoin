package ui.template;

import java.awt.*;
import javax.swing.*;

/**
 * This class represents a custom JButton with rounded corners and custom colors.
 *
 * @Author: Ruihang Zhang
 */
public class BigButton extends JButton {
    private Color backgroundColor;
    private Color foregroundColor;
    private int arcWidth = 20;
    private int arcHeight = 20;

    /**
     * Constructs a BigButton with the specified text.
     *
     * @param text
     *        the text to display on the button
     */
    public BigButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Ariel", Font.BOLD, 15));

        if (text.equals("Submit")) {
            setBackgroundColor(new Color(0xFFEDF0));
            setForegroundColor(new Color(0xF868B0));
        } else if (text.equals("Return")) {
            setBackgroundColor(new Color(0xDCDCDC));
            setForegroundColor(Color.BLACK);
        } else {
            // Default color settings
            setBackgroundColor(new Color(0xFFEDF0));
            setForegroundColor(new Color(0xF868B0));
        }
    }

    /**
     * Sets the background color of the button.
     *
     * @param color
     *        the background color to set
     */
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    /**
     * Sets the foreground color of the button.
     *
     * @param color
     *        the foreground color to set
     */
    public void setForegroundColor(Color color) {
        foregroundColor = color;
    }

    /**
     * Paints the button with custom rendering.
     *
     * @param g
     *        the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

        // Paint text
        g2d.setColor(foregroundColor);
        FontMetrics metrics = g2d.getFontMetrics(getFont());
        int textX = (width - metrics.stringWidth(getText())) / 2;
        int textY = (height - metrics.getHeight()) / 2 + metrics.getAscent();
        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }
}
