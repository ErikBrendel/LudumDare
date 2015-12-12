/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import util.controls.KeyBoard;
import util.gfx.GfxLoader;

/**
 *
 * @author Markus
 */

public class Player {
    
    private static BufferedImage[] looks;
    private int currentLook = 0;

    static{
        looks = new BufferedImage[3];
        for(int i = 0; i < 3; i++){
            looks[i] = GfxLoader.loadImage("player_" + i);
        }
    }
    
    private final int UPSPEED = 3500;
    private final int GRAVITY = 2000;
    
    private int x;
    private float y, speedy;

    public Player() {
        x = 100;
        y = 400;
    }
    
    
    
    public void update(float timeSinceLastFrame){
        if(KeyBoard.isKeyDown(KeyEvent.VK_SPACE)){
            speedy -= UPSPEED * timeSinceLastFrame;
        }
        speedy += GRAVITY * timeSinceLastFrame;
        y += speedy * timeSinceLastFrame;
        
        if(y < 0){
            y = 0;
            speedy = 0;
        }
        
        if(y > 800){
            y = 800;
            speedy = 0;
        }
    }
    
    public void render(Graphics2D g){
        g.drawImage(looks[currentLook], x, (int) y, null);
    }
}
