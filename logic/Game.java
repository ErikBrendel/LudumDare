package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import util.menu.MenuState;

/**
 * Die Klasse, die alle daten eines aktuell aktiven spiels enthält
 */
public class Game extends MenuState {

    Background bg;
    private Layer[] layers = new Layer[2];
    private boolean isInLayer1 = true; //wo das schiff gerade ist

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 900);

        bg.render(g);
        layers[0].render(g);
        layers[1].render(g);
    }

    @Override
    public int update(float timeSinceLastFrame) {
        return 1;
    }

}
