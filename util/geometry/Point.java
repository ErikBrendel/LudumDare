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

}
