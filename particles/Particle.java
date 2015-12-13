/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import util.geometry.Point;

/**
 *
 * @author Markus
 */
public class Particle {

    protected static Random ran;

    static {
        ran = new Random();
    }

    protected float x, y;
    protected int size;
    protected Color c;
    protected boolean dead;
    protected float timeToLive;
    protected ParticleMover mover;

    public Particle(int x, int y, Color c, int size, float timeToLive, ParticleMover mover) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.size = size;
        dead = false;
        this.timeToLive = timeToLive;
        this.mover = mover;
    }
    
    @Override
    public Particle clone() {
        return new Particle((int)(x), (int)(y), c, size, timeToLive, mover);
    }

    public void update(float timeSinceLastFrame) {
        timeToLive -= timeSinceLastFrame;
        if (timeToLive <= 0) {
            dead = true;
        }
        Point move = mover.move(x, y, timeSinceLastFrame);
        this.x = move.getX();
        this.y = move.getY();
    }

    public void render(Graphics2D g) {
        g.setColor(c);
        g.fillRect((int) x, (int) y, size, size);
    }

    public boolean isDead() {
        return dead;
    }

}
