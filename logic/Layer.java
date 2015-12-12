package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import util.geometry.Point;

/**
 * Eine Spiel - Ebene, die die asteroiden enthält
 */
public class Layer {

    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    public Layer() {
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    /**
     *
     * @param g
     * @param height -1 = background, 0 = focus, 1 = front
     */
    public void render(Graphics2D g, float height) {
        g.setColor(Color.WHITE);
        int dY = 100 + (int) (50 * height);

        Point imgSize = new Point(1600, 900);
        Point offset = new Point(0, 0);

        if (height != 0) {
            imgSize = imgSize.multiply(1 - (0.2f * height));
            offset = new Point(1600, 900).minus(imgSize).multiply(0.5f);
        }

        //int offsetX = 0;
        //int offsetY = 0;
        BufferedImage layerImg = new BufferedImage(imgSize.getIntX(), imgSize.getIntY(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D layerG = layerImg.createGraphics();
        layerG.translate(-offset.getIntX(), -offset.getIntY());
        for (Asteroid a : asteroids) {
            a.render(layerG, height);
        }

        g.drawImage(layerImg, 0, 0, 1600, 900, null);
        g.drawString("LayerFocus: " + height, 50, dY);
    }

    public int update(float timeSinceLastFrame) {
        if (new Random().nextInt(100) == 0) {
            asteroids.add(Asteroid.createRandomShape());
        }
        for (Asteroid a : asteroids) {
            a.update(timeSinceLastFrame);
        }
        return 0;
    }
}
