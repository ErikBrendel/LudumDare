/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;

/**
 *
 * @author Markus
 */
public class Smoke extends Particle{
    
    private static Color[] colors;
    
    static{
        colors = new Color[40];
        for(int i = 0; i < colors.length; i++){
            colors[i] = new Color(i + 10, i + 10, i + 10, 50);
        }
    }
    
    private float dx;

    public Smoke(int x, int y) {
        super(x, y, colors[ran.nextInt(40)], ran.nextInt(2) + 1, ran.nextFloat() * 2 + 2);
        dx = ran.nextFloat() - 0.5f;
    }

    @Override
    public void move(float timeSinceLastFrame) {
        y -= ran.nextFloat() * 50f * timeSinceLastFrame;
        x += dx * ran.nextFloat() * 100f * timeSinceLastFrame;
    }
    
}
