/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import main.Options;
import util.controls.KeyBoard;
import util.geometry.Circle;
import util.gfx.GfxLoader;
import util.gfx.TextBoxView;

/**
 *
 * @author Markus
 */
public class MainMenu extends MenuState {

    private static BufferedImage mainMenu;

    static {
       mainMenu = GfxLoader.loadImage("menu");
    }

    private Button start = new Button("Start Game", new Circle(400, 556, 150)) {
        @Override
        public void render(Graphics2D g) {
            g.setFont(new Font("Helvetica", 0, 40));
            g.setColor(Color.BLACK);
            
            int titlewidth = (int) TextBoxView.getSize(g, title).getWidth();
            int titleheight = g.getFont().getSize();
            if (pushed) {
                g.drawImage(b1, (int) b.getX(), (int) b.getY(), null);
                g.drawString(title, b.getX() + (300 - titlewidth) / 2 + 7, b.getY() + (300 - titleheight) / 2 + 7);
            } else {
                g.drawImage(b0, (int) b.getX(), (int) b.getY(), null);
                g.drawString(title, b.getX() + (300 - titlewidth) / 2, b.getY() + (300 - titleheight) / 2);
            }
            if (selected) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            }
        }
    };

    private Button options = new Button("Options", new Circle(1200, 556, 150)) {
        @Override
        public void render(Graphics2D g) {
           g.setFont(new Font("Helvetica", 0, 40));
            g.setColor(Color.BLACK);
            
            int titlewidth = (int) TextBoxView.getSize(g, title).getWidth();
            int titleheight = g.getFont().getSize();
            if (pushed) {
                g.drawImage(b1, (int) b.getX(), (int) b.getY(), null);
                g.drawString(title, b.getX() + (300 - titlewidth) / 2 + 7, b.getY() + (300 - titleheight) / 2 + 7);
            } else {
                g.drawImage(b0, (int) b.getX(), (int) b.getY(), null);
                g.drawString(title, b.getX() + (300 - titlewidth) / 2, b.getY() + (300 - titleheight) / 2);
            }
            if (selected) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            }
        }
    };

    private Button credits = new Button("Credits", new Circle(800, 556, 150)) {
        @Override
        public void render(Graphics2D g) {
            g.setFont(new Font("Helvetica", 0, 40));
            g.setColor(Color.BLACK);
            
            int titlewidth = (int) TextBoxView.getSize(g, title).getWidth();
            int titleheight = g.getFont().getSize();
            if (pushed) {
                g.drawImage(b1, (int) b.getX(), (int) b.getY(), null);
                g.drawString(title, b.getX() + (300 - titlewidth) / 2 + 7, b.getY() + (300 - titleheight) / 2 + 7);
            } else {
                g.drawImage(b0, (int) b.getX(), (int) b.getY(), null);
                g.drawString(title, b.getX() + (300 - titlewidth) / 2, b.getY() + (300 - titleheight) / 2);
            }
            if (selected) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            }
        }
    };

    @Override
    public int update(float timeSinceLastFrame) {
        if (start.update() || KeyBoard.isKeyDown(KeyEvent.VK_ENTER)) {
            KeyBoard.setAllReleased();
            return 1;
        }
        if (options.update()) {
            return 2;
        }
        if (credits.update()) {
            return 3;
        }
        return 0;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(mainMenu, 0, 0, null);
        start.render(g);
        options.render(g);
        credits.render(g);

        g.setColor(Color.darkGray);
        g.setFont(new Font("Helvetica", Font.BOLD, 100));
        int oswidth = (int) TextBoxView.getSize(g, "Outer Space").getWidth();
        g.drawString("Outer Space", (1600 - oswidth) / 2, 280);
    }
}
