package util.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import logic.Game;
import util.controls.Mouse;
import util.gfx.View;

public class Menu extends View{

    private ArrayList<MenuState> menuStates;
    private int currentState = 0;
    private int newState = 0;
    Transition t;
    int transitionState = 2;

    public Menu() {
        menuStates = new ArrayList<>();
        menuStates.add(new MainMenu());
        menuStates.add(new Game());
        menuStates.add(new OptionsView());
        menuStates.add(new Credits());
    }

    public void update(float timeSinceLastFrame) {
        if (t != null) {
            transitionState = t.update(timeSinceLastFrame);
            if (transitionState == 2) {
                currentState = newState;
                t = null;
            }
        } else if (newState != currentState) {
            t = new Transition(Mouse.getX(), Mouse.getY(), 0.5f, 0.5f);
        } else {
            newState = menuStates.get(currentState).update(timeSinceLastFrame);
        }
    }

    @Override
    protected void paint(Graphics2D g) {
        if (transitionState == 0) {
            menuStates.get(currentState).render(g);
            t.render(g);
        } else if (transitionState == 1) {
            menuStates.get(newState).render(g);
            t.render(g);
        } else {
            menuStates.get(currentState).render(g);
        }
    }
    
    public boolean onKeyPressed(KeyEvent e) {
        MenuState s = menuStates.get(currentState);
        if (s == null) {
            return false;
        } else {
            return s.onKeyDown(e);
        }
    }
    public boolean onKeyReleased(KeyEvent e) {
        MenuState s = menuStates.get(currentState);
        if (s == null) {
            return false;
        } else {
            return s.onKeyReleased(e);
        }
    }
    
    public int getState() {
        return currentState;
    }
    
    
}

class Transition {

    private int x, y;
    private float size, maxSize;
    private float timeRunning;
    private float circleTime;
    private float transitionTime;

    public Transition(int x, int y, float circleTime, float transitionTime) {
        this.x = x;
        this.y = y;
        if (x < 800) {
            if (y < 450) {
                maxSize = (float) Math.sqrt(Math.pow(1600 - x, 2) + Math.pow(900 - y, 2));
            } else {
                maxSize = (float) Math.sqrt(Math.pow(1600 - x, 2) + Math.pow(y, 2));
            }
        } else {
            if (y < 450) {
                maxSize = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(900 - y, 2));
            } else {
                maxSize = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            }
        }
        this.circleTime = circleTime;
        this.transitionTime = transitionTime;
        this.timeRunning = 0;
    }

    public int update(float timeSinceLastFrame) {
        timeRunning += timeSinceLastFrame;
        if (timeRunning < circleTime) {
            size = timeRunning / circleTime * maxSize;
            return 0;
        } else if (timeRunning < circleTime + transitionTime) {
            return 1;
        } else {
            return 2;
        }
    }

    public void render(Graphics2D g) {
        if (timeRunning < circleTime) {
            int alpha = (int) (timeRunning / circleTime * 55f) + 200;
            g.setColor(new Color(0, 0, 0, alpha));
            g.fillOval((int) (x - size), (int) (y - size), (int) (size * 2), (int) (size * 2));
        } else if (timeRunning < circleTime + transitionTime) {
            int alpha = 255 - (int) ((timeRunning - circleTime) / transitionTime * 255f);
            g.setColor(new Color(0, 0, 0, alpha));
            g.fillRect(0, 0, 1600, 900);
        }
    }
}
