/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import util.geometry.Point;

/**
 *
 * @author Erik
 */
public interface ParticleMover {
    public Point move(float x, float y, float deltaTime);
}
