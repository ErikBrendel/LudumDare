package util.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import util.controls.Mouse;

public class Menu {

    private ArrayList<MenuState> menuStates;
    private int currentState = 0;
    private int newState = 0;
    Transition t;
    int transitionState = 2;

    public Menu() {
        menuStates = new ArrayList<>();
    }

    public void update(float timeSinceLastFrame) {
        if (t != null) {
            transitionState = t.update(timeSinceLastFrame);
            if (transitionState == 2) {
                currentState = newState;
                t = null;
            }
        } else if (newState != currentState) {
            t = new Transition(Mouse.getX(), Mouse.getY(), 0.7f, 0.7f);
        } else {
            newState = menuStates.get(currentState).update(timeSinceLastFrame);
        }
    }

    public void render(Graphics2D g) {
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