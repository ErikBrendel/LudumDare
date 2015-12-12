/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import util.geometry.Circle;

/**
 *
 * @author Markus
 */
public class MainMenu extends MenuState {
    
    private Button b = new Button("Start Game", new Circle(800, 250, 100)) {
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
        if (b.update()) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public void render(Graphics2D g) {
        //todo make bg image
        g.setColor(new Color(255, 255, 0));
        g.fillRect(0, 0, 1600, 900);
        b.render(g);
    }
    
}
