package gfx;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GfxLoader {

	public static BufferedImage loadImage(String dateiname) {
		try {
			return ImageIO.read(GfxLoader.class.getClassLoader().getResourceAsStream("gfx/" + dateiname + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage rotateImageDegree(Image img, double degrees) {
		AffineTransform affineTransform = AffineTransform.getRotateInstance(Math.toRadians(degrees), img.getWidth(null) / 2, img.getHeight(null) / 2);
		BufferedImage rotatedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), ((BufferedImage) img).getType());
		Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
		g.setTransform(affineTransform);
		g.drawImage(img, 0, 0, null);
		return rotatedImage;
	}

	public static BufferedImage rotateImageRadians(Image img, double radians) {
		AffineTransform affineTransform = AffineTransform.getRotateInstance(radians, img.getWidth(null) / 2, img.getHeight(null) / 2);
		BufferedImage rotatedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), ((BufferedImage) img).getType());
		Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
		g.setTransform(affineTransform);
		g.drawImage(img, 0, 0, null);
		return rotatedImage;
	}

}
