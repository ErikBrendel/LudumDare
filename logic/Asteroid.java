package logic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import util.geometry.Bounding;
import util.geometry.Point;
import util.geometry.Rect;
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
    private Bounding b;
    private BufferedImage render = null;
    private boolean canBeRemoved = false;
    private float unHarmFulSecs = 0;

    private Asteroid(int id) {
        this.id = id;
        if(id == 0){
            b = new Rect(0, 0, 100, 100);
        }else{
            b = new Rect(0, 0, 300, 300);
        }
    }

    public Bounding getB() {
        return b;
    }

    public BufferedImage getLastImage() {
        return render;
    }

    public boolean canBeRemoved() {
        return canBeRemoved;
    }

    public void remove() {
        canBeRemoved = true;
    }

    public int getDamage() {
        if (unHarmFulSecs > 0) {
            return 0;
        } else {
            return 20;
        }
    }

    public Point getMoveVector() {
        return moveVector;
    }

    public void setMoveVector(Point moveVector) {
        this.moveVector = moveVector;
    }

    public void setUnHarmFul(float secs) {
        unHarmFulSecs += secs;
    }

    public void render(Graphics2D g, float height) {

        //todo calc draw coords
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
    private static final int asteroidCount = 2;
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
        Random r = new Random();
        a.location = new Point(1700, -200 + r.nextInt(1100));
        a.moveVector = new Point(-150 + r.nextInt(100), 40 - r.nextInt(80));
        a.rotateSpeed = -100 + r.nextInt(200);
        return a;
    }

}
