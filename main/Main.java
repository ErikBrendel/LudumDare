package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.controls.KeyBoard;
import util.controls.Mouse;
import util.gfx.View;
import util.menu.Menu;
import util.sounds.SoundManager;

public class Main extends Project {

    private static final long serialVersionUID = 1L;

    private ArrayList<View> viewList = new ArrayList<>();
    public Object viewListLock = new Object();
    private static Main main = null;

    private Menu m;

    public static Main get() {
        return main;
    }

    public Main() {
        super();
    }

    @Override
    public void init() {
        new SoundManager();
        main = this;
        m = new Menu();
        addViewOnTop(m);
    }

    @Override
    public void update() {
        if (isFocusOwner()) {
            m.update(timeSinceLastFrame);
            if(m.getState() == 1){
                setCursor(java.awt.Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR), new java.awt.Point(0, 0), "NOCURSOR"));
            }else{
                setCursor(java.awt.Cursor.getDefaultCursor());
            }
                
        } else {
            KeyBoard.setAllReleased();
            Mouse.setAllReleased();
            while (!isFocusOwner()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doRenderStuff(Graphics2D g) {
        synchronized (viewListLock) {
            for (int i = 0; i < viewList.size(); i++) {
                if (viewList.get(i).canBeRemoved()) {
                    viewList.remove(i);
                    i--;
                }
            }
            for (View v : viewList) {
                v.draw(g);
            }
        }
    }

    public void addViewOnTop(View v) {
        synchronized (viewListLock) {
            viewList.add(v);
        }
    }

    public void clearViewList() {
        synchronized (viewListLock) {
            viewList.clear();
        }
    }

    public void onKeyPressed(KeyEvent e) {
        synchronized (viewListLock) {
            for (View v : viewList) {
                if (v.onKeyPressed(e)) {
                    return;
                }
            }
        }
    }

    public void onKeyReleased(KeyEvent e) {
        synchronized (viewListLock) {
            for (View v : viewList) {
                if (v.onKeyReleased(e)) {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    int getMenuState() {
        return m.getState();
    }
}
