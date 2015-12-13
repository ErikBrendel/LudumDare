/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import static particles.Particle.ran;
import util.geometry.Point;

/**
 *
 * @author Markus
 */
public class Smoke extends Particle {

    private static Color[] colors;

    static {
        colors = new Color[40];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(i + 10, i + 10, i + 10, 50);
        }
    }

    public Particle clone() {
        //return new Particle((int)(x), (int)(y), colors[ran.nextInt(40)], ran.nextInt(2) + 1, ran.nextFloat() * 2 + 2, mover);
        return new Smoke((int) (x), (int) (y));
    }

    public Smoke(int x, int y) {
        super(x, y, colors[ran.nextInt(40)], ran.nextInt(2) + 1, ran.nextFloat() * 2 + 2, new ParticleMover() {

            @Override
            public Point move(float x, float y, float timeSinceLastFrame) {
                float dx = (ran.nextFloat()*2) - 1f;
                y -= ran.nextFloat() * 50f * timeSinceLastFrame;
                x += dx * ran.nextFloat() * 100f * timeSinceLastFrame;
                return new Point(x, y);
            }
        });
    }

}
