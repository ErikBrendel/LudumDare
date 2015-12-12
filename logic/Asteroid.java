package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import util.gfx.GfxLoader;

/**
 * hier muss die genaue form / der schaden gespeichert werden
 */
public class Asteroid {
    
    private BufferedImage rotatedImage;
    
    public void move() {
        
    }
    
    public int getDamage() {
        return 0;
    }
    
    public void render(Graphics2D g) {
        
    }
    
    //
    // STATIC STUFF BELOW HERE
    //
    
    private static final int asteroidCount = 10;
    private static BufferedImage[] allRawImages = new BufferedImage[asteroidCount];
    
    static {
        for (int i = 0; i < asteroidCount; i++) {
            String filename = "asteroid_" + i;
            allRawImages[i] = GfxLoader.loadImage(filename);
        }
    }
    
    
    public static Asteroid createRandomShape() {
        //todo asteroid generieren
        return null;
    }
    
    
}
