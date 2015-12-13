/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author Markus
 */
public abstract class Particle {
    protected static Random ran;
    
    static{
        ran = new Random();
    }
    
    protected float x, y;
    private int size;
    private Color c;
    private boolean dead;
    private float timeToLive;
    
    
    public Particle(int x, int y, Color c, int size, float timeToLive) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.size = size;
        dead = false;
        this.timeToLive = timeToLive;
    }
    
    public void update(float timeSinceLastFrame){
        timeToLive -= timeSinceLastFrame;
        if(timeToLive <= 0){
            dead = true;
        }
        move(timeSinceLastFrame);
    }
    
    public abstract void move(float timeSinceLastFrame);
    
    public void render(Graphics2D g){
        g.setColor(c);
        g.fillRect((int) x, (int) y, size, size);
    }

    public boolean isDead() {
        return dead;
    }
    
    
}
