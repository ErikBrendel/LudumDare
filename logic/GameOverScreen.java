/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import main.Options;
import util.controls.KeyBoard;
import util.gfx.TextBoxView;

/**
 *
 * @author Erik
 */
public class GameOverScreen {

    float timeVisible = 0;

    int update(float timeSinceLastFrame) {
        timeVisible += timeSinceLastFrame;
        if(KeyBoard.isKeyDown(KeyEvent.VK_ENTER)){
            return 0;
        }
        return 1;
    }

    void draw(Graphics2D g) {
        if (timeVisible < 2) {
            System.out.println(timeVisible * 110f);
            g.setColor(new Color(50, 50, 50, (int) (timeVisible * 110f)));
            g.fillRect(200, 200, 1200, 500);
        } else {
            g.setColor(new Color(50, 50, 50, 220));
            g.fillRect(200, 200, 1200, 500);
            
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Helvetica", Font.BOLD, 100));
            
            int gowidth = (int)TextBoxView.getSize(g, "Game Over").getWidth();
            int scorewidth = (int)TextBoxView.getSize(g, "Score: "+ (int) Options.score).getWidth();
            
            g.drawString("Game Over", 200 + (1200 - gowidth) / 2, 330);
            g.drawString("Score:" + (int) Options.score, 200 + (1200 - scorewidth) / 2, 450);
            
            g.setFont(new Font("Helvetica", Font.BOLD, 50));
            
            int newwidth = (int)TextBoxView.getSize(g, "Press ENTER to restart").getWidth();            
            
            g.drawString("Press ENTER to restart", 200 + (1200 - newwidth) / 2, 620);
        }
    }

}
