package logic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import util.geometry.Point;
import util.gfx.GfxLoader;

/**
 * hier muss die genaue form / der schaden gespeichert werden
 */
public class Asteroid {

    private float rotation = 0;

    private final int id;

    private Point location;
    private Point moveVector;
    private double rotateSpeed;

    private Asteroid(int id) {
        this.id = id;
        rotateSpeed = 100;
    }

    public void move() {

    }

    public int getDamage() {
        return 0;
    }

    public void render(Graphics2D g, float height) {

        //todo calc draw coords
        int dX = location.getIntX();
        int dY = location.getIntY();

        BufferedImage render;
        if (height == 0) {
            render = GfxLoader.rotateImageDegree(allRawImages[id], rotation);
        } else {
            if (height == 1) {
                render = GfxLoader.rotateImageDegree(allTransparentImages[id], rotation);
            } else if (height == -1) {
                render = GfxLoader.rotateImageDegree(allTransparentImages[id], rotation);
            } else {
                float factor = Math.abs(height);
                System.err.println("factor = " + factor);
                BufferedImage render1 = GfxLoader.rotateImageDegree(allRawImages[id], rotation); //factor == 0
                BufferedImage render2 = GfxLoader.rotateImageDegree(allTransparentImages[id], rotation); //factor == 1
                //render = GfxLoader.combine(render1, render2, factor);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - factor));
                g.drawImage(render1, dX, dY, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, factor));
                g.drawImage(render2, dX, dY, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

                return;
            }
        }
        g.drawImage(render, dX, dY, null);
    }

    public int update(float timeSinceLastFrame) {
        rotation += rotateSpeed * timeSinceLastFrame;
        location = location.plus(moveVector.multiply(timeSinceLastFrame));
        return 0;
    }

    //
    // STATIC STUFF BELOW HERE
    //
    private static final int asteroidCount = 1;
    private static BufferedImage[] allRawImages = new BufferedImage[asteroidCount];
    private static BufferedImage[] allBlurImages = new BufferedImage[asteroidCount];
    private static BufferedImage[] allTransparentImages = new BufferedImage[asteroidCount];

    static {
        for (int i = 0; i < asteroidCount; i++) {
            allRawImages[i] = GfxLoader.loadImage("asteroid_" + i);
            allBlurImages[i] = GfxLoader.loadImage("asteroid_" + i + "_blur");
            allTransparentImages[i] = GfxLoader.createWatermark(allBlurImages[i], 0.4);
        }
    }

    /**
     * create a new random asteroid
     *
     * @param front if this asteroid hould blur out like it is in front when out
     * of focus or not
     * @return
     */
    public static Asteroid createRandomShape() {
        Asteroid a = new Asteroid(new Random().nextInt(asteroidCount));
        Random r = new Random();
        a.location = new Point(1600, -200 + r.nextInt(1100));
        a.moveVector = new Point(-100, 40 - r.nextInt(80));
        return a;
    }

}
