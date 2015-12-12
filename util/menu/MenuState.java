package util.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class MenuState {

    public abstract int update(float timeSinceLastFrame);

    public abstract void render(Graphics2D g);
    
    public boolean onKeyDown(KeyEvent e) {
        return false;
    }
    
    public boolean onKeyReleased(KeyEvent e) {
        return false;
    }
}
