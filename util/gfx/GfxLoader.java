package util.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GfxLoader {

    public static BufferedImage loadImage(String dateiname) {
        try {
            return ImageIO.read(GfxLoader.class.getClass().getResourceAsStream("/resources/" + dateiname + ".png"));
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

        return bilinearScaleOp.filter(image,new BufferedImage(width, height, BufferedImageType));
    }
    
    
    /*
     * watermarking
     */
    public static BufferedImage createWatermark(BufferedImage original, double opacity) {
        BufferedImage ret = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                Color c = new Color(original.getRGB(x, y), true);
                int alpha = Math.max(0, Math.min(255, (int)Math.round(opacity * c.getAlpha())));
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
        }
        return null; //todo if width is bigger than height
    }

}
