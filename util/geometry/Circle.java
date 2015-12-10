package util.geometry;

public class Circle extends Bounding {

	public float centerX, centerY, radius;

	private float rsqaure;

	public Circle(float x, float y, float radius) {
		this.x = x - radius;
		this.y = y - radius;
		this.centerX = x;
		this.centerY = y;
		this.width = radius * 2;
		this.height = radius * 2;
		this.radius = radius;
		this.rsqaure = radius * radius;
	}

	@Override
	public boolean intersects(Bounding b2) {
		if (b2 instanceof Rect) {
			return intersects((Rect) b2);
		} else if (b2 instanceof Circle) {
			return intersects((Circle) b2);
		} else if (b2 instanceof Point) {
			return intersects((Point) b2);
		} else {
			System.out.println("Not implemented jet: Circle & " + b2 + " intesection");
			return false;
		}
	}

	@Override
	public void translateX(float t) {
		setX(centerX + t);
	}

	@Override
	public void translateY(float t) {
		setY(centerY + t);
	}

	@Override
	public void setX(float x) {
		this.x = x - radius;
		this.centerX = x;
	}

	@Override
	public void setY(float y) {
		this.y = y - radius;
		this.centerY = y;
	}

	protected boolean intersects(Circle c) {
		return Math.pow(centerX - c.centerX, 2) + Math.pow(centerY - c.centerY, 2) <= Math.pow(radius + c.radius, 2);
	}

	protected boolean intersects(Rect r) {
		if (r.intersects(centerX, centerY)) {
			return true;
		}

		for (int i = 0; i < 4; i++) {
			if (intersects(r.x + i / 2 * r.width, r.y + i % 2 * r.height)) {
				return true;
			}
		}

		if (centerX > r.x && centerX < r.x + r.width && centerY > r.y - radius && centerY < r.y + r.height + radius) {
			return true;
		}
		if (centerX > r.x - radius && centerX < r.x + r.width + radius && centerY > r.y && centerY < r.y + r.height) {
			return true;
		}

		return false;
	}

	protected boolean intersects(Point p) {
		return intersects(p.x, p.y);
	}

	protected boolean intersects(float x, float y) {
		return rsqaure > Math.pow(this.centerX - x, 2) + Math.pow(this.centerY - y, 2);
	}

}
