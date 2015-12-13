/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import util.geometry.Point;
import util.geometry.Rect;
import util.gfx.GfxLoader;

/**
 *
 * @author Erik
 */
public abstract class Pickup extends FlyingObject {

    public static final int PickupCount = 1;

    private final int id;

    private Pickup(int id) {
        this.id = id;
        b = new Rect(0, 0, 10, 10);
    }

    @Override
    public void render(Graphics2D g, float height) {
        int dX = location.getIntX();
        int dY = location.getIntY();

        if (height == 0) {
            render = images[0][id];
        } else {
            if (height == 1) {
                render = images[1][id];
            } else if (height == -1) {
                render = images[1][id];
            } else {
                float factor = Math.abs(height);
                //System.err.println("factor = " + factor);
                BufferedImage render1 = images[0][id]; //factor == 0
                BufferedImage render2 = images[1][id]; //factor == 1
                //render = GfxLoader.combine(render1, render2, factor);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - factor));
                g.drawImage(render1, dX, dY, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, factor));
                g.drawImage(render2, dX, dY, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

                render = render1;
                return;
            }
        }
        g.drawImage(render, dX, dY, null);
        //g.setColor(Color.red);
        //g.drawRect((int)b.getX(), (int)b.getY(), (int)b.getWidth(), (int)b.getHeight());
        b.setWidth(render.getWidth());
        b.setHeight(render.getHeight());
    }

    @Override
    public int update(float deltaTime) {
        location = location.plus(moveVector.multiply(deltaTime));
        b.setX(location.getX());
        b.setY(location.getY());
        return 0;
    }
    
    public abstract void doEffect(Player p);

    //
    // STATIC
    //
    private static BufferedImage[/*0=raw|1=blurTrans*/][/*id*/] images = new BufferedImage[2][PickupCount];

    static {
        for (int i = 0; i < PickupCount; i++) {
            images[0][i] = GfxLoader.loadImage("pickup_" + i);
            images[1][i] = GfxLoader.createWatermark(GfxLoader.loadImage("pickup_" + i + "_blur"), 0.4);
        }
    }

    public static Pickup createRepairKit() {
        Pickup p = new Pickup(0) {

            @Override
            public void doEffect(Player p) {
                p.damage(p.getHealth() - 100);
            }
            
        };
        Random r = new Random();
        p.location = new Point(1700, -200 + r.nextInt(1100));
        p.moveVector = new Point(-150 + r.nextInt(100), 40 - r.nextInt(80));
        p.rotateSpeed = -100 + r.nextInt(200);
        return p;
    }

}
