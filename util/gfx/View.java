/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.gfx;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

/**
 *
 * @author Erik
 */
public abstract class View {

    private Point startPoint = null;
    private Point viewSize = null;

    public View() {
        this(new Point(0, 0), null);
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

}
