/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.gfx;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;

/**
 *
 * @author Erik
 */
public abstract class View {

    private Point startPoint = null;
    private Point viewSize = null;
    protected boolean canBeRemoved = false;

    public View() {
        this(new Point(0, 0), new Point(1600, 900));
    }

    public View(int x, int y, int w, int h) {
        this(new Point(x, y), new Point(w, h));
    }

    public View(int x, int y) {
        this(new Point(x, y), new Point(1600 - x, 900 - y));
    }

    public View(Point pos, Point size) {
        startPoint = pos;
        this.viewSize = size;
    }
    
    public boolean canBeRemoved() {
        return canBeRemoved;
    }

    public final void draw(Graphics2D g) {
        g.translate(startPoint.x, startPoint.y);
        
        Shape oldClip = g.getClip();
        g.setClip(0, 0, viewSize.x, viewSize.y);
        
        paint(g);
        
        g.setClip(oldClip);
        g.translate(-startPoint.x, -startPoint.y);
    }

    protected Point getSize() {
        return viewSize;
    }

    protected abstract void paint(Graphics2D g);

    /**
     *
     * @param e
     * @return true if handled, false if this event should be passed to lower views
     */
    public boolean onKeyPressed(KeyEvent e) {
        return false;
    }
}
