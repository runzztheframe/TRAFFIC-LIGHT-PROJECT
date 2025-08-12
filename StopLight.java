import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopLight extends JPanel {
    private enum Light {GREEN, YELLOW, RED}
    private Light current = Light.GREEN;

    public StopLight() {
        // repaint every time the timer ticks
        int delayMs = 10000; // 10 seconds per color
        Timer timer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (current) {
                    case GREEN: current = Light.YELLOW; break;
                    case YELLOW: current = Light.RED; break;
                    case RED: current = Light.GREEN; break;
                }
                repaint();
            }
        });
        timer.setInitialDelay(0); // start immediately
        timer.start();

        setPreferredSize(new Dimension(200, 480));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int circleDiameter = Math.min(width, height / 4) - 20;
        int centerX = (width - circleDiameter) / 2;
        int gap = 20;

        // positions for three lights
        int yGreen = 20;
        int yYellow = yGreen + circleDiameter + gap;
        int yRed = yYellow + circleDiameter + gap;

        // Draw housing
        g2.setColor(new Color(30, 30, 30));
        int housingWidth = circleDiameter + 40;
        int housingHeight = circleDiameter * 3 + gap * 2 + 40;
        int hx = (width - housingWidth) / 2;
        int hy = 10;
        g2.fillRoundRect(hx, hy, housingWidth, housingHeight, 30, 30);

        // Draw each light (dimmed by default)
        drawLight(g2, centerX, yRed, circleDiameter, Color.RED, current == Light.RED);
        drawLight(g2, centerX, yYellow, circleDiameter, Color.YELLOW, current == Light.YELLOW);
        drawLight(g2, centerX, yGreen, circleDiameter, Color.GREEN, current == Light.GREEN);

        // Draw label
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
        String text = "ស្លាកសញ្ញានេះនិងប្តូរពណ៏រៀងរាល់ 10 វិនាទី";
        FontMetrics fm = g2.getFontMetrics();
        int tx = (width - fm.stringWidth(text)) / 2;
        g2.drawString(text, tx, housingHeight + 35);

        g2.dispose();
    }

    private void drawLight(Graphics2D g2, int x, int y, int d, Color color, boolean on) {
        // shadow / outer
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillOval(x - 4, y - 4, d + 8, d + 8);

        // bulb background (dark)
        g2.setColor(new Color(20, 20, 20));
        g2.fillOval(x, y, d, d);

        // actual color (brighter if on)
        if (on) {
            // bright glow
            g2.setPaint(new RadialGradientPaint(new Point(x + d/2, y + d/2), d/2,
                    new float[]{0f, 1f}, new Color[]{color.brighter(), color.darker()}));
            g2.fillOval(x+4, y+4, d-8, d-8);
        } else {
            // dimmed
            Color dim = new Color(Math.max(0, color.getRed()/6), Math.max(0, color.getGreen()/6), Math.max(0, color.getBlue()/6));
            g2.setColor(dim);
            g2.fillOval(x+4, y+4, d-8, d-8);
        }
    }
                                                                                                                                                                                                  public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Stop Light");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new StopLight());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
