package util.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.geometry.Point;

public class GfxLoader {

    public static BufferedImage loadImage(String dateiname) {
        try {
            BufferedImage img = ImageIO.read(GfxLoader.class.getClass().getResourceAsStream("/resources/" + dateiname + ".png"));
            return getCompatibleImage(img);
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

    /*
     *
     * SCALING IMAGES
     *
     */
    public static final int MODE_FINE = AffineTransformOp.TYPE_BICUBIC;
    public static final int MODE_MEDIUM = AffineTransformOp.TYPE_BILINEAR;
    public static final int MODE_FAST = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;

    public static BufferedImage getScaledImage(BufferedImage image, int width, int height, int mode) {
        return getScaledImage(image, width, height, mode, image.getType());
    }

    public static BufferedImage getScaledImage(BufferedImage image, int width, int height, int mode, int BufferedImageType) {
        if (width == 0 || height == 0) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, mode);

        return bilinearScaleOp.filter(image, new BufferedImage(width, height, BufferedImageType));
    }

    /*
     * watermarking
     */
    public static BufferedImage createWatermark(BufferedImage original, double opacity) {
        BufferedImage ret = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                Color c = new Color(original.getRGB(x, y), true);
                int alpha = Math.max(0, Math.min(255, (int) Math.round(opacity * c.getAlpha())));
                Color newColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
                ret.setRGB(x, y, newColor.getRGB());
            }
        }

        return ret;
    }

    /*
     * desaturation
     */
    public static BufferedImage desaturate(BufferedImage original) {
        BufferedImage ret = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                Color c = new Color(original.getRGB(x, y), true);
                int mix = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                Color newColor = new Color(mix, mix, mix, c.getAlpha());
                ret.setRGB(x, y, newColor.getRGB());
            }
        }

        return ret;
    }

    public static BufferedImage extendToSquare(BufferedImage img) {
        if (img.getHeight() > img.getWidth()) {
            int delta = (img.getHeight() - img.getWidth()) / 2;
            BufferedImage ret = new BufferedImage(img.getHeight(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    ret.setRGB(x + delta, y, img.getRGB(x, y));
                }
            }
            return ret;
        } else {
            int delta = (img.getWidth() - img.getHeight()) / 2;
            BufferedImage ret = new BufferedImage(img.getWidth(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    ret.setRGB(x, y + delta, img.getRGB(x, y));
                }
            }
            return ret;
        }
    }

    public static BufferedImage getCompatibleImage(BufferedImage img) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        BufferedImage buffy = config.createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);

        Graphics2D g = buffy.createGraphics();
        g.drawImage(img, 0, 0, null);

        return buffy;
    }

    /**
     *
     * @param img1
     * @param img2
     * @param offsetX
     * @param offsetY
     * @return the point relative to the location of img1 which collided first
     */
    public static Point intersect(BufferedImage img1, BufferedImage img2, int offsetX, int offsetY) {
        int secStartX = Math.max(0, offsetX);
        int secStartY = Math.max(0, offsetY);

        int secEndX = Math.min(img1.getWidth(), img2.getWidth()+ offsetX/**/);
        int secEndY = Math.min(img1.getHeight(), img2.getHeight()+ offsetY/**/);

        int widthX = secEndX - secStartX;
        int widthY = secEndY - secStartY;

        widthX = Math.abs(widthX);
        widthY = Math.abs(widthY);

        if (widthX <= 0 || widthY <= 0) {
            return null;
        }

        if (secStartX + widthX > img1.getWidth() || secStartY + widthY > img1.getHeight()) {
            return null;
        }

        if (secStartX + widthX - offsetX > img2.getWidth() || secStartY + widthY - offsetY > img2.getHeight()) {
            return null;
        }

        BufferedImage sec1 = img1.getSubimage(secStartX, secStartY, widthX, widthY);
        BufferedImage sec2 = img2.getSubimage(secStartX - offsetX, secStartY - offsetY, widthX, widthY);
        Point ret = intersect(sec1, sec2);
        if (ret != null) {
            ret = ret.plus(new Point(secStartX, secStartY));
        }
        return ret;
        //return sec1;
    }


    /**
     * please make both images have the same size
     *
     * @param img1
     * @param img2
     * @return x/y coords of the first found overlapping pixel, or null
     */
    public static Point intersect(BufferedImage img1, BufferedImage img2) {
        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                if (new Color(img1.getRGB(x, y), true).getAlpha() > 64) { 
                    if (new Color(img2.getRGB(x, y), true).getAlpha() > 64) {
                        return new Point(x, y);
                    }
                }/* */

            }
        }
        return null;
    }

}
