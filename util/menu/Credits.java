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

/**
 *
 * @author Markus
 */
public class Credits extends MenuState{

    
    
    @Override
    public int update(float timeSinceLastFrame) {
        if(KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE)){
            return 0;
        }
        return 3;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.BOLD, 70));
        g.drawString("Worktitel", 300, 150);
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
