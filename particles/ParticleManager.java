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

    private ArrayList<Particle> particles;
    private ArrayList<ParticleEmitter> emitters;
    private final Object emittersLock = new Object();

    public ParticleManager() {
        particles = new ArrayList<>();
        emitters = new ArrayList<>();
    }

    public void update(float timeSinceLastFrame) {
        synchronized (emittersLock) {
            for (ParticleEmitter e : emitters) {
                particles.addAll(e.getOutput(timeSinceLastFrame));
            }
        }

        for (Particle p : particles) {
            p.update(timeSinceLastFrame);
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isDead()) {
                particles.remove(i);
                i--;
            }
        }
    }

    public void render(Graphics2D g) {
        for (Particle p : particles) {
            p.render(g);
        }
    }

    public void addEmitter(ParticleEmitter e) {
        synchronized (emittersLock) {
            emitters.add(e);
        }
    }

    public void removeEmitter(ParticleEmitter e) {
        synchronized (emittersLock) {
            emitters.remove(e);
        }
    }
}
