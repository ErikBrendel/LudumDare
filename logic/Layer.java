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
    private int layerScore = 0;
    private float timeSinceLastSpawn;

    public Layer() {
        timeSinceLastSpawn = 0;
    }

    public int getScore() {
        return layerScore;
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
        
        float zoom = 0.2f;

        if (height != 0) {
            imgSize = imgSize.multiply(1 - (zoom * height));
            offset = new Point(1600, 900).minus(imgSize).multiply(0.5f);
        }

        double scale = height * zoom + 1.0;
        g.translate(-offset.getIntX(), -offset.getIntY());
        g.scale(scale, scale);
        for (Asteroid a : asteroids) {
            a.render(g, height);
        }
        g.scale(1 / scale, 1 / scale);
        g.translate(offset.getIntX(), offset.getIntY());
    }

    public int update(float timeSinceLastFrame) {
        timeSinceLastSpawn += timeSinceLastFrame;
        if (timeSinceLastSpawn >= 2) {
            timeSinceLastSpawn = 0;
            asteroids.add(Asteroid.createRandomShape());
        }
        for (Asteroid a : asteroids) {
            a.update(timeSinceLastFrame);
            if (a.getB().getX() + a.getB().getWidth() < -100) {
                a.remove();
                layerScore+= a.getDamage();
            }
        }
        //System.err.println("size = " + asteroids.size());
        for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).canBeRemoved()) {
                asteroids.remove(i);
                i--;
            }
        }

        return 0;
    }
}
