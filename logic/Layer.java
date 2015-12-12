package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Eine Spiel - Ebene, die die asteroiden enthält
 */
public class Layer {

    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    public void moveEverything() {
        for (Asteroid a : asteroids) {
            a.move();
        }
    }
    
    /**
     *
     * @param g
     * @param focus ob diese layer gerade im fokus ist
     * @param front wenn nicht, ob sie im hintergrund oder vorn angezeigt wird
     */
    public void render(Graphics2D g, boolean focus, boolean front) {
        for (Asteroid a : asteroids) {
            a.render(g);
        }
        g.setColor(Color.WHITE);
        int dY = 50;
        if (front) {
            dY += 100;
        }
        g.drawString("LayerFocus: " + focus, 50, dY);
    }
}
