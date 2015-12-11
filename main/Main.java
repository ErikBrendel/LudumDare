package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import util.controls.KeyBoard;
import util.controls.Mouse;
import util.gfx.TextBoxView;
import util.gfx.View;

public class Main extends Project {

    private static final long serialVersionUID = 1L;
    
    private ArrayList<View> viewList = new ArrayList<>();

    public Main() {
        super();
    }

    @Override
    public void init() {
        View test = new View(100, 100, 500, 500) {

            @Override
            protected void paint(Graphics2D g) {
                g.setColor(Color.red);
                g.fillRect(-100, 0, this.getSize().x - 1, 900);
            }
        };
        addViewOnTop(test);
        View test2 = new View(120, 120) {

            @Override
            protected void paint(Graphics2D g) {
                g.setColor(Color.blue);
                g.fillRect(0, 0, 1000, 1000);
            }
        };
        addViewOnTop(test2);
        addViewOnTop(new TextBoxView("test"));
    }

    @Override
    public void update() {
        if (isFocusOwner()) {

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
        
        for (View v: viewList) {
            v.draw(g);
        }
    }
    
    public void addViewOnTop(View v) {
        viewList.add(v);
    }
    
    public void removeView(View v) {
        viewList.remove(v);
    }
    
    public void clearViewList() {
        viewList.clear();
    }

    public static void main(String[] args) {
        new Main();
    }
}
