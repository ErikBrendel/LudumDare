package util.geometry;

public class Rect extends Bounding {

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean intersects(Bounding b2) {
        if (b2 instanceof Rect) {
            return intersects((Rect) b2);
        } else if (b2 instanceof Circle) {
            return b2.intersects(this);
        } else if (b2 instanceof Point) {
            return intersects((Point) b2);
        } else {
            System.out.println("Not implemented jet: Circle & " + b2 + " intesection");
            return false;
        }
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    protected boolean intersects(Rect r) {
        return !(this.x + this.width < r.x || r.x + r.width < this.x || this.y + this.height < r.y || r.y + r.height < this.y);
    }

    protected boolean intersects(Point p) {
        return intersects(p.x, p.y);
    }

    protected boolean intersects(float x, float y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }
}
