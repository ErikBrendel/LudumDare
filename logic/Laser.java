/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import util.geometry.Point;

/**
 *
 * @author Markus
 */
public class Laser {

    private float x, y, speedx, speedy;
    private boolean dead;

    public Laser(float x, float y, float speedx, float speedy) {
        this.x = x;
        this.y = y;
        Point p = new Point(speedx, speedy).trim(1000);
        this.speedx = p.getX();
        this.speedy = p.getY();
        dead = false;
    }

    public void update(float timeSinceLastFrame) {
        x += speedx * timeSinceLastFrame;
        y += speedy * timeSinceLastFrame;
        if (x > 1600) {
            dead = true;
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(4));
        g.drawLine((int) x, (int) y, (int) (x + speedx / 20f), (int) (y + speedy / 20f));
    }

    public boolean isDead() {
        return dead;
    }
    
    public Point getStart(){
        return new Point(x, y);
    }
    
    public Point getEnd(){
        return new Point(x + speedx / 20f, y + speedy / 20f);
    }
    
}
