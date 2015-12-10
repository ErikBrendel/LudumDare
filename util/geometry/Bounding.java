package util.geometry;

public abstract class Bounding {
	protected float x, y; // upper left corner
	protected float width, height;

	public abstract boolean intersects(Bounding b2);

	public abstract void setX(float x);

	public abstract void setY(float y);

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void translateX(float t) {
		setX(x + t);
	}

	public void translateY(float t) {
		setY(y + t);
	}

	public void translate(float tx, float ty) {
		translateX(tx);
		translateY(ty);
	}

}
