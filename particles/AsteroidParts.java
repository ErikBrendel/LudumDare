/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import java.awt.Graphics2D;
import static particles.Particle.ran;
import util.geometry.Point;

/**
 *
 * @author Erik
 */
public class AsteroidParts extends Particle {

    private static Color[] colors;

    static {
        colors = new Color[40];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(2 * i + 50, 2 * i + 50, 2 * i + 50, 100);
        }
    }

    @Override
    public Particle clone() {
        return new AsteroidParts((int) (x), (int) (y));
    }

    public AsteroidParts(int x, int y) {
        super(x - 50 + ran.nextInt(101), y - 50 + ran.nextInt(101), colors[ran.nextInt(40)], ran.nextInt(20) + 10, ran.nextFloat() * 0.2f + 0.1f, new ParticleMover() {

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
    
    
    public void render(Graphics2D g) {
        g.setColor(c);
        g.fillOval((int) x, (int) y, size, size);
    }
}
