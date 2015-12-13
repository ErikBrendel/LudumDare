/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static particles.Particle.ran;
import util.geometry.Point;

/**
 *
 * @author Erik
 */
public class ImageParticle extends Particle {

    private static BufferedImage img;
    private static ArrayList<Color> colors;

    public static void setImage(BufferedImage img) {
        ImageParticle.img = img;
        colors = new ArrayList<>();

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color c = new Color(img.getRGB(x, y), true);
                if (c.getAlpha() > 64) {
                    colors.add(c.darker());
                }
            }
        }
    }
    
    public Particle clone() {
        return new ImageParticle((int) (x), (int) (y));
    }

    public ImageParticle(int x, int y) {
        super(x - img.getWidth()/2 + ran.nextInt(img.getWidth()), y - img.getHeight()/2 + ran.nextInt(img.getHeight()), colors.get(ran.nextInt(colors.size())), ran.nextInt(3) + 2, ran.nextFloat() * 3f + 0.5f, new ParticleMover() {

            float dir = ran.nextFloat() * 360f;
            Point moveVector = new Point(50 + ran.nextFloat() * 70f, 0).rotate(dir);

            @Override
            public Point move(float x, float y, float deltaTime) {
                Point p = new Point(x, y);

                Point v = moveVector.trim(deltaTime * 100f);
                p = p.plus(v);

                return p;
            }
        });
    }
}
