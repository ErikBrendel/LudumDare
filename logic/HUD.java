/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Erik
 */
public class HUD {
    
    private Player p;

    public HUD(Player p) {
        this.p = p;
    }
    
    public void draw(Graphics2D g) {
        g.setColor(new Color(100, 100, 100));
        g.fillRect(20, 20, 500, 20);
        g.setColor(Color.RED);
        g.fillRect(20, 20, (int)(500 * p.getHealth() / 100f), 20);
    }
}
