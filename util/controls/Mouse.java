package util.controls;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import util.geometry.Point;

public class Mouse implements MouseListener, MouseMotionListener {
	private static boolean[] keys = new boolean[16];
	private static Point p = new Point(0, 0);
	private static double scale = 1;

	public static void setAllReleased() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}

	public static void setScale(double s) {
		scale = s;
	}

	public static boolean isKeyDown(int keyCode) {
		if (keyCode >= 0 && keyCode < keys.length) {
			return keys[keyCode];
		} else {
			return false;
		}
	}

	public static int getX() {
		return (int) p.getX();
	}

	public static int getY() {
		return (int) p.getY();
	}

	public static Point getPos() {
		return p;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int buttonCode = e.getButton();
		if (buttonCode >= 0 && buttonCode < keys.length) {
			keys[buttonCode] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int buttonCode = e.getButton();
		if (buttonCode >= 0 && buttonCode < keys.length) {
			keys[buttonCode] = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		p.setX(e.getX() / (float) scale);
		p.setY(e.getY() / (float) scale);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		p.setX(e.getX() / (float) scale);
		p.setY(e.getY() / (float) scale);
	}

}
