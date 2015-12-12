package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import util.menu.MenuState;

/**
 * Die Klasse, die alle daten eines aktuell aktiven spiels enthält
 */
public class Game extends MenuState {

    private static final float FOCUS_FADE_AINMATION_S = 0.2f;

    private Player player;
    private Background bg;
    private Layer[] layers = new Layer[2];
    private HUD hud;
    
    
    private float focus = 0;
    private boolean animateFocus = false;
    private boolean animateFocusUp;

    private boolean firstRun = true;

    @Override
    public void render(Graphics2D g) {
        if (firstRun) {
            init();
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 900);

        bg.render(g);
        layers[0].render(g, -focus);
        player.render(g);
        layers[1].render(g, 1f - focus);
        hud.draw(g);
    }

    @Override
    public boolean onKeyDown(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            animateFocus = true;
            animateFocusUp = focus < 0.5f;
        }
        return false;
    }

    @Override
    public boolean onKeyReleased(KeyEvent e) {
        /*if (e.getKeyCode() == KeyEvent.VK_SPACE) {
         animateFocus = true;
         animateFocusUp = false;
         } /* */
        return false;
    }

    @Override
    public int update(float timeSinceLastFrame) {
        if (firstRun) {
            init();
        }
        if (animateFocus) {
            if (animateFocusUp) {
                focus += timeSinceLastFrame / FOCUS_FADE_AINMATION_S;
                if (focus >= 1f) {
                    animateFocus = false;
                    focus = 1f;
                }
            } else {
                focus -= timeSinceLastFrame / FOCUS_FADE_AINMATION_S;
                if (focus <= 0f) {
                    animateFocus = false;
                    focus = 0f;
                }
            }
        }
        bg.update(timeSinceLastFrame);
        layers[0].update(timeSinceLastFrame);
        layers[1].update(timeSinceLastFrame);
        
        for (int i = 0; i < layers[(int) (focus + 0.5)].getAsteroids().size(); i++) {
            if (player.getBounding().intersects(layers[(int) (focus + 0.5)].getAsteroids().get(i).getB())) {
                player.damage(layers[(int) (focus + 0.5)].getAsteroids().get(i).getDamage());
            }
        }
        player.update(timeSinceLastFrame);
        return 1;
    }

    public void init() {
        firstRun = false;
        //CONSTRUUCTOR!
        bg = new Background();
        player = new Player();
        layers[0] = new Layer();
        layers[1] = new Layer();
        hud = new HUD(player);
    }

}
