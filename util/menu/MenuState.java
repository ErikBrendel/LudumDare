package menu;

import java.awt.Graphics2D;

public abstract class MenuState {
	public abstract int update(float timeSinceLastFrame);

	public abstract void render(Graphics2D g);
}
