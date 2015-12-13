/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Markus
 */
public class ParticleManager {
    private float timeSinceLastSpawn;
    private ArrayList<Particle> particles;

    public ParticleManager() {
        particles = new ArrayList<>();
    }
    
    
    
    public void update(float timeSinceLastFrame){
        timeSinceLastSpawn += timeSinceLastFrame;
        while(timeSinceLastSpawn > 0.001){
            timeSinceLastSpawn -= 0.001;
            particles.add(new Smoke(500, 500));
        }
        for(Particle p : particles){
            p.update(timeSinceLastFrame);
        }
        for(int i = 0; i < particles.size(); i++){
            if(particles.get(i).isDead()){
                particles.remove(i);
                i--;
            }
        }
    }
    
    public void render(Graphics2D g){
        for(Particle p : particles){
            p.render(g);
        }
    }
}
