package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.geometry.Point;

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

        Point imgSize = new Point(1600, 900);
        Point offset = new Point(0, 0);
        
        if (!focus) {
            if (front) {
                imgSize = imgSize.multiply(0.8f);
            } else {
                imgSize = imgSize.multiply(1 / 0.8f);
            }
            offset = new Point(1600, 900).minus(imgSize).multiply(0.5f);
        }
        
        //int offsetX = 0;
        //int offsetY = 0;
        
        BufferedImage layerImg = new BufferedImage(imgSize.getIntX(), imgSize.getIntY(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D layerG = layerImg.createGraphics();
        layerG.translate(-offset.getIntX(), -offset.getIntY());
        for (Asteroid a : asteroids) {
            a.render(layerG, focus);
        }

        g.drawImage(layerImg, 0, 0, 1600, 900, null);
        g.drawString("LayerFocus: " + focus, 50, dY);
    }

    public int update(float timeSinceLastFrame) {
        for (Asteroid a : asteroids) {
            a.update(timeSinceLastFrame);
        }
        return 0;
    }
}
