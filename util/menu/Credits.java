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
import util.controls.KeyBoard;
import util.geometry.Circle;
import util.gfx.GfxLoader;

/**
 *
 * @author Markus
 */
public class Credits extends MenuState{
    
    private static BufferedImage look;
    
    static{
        look = GfxLoader.loadImage("credits");
    }

    private Button back = new Button("Back", new Circle(100, 100, 60)) {
        @Override
        public void render(Graphics2D g) {            
            g.setFont(new Font("Helvetica", 0, 40));
            g.setColor(Color.BLACK);
            if (pushed) {
                g.drawImage(b1, (int) b.getX(), (int) b.getY(), 120, 120, null);
                g.drawString(title, (int)  b.getX() + 13, (int) b.getY() + 73);
            } else {
                g.drawImage(b0, (int) b.getX(), (int) b.getY(), 120, 120, null);
                g.drawString(title, (int)  b.getX() + 10, (int) b.getY() + 70);
            }
            if (selected) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            }
        }
    };
    
    @Override
    public int update(float timeSinceLastFrame) {
        if(KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE) || back.update()){
            return 0;
        }
        return 3;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(look, 0, 0, null);
        back.render(g);
        g.setColor(Color.darkGray);
        g.setFont(new Font("Helvetica", Font.BOLD, 70));
        g.drawString("Outer Space", 300, 150);
        g.setFont(new Font("Helvetica", Font.BOLD, 50));
        g.drawString("A game created for LudumDare 34 in 72h", 300, 250);
        g.drawString("Tools:", 300, 320);
        g.drawString("NetBeans - Programming", 300, 390);
        g.drawString("Bfxr - Sounds", 300, 460);
        g.drawString("Jukedeck - Music", 300, 530);
        
        g.setFont(new Font("Helvetica", Font.ITALIC, 50));
        g.drawString("by Erik Brendel & Markus Brand", 600, 800);
    }
    
}
