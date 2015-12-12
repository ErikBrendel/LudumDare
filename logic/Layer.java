package logic;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Eine Spiel - Ebene, die die asteroiden enthält
 */
public class Layer {

    private ArrayList<Asteroid> asteroids;

    public void moveEverything() {
        for (Asteroid a : asteroids) {
            a.move();
        }
    }
    
    public void render(Graphics2D g) {
        
    }
}
