package util.geometry;

public class Point extends Bounding {

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 1;
        this.height = 1;
    }

    @Override
    public boolean intersects(Bounding b2) {
        if (b2 instanceof Point) {
            return this.x == b2.x && this.y == b2.y;
        }
        return b2.intersects(this);
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
    
    public int getIntX() {
        return (int) Math.round(x);
    }
    public int getIntY() {
        return (int) Math.round(y);
    }
    
    public Point plus(Point p2) {
        return new Point(x + p2.x, y + p2.y);
    }
    
    public Point minus(Point p2) {
        return new Point(x - p2.x, y - p2.y);
    }
    
    public Point multiply(float factor) {
        return new Point(x * factor, y * factor);
    }
    
    public Point trim(float newLength) {
        return multiply(newLength / hypot());
    }
    
    public float hypot() {
        return (float)Math.hypot(x, y);
    }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}
