/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import util.geometry.Point;

/**
 *
 * @author Erik
 */
public class Dust extends Particle {

    private static Color[] colors;

    static {
        colors = new Color[40];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(2*i + 50, 2*i + 50, 2*i + 50, 100);
        }
    }
    
    @Override
    public Particle clone() {
        return new Dust((int)(x), (int)(y));
    }

    public Dust(int x, int y) {
        super(x, y, colors[ran.nextInt(40)], ran.nextInt(3) + 2, ran.nextFloat() * 0.2f + 0.1f, new ParticleMover() {

            float dir = ran.nextFloat() * 360;
            Point moveVector = new Point(10 + ran.nextFloat()*20f, 0).rotate(dir);

            
            @Override
            public Point move(float x, float y, float deltaTime) {
                Point p = new Point(x, y);
                
                Point v = moveVector.trim(deltaTime * 100);
                p = p.plus(v);
                
                return p;
            }
        });
    }

}
