package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import util.controls.KeyBoard;
import util.menu.MenuState;

/**
 * Die Klasse, die alle daten eines aktuell aktiven spiels enthält
 */
public class Game extends MenuState {

    Background bg;
    private Layer[] layers = new Layer[2];
    private boolean isInLayer0 = true; //wo das schiff gerade ist

    private boolean firstRun = true;

    @Override
    public void render(Graphics2D g) {
        if (firstRun) {
            init();
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 900);
        
        isInLayer0 = KeyBoard.isKeyDown(KeyEvent.VK_SPACE);

        bg.render(g);
        layers[0].render(g, isInLayer0, false);
        layers[1].render(g, !isInLayer0, true);
    }

    @Override
    public int update(float timeSinceLastFrame) {
        if (firstRun) {
            init();
        }
        bg.update(timeSinceLastFrame);
        return 1;
    }

    public void init() {
        firstRun = false;
        //CONSTRUUCTOR!
        bg = new Background();
        layers[0] = new Layer();
        layers[1] = new Layer();
    }

}
