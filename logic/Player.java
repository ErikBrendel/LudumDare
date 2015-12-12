package logic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import util.controls.KeyBoard;
import util.geometry.Bounding;
import util.geometry.Rect;
import util.gfx.GfxLoader;

/**
 *
 * @author Markus
 */

public class Player {
    
    private static BufferedImage[] looks;

    static{
        looks = new BufferedImage[3];
        for(int i = 0; i < 3; i++){
            looks[i] = GfxLoader.loadImage("player_" + i);
        }
    }
    
    private final int UPSPEED = 3000;
    private final int GRAVITY = 1500;
    
    private int x, health;
    private float y, speedy;
    
    private float timeAnimation;
    private int currentLook = 0;
    private Bounding b;

    private BufferedImage look;
    
    public Player() {
        x = 150;
        y = 200;
        b = new Rect(x, y, 250, 200);
        health = 100;
        look = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
    }    
    
    public void update(float timeSinceLastFrame){
        if(KeyBoard.isKeyDown(KeyEvent.VK_SPACE)){
            speedy -= UPSPEED * timeSinceLastFrame;
        }
        speedy += GRAVITY * timeSinceLastFrame;
        
        if(speedy > 1000){
            speedy = 1000;
        }
        if(speedy < -1000){
            speedy = -1000;
        }
        
        y += speedy * timeSinceLastFrame;
        
        if(y < 0){
            y = 0;
            speedy = 0;
        }
        
        if(y > 700){
            y = 700;
            speedy = 0;
        }
        b.setY(y);
        timeAnimation += timeSinceLastFrame;
        if(timeAnimation >= 0.05){
            timeAnimation = 0;
            currentLook++;
            currentLook %= 3;
        }
    }
    
    public void render(Graphics2D g){
        look = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = look.createGraphics();
        g2.drawImage(looks[currentLook], 0, 50, null);
        g2.dispose();
        look = GfxLoader.rotateImageDegree(look, speedy / 50);
        g.drawImage(look, x - 50, (int) y, null);
    }

    public BufferedImage getImage(){
        return look;
    }
    
    public Bounding getBounding() {
        return b;
    }  

    public int getHealth() {
        return health;
    }    
    
    void damage(int damage) {
        health -= damage;
        if(health <= 0){
            //Lost Game
            health = 0;
        }
    }
    
}
