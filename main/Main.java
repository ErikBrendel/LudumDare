package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import util.controls.KeyBoard;
import util.controls.Mouse;
import util.gfx.View;
import util.menu.Menu;

public class Main extends Project {

    private static final long serialVersionUID = 1L;
    
    private ArrayList<View> viewList = new ArrayList<>();
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
        main = this;
        //addViewOnTop(new TextBoxView("test mama ich schreibe einfach mal ein bisschen um zu gucken ob der linebreak geht trololol.."));
        m = new Menu();
        addViewOnTop(m);
        
    }

    @Override
    public void update() {
        if (isFocusOwner()) {
            m.update(timeSinceLastFrame);
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

    @Override
    public void doRenderStuff(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1600, 900);
        
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).canBeRemoved()) {
                viewList.remove(i);
                i--;
            }
        }
        for (View v: viewList) {
            v.draw(g);
        }
    }
    
    public void addViewOnTop(View v) {
        viewList.add(v);
    }
    
    public void clearViewList() {
        viewList.clear();
    }
    
    public void onKeyPressed(KeyEvent e) {
        for (View v: viewList) {
            if (v.onKeyPressed(e)) {
                return;
            }
        }
    }
    public void onKeyReleased(KeyEvent e) {
        for (View v: viewList) {
            if (v.onKeyReleased(e)) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
