package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import util.gfx.GfxLoader;

/**
 * hier muss die genaue form / der schaden gespeichert werden
 */
public class Asteroid {

    private int rotation = 0;

    private final int id;
    private final boolean front;

    private Asteroid(int id, boolean front) {
        this.id = id;
        this.front = front;
    }

    public void move() {

    }

    public int getDamage() {
        return 0;
    }

    public void render(Graphics2D g, boolean focus) {

        //todo calc draw coords
        int dX = 100;
        int dY = 100;
        rotation++;

        BufferedImage render;
        if (focus) {
            render = GfxLoader.rotateImageDegree(allRawImages[id], rotation);
        } else {
            if (front) {
                render = GfxLoader.rotateImageDegree(allTransparentImages[id], rotation);
            } else {
                render = GfxLoader.rotateImageDegree(allBlurImages[id], rotation);
            }
        }
        g.drawImage(render, dX, dY, null);
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
            allTransparentImages[i] = GfxLoader.createWatermark(allBlurImages[i], 0.5);
        }
    }

    /**
     * create a new random asteroid
     *
     * @param front if this asteroid hould blur out like it is in front when out
     * of focus or not
     * @return
     */
    public static Asteroid createRandomShape(boolean front) {
        return new Asteroid(new Random().nextInt(asteroidCount), front);
    }

}
