package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import util.geometry.Point;

/**
 * Eine Spiel - Ebene, die die asteroiden enthält
 */
public class Layer {

    private ArrayList<FlyingObject> asteroids = new ArrayList<>();
    private float timeSinceLastSpawn;

    public Layer() {
        timeSinceLastSpawn = 0;
    }

    public ArrayList<FlyingObject> getAsteroids() {
        return asteroids;
    }

    /**
     *
     * @param g
     * @param height -1 = background, 0 = focus, 1 = front
     */
    public void render(Graphics2D g, float height) {
        g.setColor(Color.WHITE);

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
        for (FlyingObject a : asteroids) {
            a.render(g, height);
        }
        g.scale(1 / scale, 1 / scale);
        g.translate(offset.getIntX(), offset.getIntY());
    }

    public int update(float timeSinceLastFrame, Game g) {
        timeSinceLastSpawn += timeSinceLastFrame;
        if (timeSinceLastSpawn >= 2) {
            timeSinceLastSpawn = 0;
            int r = new Random().nextInt(20);
            switch (r) {
                case 0:
                    asteroids.add(Pickup.createRepairKit());
                    break;
                case 1:
                    asteroids.add(Pickup.createLaser());                
                    break;
                default:
                    asteroids.add(Asteroid.createRandomShape());
                    break;
            }
        }
        for (FlyingObject a : asteroids) {
            a.update(timeSinceLastFrame); //move
            if (a.getB().getX() + a.getB().getWidth() < -200
                    || a.getB().getY() + a.getB().getHeight() < -200
                    || a.getB().getY() > 1100
                    || a.getB().getX() > 1900) {
                a.remove();
            }
        }
        for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).canBeRemoved()) {
                asteroids.remove(i);
                i--;
            }
        }
        asteroids = Physics.doPhysics(asteroids, g); //create collisions

        return 0;
    }
}
