/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import util.geometry.Point;
import util.gfx.GfxLoader;

/**
 *
 * @author Erik
 */
public class Physics {

    /**
     * creates collision logic / physics in a given list of asteroids
     *
     * @param asteroids
     * @return
     */
    public static ArrayList<FlyingObject> doPhysics(ArrayList<FlyingObject> asteroids) {

        for (int i = 0; i < asteroids.size(); i++) {
            for (int j = 0; j < asteroids.size(); j++) {
                if (i != j) {
                    FlyingObject a1 = asteroids.get(i);
                    FlyingObject a2 = asteroids.get(j);

                    if (a2.getB().getWidth() < a1.getB().getWidth()) {
                        FlyingObject c = a1;
                        a1 = a2;
                        a2 = c;
                    }

                    if (a1.getLastImage() != null && a2.getLastImage() != null) {

                        if (a1.getB().intersects(a2.getB())) {
                            int vX = (int) (a1.getB().getX() - a2.getB().getX());
                            int vY = (int) (a1.getB().getY() - a2.getB().getY());
                            if (GfxLoader.intersect(a1.getLastImage(), a2.getLastImage(), -vX, -vY)) {
                                repel(a1, a2);
                            }
                        }
                    }
                }
            }
        }
        return asteroids;
    }

    public static void repel(FlyingObject a1, FlyingObject a2) {
        Point m1 = a1.getB().getLocation().plus(a1.getB().getSize().multiply(0.5f));
        Point m2 = a2.getB().getLocation().plus(a2.getB().getSize().multiply(0.5f));
        Point center = m1.plus(m2).multiply(0.5f);
        Point v1 = m1.minus(center);
        Point v2 = m2.minus(center);

        Point mv1 = a1.getMoveVector().plus(v1.multiply(2f));
        Point mv2 = a2.getMoveVector().plus(v2.multiply(2f));

        mv1 = mv1.trim(a1.getMoveVector().hypot());
        mv2 = mv2.trim(a1.getMoveVector().hypot());
        a1.setMoveVector(mv1);
        a2.setMoveVector(mv2);
        /*int vX = 0;
         int vY = 0;
         do {
         vX = (int) (a1.getB().getX() - a2.getB().getX());
         vY = (int) (a1.getB().getY() - a2.getB().getY());
         a1.update(0.01f);
         a2.update(0.01f);
         } while (GfxLoader.intersect(a1.getLastImage(), a2.getLastImage(), -vX, -vY));/**/
    }

    /**
     * this only looks at asteroid - player collision. To create
     * as-as-collisions, dont pass any player object
     *
     * @param asteroids
     * @param player
     * @return
     */
    public static ArrayList<FlyingObject> doPhysics(ArrayList<FlyingObject> asteroids, Player player) {
        for (int i = 0; i < asteroids.size(); i++) {
            FlyingObject fo = asteroids.get(i);
            if (fo.getB().intersects(player.getBounding()) && fo.getLastImage() != null) {
                int vX = (int) (fo.getB().getX() - player.getBounding().getX());
                int vY = (int) (fo.getB().getY() - player.getBounding().getY());
                if (GfxLoader.intersect(player.getImage(), fo.getLastImage(), vX, vY)) {
                    if (fo instanceof Asteroid) {
                        Asteroid a = (Asteroid) fo;
                        //repel asteroid from player

                        Point m1 = a.getB().getLocation().plus(a.getB().getSize().multiply(0.5f));
                        Point m2 = player.getBounding().getLocation().plus(player.getBounding().getSize().multiply(0.5f));
                        Point center = m1.plus(m2).multiply(0.5f);
                        Point v1 = m1.minus(center);

                        Point mv1 = a.getMoveVector().plus(v1.multiply(15f));
                        mv1 = mv1.trim(a.getMoveVector().hypot());
                        a.setMoveVector(mv1);

                        Point v2 = m2.minus(center);

                        player.setSpeed(player.getSpeed() * -0.3f);
                        player.damage(a.getDamage());
                        a.setUnHarmFul(0.2f);

                    } else if (fo instanceof Pickup) {
                        fo.remove();
                        Pickup p = (Pickup) fo;
                        p.doEffect(player);
                    }
                }
            }
        }
        return asteroids;
    }
}
