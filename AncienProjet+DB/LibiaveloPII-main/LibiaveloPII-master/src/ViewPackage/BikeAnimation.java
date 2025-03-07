package ViewPackage;

import javax.swing.*;
import java.awt.*;

public class BikeAnimation extends JPanel implements Runnable {
    private int x = 0;
    private int y;

    public BikeAnimation() {
        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        y = getHeight() - 90;
        drawRoad(g);
        drawBike(g, x, y);
    }

    private void drawRoad(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, getHeight() - 50, getWidth(), 50);
        g.setColor(Color.WHITE);
        for (int i = 0; i < getWidth(); i += 40) {
            g.fillRect(i, getHeight() - 25, 20, 5);
        }
    }

    private void drawBike(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawOval(x, y, 40, 40);  // Roue avant
        g.drawOval(x + 70, y, 40, 40);  // Roue arrière

        // Dessiner le cadre
        g.drawLine(x + 20, y + 20, x + 70, y - 10);
        g.drawLine(x + 90, y + 20, x + 70, y - 10);
        g.drawLine(x + 20, y + 20, x + 40, y - 10);
        g.drawLine(x + 40, y - 10, x + 70, y - 10);

        // Dessiner le guidon
        g.drawLine(x + 70, y - 10, x + 70, y - 40);  // Tige du guidon
        g.drawLine(x + 60, y - 40, x + 80, y - 40);  // Barre du guidon

        // Dessiner la selle
        g.drawLine(x + 40, y - 10, x + 40, y - 30);  // Tige de la selle
        g.drawLine(x + 35, y - 30, x + 45, y - 30);  // Barre de la selle
    }

    @Override
    public void run() {
        while (true) {
            x += 5;
            if (x > getWidth()) {
                x = -140;
            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
