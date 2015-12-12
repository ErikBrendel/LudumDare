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
import util.controls.KeyBoard;
import util.geometry.Circle;

/**
 *
 * @author Markus
 */
public class OptionsView extends MenuState{

    private Button options = new Button("Options", new Circle(800, 500, 100)) {
        @Override
        public void render(Graphics2D g) {
            if (selected) {                
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.DARK_GRAY);
            }
            g.setFont(new Font("Helvetica", 0, 30));
            g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            g.setColor(Color.BLACK);
            g.drawString(title, (int) b.getX() + 30, (int) b.getY() + 90);
        }        
    };
    
    @Override
    public int update(float timeSinceLastFrame) {
        if(KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE)){
            KeyBoard.setAllReleased();
            return 0;
        }
        return 2;
    }

    @Override
    public void render(Graphics2D g) {
        options.render(g);
    }
    
}
