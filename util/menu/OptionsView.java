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
import java.awt.event.MouseEvent;
import main.Options;
import util.controls.KeyBoard;
import util.controls.Mouse;
import util.geometry.Bounding;
import util.geometry.Circle;
import util.sounds.SoundManager;

/**
 *
 * @author Markus
 */
public class OptionsView extends MenuState {

    private Button toggleMusic = new ToggleButton("Toggle Music", new Circle(1100, 450, 150));
    private Button toggleSounds = new ToggleButton("Toggle Sounds", new Circle(500, 450, 150));
    
    private Button back = new Button("Back", new Circle(100, 100, 60)) {
        @Override
        public void render(Graphics2D g) {
            if (selected) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.DARK_GRAY);
            }
            g.setFont(new Font("Helvetica", 0, 40));
            g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            g.setColor(Color.BLACK);
            g.drawString(title, (int) b.getX() + 10, (int) b.getY() + 70);
        }
    };

    @Override
    public int update(float timeSinceLastFrame) {

        SoundManager.setPlayMusic(toggleMusic.update());
        Options.playSounds = toggleSounds.update();

        if (KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE) || back.update()) {
            KeyBoard.setAllReleased();
            return 0;
        }
        return 2;
    }

    @Override
    public void render(Graphics2D g) {
        toggleMusic.render(g);
        toggleSounds.render(g);
        back.render(g);
    }

}

class ToggleButton extends Button {

    private boolean toggled;

    public ToggleButton(String title, Bounding b) {
        super(title, b);
        toggled = false;
    }

    @Override
    public void render(Graphics2D g) {
        if (selected) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.DARK_GRAY);
        }
        g.setFont(new Font("Helvetica", 0, 40));
        g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
        g.setColor(Color.BLACK);
        g.drawString(title, (int) b.getX() + 20, (int) b.getY() + 140);
        if (toggled) {
            g.drawString("ON", (int) b.getX() + 100, (int) b.getY() + 190);
        } else {
            g.drawString("OFF", (int) b.getX() + 100, (int) b.getY() + 190);
        }
    }

    @Override
    public boolean update() {
        if (b.intersects(Mouse.getPos())) {
            selected = true;
            if (Mouse.isKeyDown(MouseEvent.BUTTON1)) {
                toggled = !toggled;
                Mouse.setAllReleased();
            }
        } else {
            selected = false;
        }
        return toggled;
    }

}
