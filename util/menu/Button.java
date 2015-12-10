package util.menu;

import controls.Mouse;
import geometry.Bounding;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class Button {
	protected String title;
	protected Bounding b;
	protected boolean selected;

	public Button(String title, Bounding b) {
		this.b = b;
		this.title = title;
		selected = false;
	}

	public boolean update() {
		if (b.intersects(Mouse.getPos())) {
			selected = true;
			if (Mouse.isKeyDown(MouseEvent.BUTTON1)) {
				System.out.println(true);
				return true;
			}
		} else {
			selected = false;
		}
		return false;
	}

	public abstract void render(Graphics2D g);
}
