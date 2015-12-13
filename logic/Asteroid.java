package logic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import util.geometry.Rect;
import util.gfx.GfxLoader;

/**
 * hier muss die genaue form / der schaden gespeichert werden
 */
public class Asteroid extends FlyingObject {

    private final int id;
    private float unHarmFulSecs = 0;

    private Asteroid(int id) {
        this.id = id;
        if(id == 0){
            b = new Rect(0, 0, 100, 100);
        }else if (id == 1){
            b = new Rect(0, 0, 300, 300);
        } else {
            b = new Rect(0, 0, 50, 50);
        }
    }

    public int getDamage() {
        if (unHarmFulSecs > 0) {
            return 0;
        } else {
            switch (id) {
                case 1:
                    return 20;
                case 2:
                    return 4;
                case 0:
                default:
                    return 11;
            }
        }
    }

    public void setUnHarmFul(float secs) {
        unHarmFulSecs += secs;
    }

    @Override
    public void render(Graphics2D g, float height) {

        int dX = location.getIntX();
        int dY = location.getIntY();

        if (height == 0) {
            render = getRotatedImage(0, id, rotation);
        } else {
            if (height == 1) {
                render = getRotatedImage(1, id, rotation);
            } else if (height == -1) {
                render = getRotatedImage(1, id, rotation);
            } else {
                float factor = Math.abs(height);
                //System.err.println("factor = " + factor);
                BufferedImage render1 = getRotatedImage(0, id, rotation); //factor == 0
                BufferedImage render2 = getRotatedImage(1, id, rotation); //factor == 1
                //render = GfxLoader.combine(render1, render2, factor);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - factor));
                g.drawImage(render1, dX, dY, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, factor));
                g.drawImage(render2, dX, dY, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

                render = render1;
                return;
            }
        }
        g.drawImage(render, dX, dY, null);
        //g.setColor(Color.red);
        //g.drawRect((int)b.getX(), (int)b.getY(), (int)b.getWidth(), (int)b.getHeight());
        b.setWidth(render.getWidth());
        b.setHeight(render.getHeight());
    }

    public int update(float timeSinceLastFrame) {
        unHarmFulSecs -= timeSinceLastFrame;
        if (unHarmFulSecs < 0) {
            unHarmFulSecs = 0;
        }
        rotation += rotateSpeed * timeSinceLastFrame;
        location = location.plus(moveVector.multiply(timeSinceLastFrame));
        b.setX(location.getX());
        b.setY(location.getY());
        return 0;
    }

    //
    // STATIC STUFF BELOW HERE
    //
    private static final int asteroidCount = 3;
    private static BufferedImage[] allRawImages = new BufferedImage[asteroidCount];
    private static BufferedImage[] allBlurImages = new BufferedImage[asteroidCount];
    private static BufferedImage[] allTransparentImages = new BufferedImage[asteroidCount];

    static {
        for (int i = 0; i < asteroidCount; i++) {
            allRawImages[i] = GfxLoader.loadImage("asteroid_" + (i));
            allBlurImages[i] = GfxLoader.loadImage("asteroid_" + (i) + "_blur");
            allTransparentImages[i] = GfxLoader.createWatermark(allBlurImages[i], 0.4);

        }
    }

    public static BufferedImage getRotatedImage(int version, int asteroidID, float degrees) {
        if (version == 0) {
            return GfxLoader.rotateImageDegree(allRawImages[asteroidID], degrees);
        } else {
            return GfxLoader.rotateImageDegree(allTransparentImages[asteroidID], degrees);
        }
    }

    /**
     * create a new random asteroid
     *
     * @return
     */
    public static Asteroid createRandomShape() {
        Asteroid a = new Asteroid(new Random().nextInt(asteroidCount));
        a.init();
        return a;
    }

}
