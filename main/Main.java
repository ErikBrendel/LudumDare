package main;

import java.awt.Color;
import java.awt.Graphics2D;

import controls.KeyBoard;
import controls.Mouse;

public class Main extends Project {

    private static final long serialVersionUID = 1L;

    public Main() {
        super();
    }

    @Override
    public void init() {

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
    }

    public static void main(String[] args) {
        new Main();
    }
}
