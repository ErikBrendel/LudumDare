/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Options;
import util.gfx.GfxLoader;

/**
 *
 * @author Erik
 */
public class Background {

    private final BufferedImage background, stars0, stars1;
    private float timeSinceStart;

    public Background() {
        background = GfxLoader.loadImage("background");
        stars0 = GfxLoader.loadImage("stars0");
        stars1 = GfxLoader.loadImage("stars1");
        timeSinceStart = 0;
    }

    public void update(float timeSinceLastFrame) {
        timeSinceStart += timeSinceLastFrame;
    }

    public void render(Graphics2D g) {
        g.drawImage(background, 0, 0, null);
        if (Options.animateBackground) {
            int s0xPos = (int) (timeSinceStart * 10) % 3200;

            if (s0xPos >= 1600) {
                g.drawImage(stars0, -s0xPos + 3200, 0, null);
            }
            g.drawImage(stars0, -s0xPos, 0, null);

            int s1xPos = (int) (timeSinceStart * 15) % 3200;

            if (s1xPos >= 1600) {
                g.drawImage(stars1, -s1xPos + 3200, 0, null);
            }
            g.drawImage(stars1, -s1xPos, 0, null);
        }
    }
}
