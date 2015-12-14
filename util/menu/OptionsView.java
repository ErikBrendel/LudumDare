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
import java.awt.image.BufferedImage;
import main.Options;
import util.controls.KeyBoard;
import util.controls.Mouse;
import util.geometry.Bounding;
import util.geometry.Circle;
import util.gfx.GfxLoader;
import util.sounds.SoundManager;

/**
 *
 * @author Markus
 */
public class OptionsView extends MenuState {
    
    private static BufferedImage look;
    
    static{
        look = GfxLoader.loadImage("options");
    }

    private Button toggleMusic = new ToggleButton("Toggle Music", new Circle(1100, 450, 150));
    private Button toggleSounds = new ToggleButton("Toggle Sounds", new Circle(500, 450, 150));

    private Button back = new Button("Back", new Circle(100, 100, 60)) {
        @Override
        public void render(Graphics2D g) {
            g.setFont(new Font("Helvetica", 0, 40));
            g.setColor(Color.BLACK);
            if (pushed) {
                g.drawImage(b1, (int) b.getX(), (int) b.getY(), 120, 120, null);
                g.drawString(title, (int) b.getX() + 13, (int) b.getY() + 73);
            } else {
                g.drawImage(b0, (int) b.getX(), (int) b.getY(), 120, 120, null);
                g.drawString(title, (int) b.getX() + 10, (int) b.getY() + 70);
            }
            if (selected) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            }
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
        g.drawImage(look, 0, 0, null);
        toggleMusic.render(g);
        toggleSounds.render(g);
        back.render(g);
    }

}

class ToggleButton extends Button {

    private boolean toggled;

    public ToggleButton(String title, Bounding b) {
        super(title, b);
        toggled = true;
    }

    @Override
    public void render(Graphics2D g) {
        g.setFont(new Font("Helvetica", 0, 40));
        g.setColor(Color.BLACK);
        if (toggled) {
            g.drawImage(b1, (int) b.getX(), (int) b.getY(), null);
            g.drawString(title, (int) b.getX() + 17, (int) b.getY() + 147);
            g.drawString("ON", (int) b.getX() + 107, (int) b.getY() + 197);
        } else {
            g.drawImage(b0, (int) b.getX(), (int) b.getY(), null);
            g.drawString(title, (int) b.getX() + 10, (int) b.getY() + 140);
            g.drawString("OFF", (int) b.getX() + 100, (int) b.getY() + 190);
        }
        if (selected) {
            g.setColor(new Color(0, 0, 0, 50));
            g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
        }
    }

    @Override
    public boolean update() {
        if (b.intersects(Mouse.getPos())) {
            selected = true;
            if (Mouse.isKeyDown(MouseEvent.BUTTON1)) {
                SoundManager.playSound(SoundManager.Sounds.button_press);
                toggled = !toggled;
                Mouse.setAllReleased();
            }
        } else {
            selected = false;
        }
        return toggled;
    }

}
