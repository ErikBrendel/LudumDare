package logic;

import java.awt.Color;
import java.awt.Graphics2D;
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
        
        for (Asteroid a : asteroids) {
            a.render(g, focus);
        }
        g.drawString("LayerFocus: " + focus, 50, dY);
        
        if (front) {
            g.translate(0, -50);
        }
    }
}
