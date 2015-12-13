package logic;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import main.Main;
import main.Options;
import particles.ParticleEmitter;
import particles.ParticleManager;
import particles.Smoke;
import util.controls.KeyBoard;
import util.gfx.TextBoxView;
import util.menu.MenuState;

/**
 * Die Klasse, die alle daten eines aktuell aktiven spiels enthält
 */
public class Game extends MenuState {

    private static final float FOCUS_FADE_AINMATION_S = 0.25f;

    private TextBoxView welcomeBox;

    private Player player;
    private Background bg;
    private Layer[] layers = new Layer[2];
    private HUD hud;

    private float focus = 0;
    private boolean animateFocus = false;
    private boolean animateFocusUp;

    private boolean firstRun = true;

    private ParticleManager pm;
    
     public void init() {
        firstRun = false;
        //CONSTRUUCTOR!

        welcomeBox = new TextBoxView(Options.WelcomeBox);
        new Thread() {
            public void run() {
                Main.get().addViewOnTop(welcomeBox);
            }
        }.start();
        bg = new Background();
        player = new Player();
        layers[0] = new Layer();
        layers[1] = new Layer();
        hud = new HUD(player, layers);
        pm = new ParticleManager();
        pm.addEmitter(new ParticleEmitter(new Smoke(500, 500), 0.001));
    }
    
    @Override
    public void render(Graphics2D g) {
        if (firstRun) {
            init();
        }

        bg.render(g);
        layers[0].render(g, -focus);
        player.render(g);
        layers[1].render(g, 1f - focus);
        hud.draw(g);
        
        pm.render(g);

    }

    public ParticleManager getParticleManager() {
        return pm;
    }

    @Override
    public boolean onKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            animateFocus = true;
            animateFocusUp = focus < 0.5f;
        }
        return false;
    }

    @Override
    public int update(float timeSinceLastFrame) {
        if (firstRun) {
            init();
        }
        if (welcomeBox.canBeRemoved()) {
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
            if (focus == 0) {
                Physics.doPhysics(layers[0].getAsteroids(), player);
            } else if (focus == 1) {
                Physics.doPhysics(layers[1].getAsteroids(), player);
            }

            player.update(timeSinceLastFrame);
            hud.update(timeSinceLastFrame);

            pm.update(timeSinceLastFrame);
            
            if (KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE)) {
                KeyBoard.setAllReleased();
                return 0;
            }
        }

        return 1;

    }

   

}
