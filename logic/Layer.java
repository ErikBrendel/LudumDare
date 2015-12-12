package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Eine Spiel - Ebene, die die asteroiden enthält
 */
public class Layer {

    private final boolean front;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    public Layer(boolean front) {
        asteroids.add(Asteroid.createRandomShape(front));
        this.front = front;
    }

    public void moveEverything() {
        for (Asteroid a : asteroids) {
            a.move();
        }
    }

    /**
     *
     * @param g
     * @param focus ob diese layer gerade im fokus ist
     */
    public void render(Graphics2D g, boolean focus) {
        g.setColor(Color.WHITE);
        int dY = 50;
        if (front) {
            g.translate(0, 50);
        }
        BufferedImage layerImg = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_ARGB);
        Graphics2D layerG = layerImg.createGraphics();
        for (Asteroid a : asteroids) {
            a.render(layerG, focus);
        }
        int drawWidth = 1600;
        int drawHeight = 900;
        int drawX = 0;
        int drawY = 0;
        if (!focus) {
            if (front) {
                drawWidth /= 0.8;
                drawHeight /= 0.8;
            } else {
                drawWidth *= 0.8;
                drawHeight *= 0.8;
            }
            drawX = (1600 - drawWidth) / 2;
            drawY = (900 - drawHeight) / 2;
        }
        g.drawImage(layerImg, drawX, drawY, drawWidth, drawHeight, null);
        g.drawString("LayerFocus: " + focus, 50, dY);

        if (front) {
            g.translate(0, -50);
        }
    }
}
