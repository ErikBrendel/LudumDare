/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.util.ArrayList;

/**
 *
 * @author Erik
 */
public class ParticleEmitter {
    
    private float timeSinceLastSpawn;
    private Particle origin;
    private double spawnDelay;

    public ParticleEmitter(Particle p, double spawnDelay) {
        origin = p;
        this.spawnDelay = spawnDelay;
    }
    
    public ArrayList<Particle> getOutput(float deltaTime) {
        ArrayList<Particle> out = new ArrayList<>();
        timeSinceLastSpawn += deltaTime;
        while (timeSinceLastSpawn > spawnDelay) {
            timeSinceLastSpawn -= spawnDelay;
            out.add(origin.clone());
        }
        return out;
    }
    
    public void translate(int tX, int tY) {
        origin.x += tX;
        origin.y += tY;
    }
}
