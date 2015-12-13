/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Erik
 */
public class HUD {
    
    private Player p;
    private Layer[] layers;
    private float score = 0;

    public HUD(Player p, Layer[] layers) {
        this.p = p;
        this.layers = layers;
    }
    
    public void draw(Graphics2D g) {
        g.setFont(new Font("monospaced", Font.BOLD, 30));
        g.setColor(new Color(100, 100, 100));
        g.fillRect(20, 20, 500, 40);
        g.setColor(Color.RED);
        g.fillRect(20, 20, (int)(500 * p.getHealth() / 100f), 40);
        
        
        g.setColor(Color.WHITE);
        g.drawString("Score: " + (int)(score), 545, 50);
    }
    
    public void update(float deltaTime) {
        score += deltaTime;
    }
}
