/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import main.Options;
import util.geometry.Bounding;
import util.geometry.Point;

/**
 *
 * @author Erik
 */
public abstract class FlyingObject {

    protected float rotation = 0;

    protected Point location;
    protected Point moveVector;
    protected double rotateSpeed;
    protected Bounding b;
    protected BufferedImage render = null;
    protected boolean canBeRemoved = false;
    

    public Bounding getB() {
        return b;
    }

    public BufferedImage getLastImage() {
        return render;
    }

    public boolean canBeRemoved() {
        return canBeRemoved;
    }

    public void remove() {
        canBeRemoved = true;
    }

    public Point getMoveVector() {
        return moveVector;
    }

    public void setMoveVector(Point moveVector) {
        this.moveVector = moveVector;
    }
    
    public abstract void render(Graphics2D g, float height);
    
    public abstract int update(float deltaTime);
    
    protected void init() {
        Random r = new Random();
        //location = new Point(1700, -200 + r.nextInt(1100));
        //moveVector = new Point(-150 + r.nextInt(100), 40 - r.nextInt(80));
        //rotateSpeed = -100 + r.nextInt(200);
        
        Point spawnCircleCenter = new Point(1100, 450); //center of the spawning circle
        
        Point targetPoint = new Point(100 + r.nextFloat()*100, 150 + r.nextFloat()*600);
        Point spawnVector = new Point(0, 800).rotate(r.nextFloat() * 180);
        Point spawnPoint = spawnCircleCenter.plus(spawnVector);
        Point rawMoveVector = targetPoint.minus(spawnPoint);
        
        moveVector = rawMoveVector.trim(Options.score + 150 + r.nextInt(150));
        location = spawnPoint.minus(getB().getSize().multiply(0.5f));
        rotateSpeed = -100 + r.nextInt(200);
    }
}
