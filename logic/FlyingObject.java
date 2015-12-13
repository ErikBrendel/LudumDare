/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
}
