/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Options;
import util.gfx.GfxLoader;

/**
 *
 * @author Erik
 */
public class HUD {

    private Player p;
    private Layer[] layers;

    private BufferedImage overlay = GfxLoader.loadImage("overlay");
    private BufferedImage healthbar = GfxLoader.loadImage("healthbar");
    private BufferedImage healthbar_end = GfxLoader.loadImage("healthbar_end");

    public HUD(Player p, Layer[] layers) {
        this.p = p;
        this.layers = layers;
    }

    public void draw(Graphics2D g) {
        g.drawImage(overlay, 0, 0, null);
        g.setFont(new Font("monospaced", Font.BOLD, 30));
        //g.setColor(new Color(100, 100, 100));
        //g.fillRect(16, 16, 500, 44);
        int healthBarWidth = (int) (500 * p.getHealth() / 100f);
        if (healthBarWidth > 0) {
            g.drawImage(healthbar.getSubimage(0, 0, healthBarWidth, healthbar.getHeight()), 16, 16, null);
        }
        int endImageWidth = Math.min(healthbar_end.getWidth(), 500 - healthBarWidth);
        if (endImageWidth > 0) {
            g.drawImage(healthbar_end.getSubimage(0, 0, endImageWidth, healthbar_end.getHeight()), 16 + healthBarWidth, 16, null);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + (int) (Options.score), 545, 50);
    }

    public void update(float deltaTime) {
        Options.score += deltaTime;
    }

    public float getScore() {
        return Options.score;
    }
    
    
}
