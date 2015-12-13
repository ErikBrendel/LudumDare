package logic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import particles.ParticleEmitter;
import particles.Smoke;
import util.controls.KeyBoard;
import util.geometry.Bounding;
import util.geometry.Point;
import util.geometry.Rect;
import util.gfx.GfxLoader;

/**
 *
 * @author Markus
 */
public class Player {

    private static BufferedImage[] looks;
    private static BufferedImage[] damageOverlays;
    private static BufferedImage shieldOverlay;

    private ParticleEmitter emitter1;
    private ParticleEmitter emitter2;
    private ParticleEmitter emitter3;

    private float timeLaser;
    private float timeSinceLastShot;
    private boolean shieldEnabled = false;

    private ArrayList<Laser> laser;

    static {
        int lookCount = 3;
        int damageOverlayCount = 4;

        looks = new BufferedImage[lookCount];
        for (int i = 0; i < lookCount; i++) {
            looks[i] = GfxLoader.loadImage("player_" + i);
        }
        damageOverlays = new BufferedImage[damageOverlayCount];
        for (int i = 0; i < damageOverlayCount; i++) {
            damageOverlays[i] = GfxLoader.loadImage("damage_" + i);
        }
        shieldOverlay = GfxLoader.loadImage("playerShieldOverlay");
    }

    private final int UPSPEED = 3000;
    private final int GRAVITY = 1500;

    private int x, health;
    private float y, speedy;

    private float timeAnimation;
    private int currentLook = 0;
    private Bounding b;

    private BufferedImage look;

    public Player(Game g) {
        x = 100;
        y = 200;
        b = new Rect(x, y, 308, 200);
        health = 100;
        look = new BufferedImage(308, 200, BufferedImage.TYPE_INT_ARGB);
        emitter1 = new ParticleEmitter(new Smoke(500, 500), 0.001);
        emitter2 = new ParticleEmitter(new Smoke(200, 100), 0.001);
        emitter3 = new ParticleEmitter(new Smoke(200, 100), 0.001);
        g.getParticleManager().addEmitter(emitter1);
        g.getParticleManager().addEmitter(emitter2);
        g.getParticleManager().addEmitter(emitter3);
        laser = new ArrayList<>();
    }

    public void addSpeed(float speedAdd) {
        speedy += speedAdd;
    }

    public void setSpeed(float newSpeed) {
        speedy = newSpeed;
    }

    public float getSpeed() {
        return speedy;
    }

    public void update(float timeSinceLastFrame) {
        if (KeyBoard.isKeyDown(KeyEvent.VK_SPACE)) {
            speedy -= UPSPEED * timeSinceLastFrame;
        }
        speedy += GRAVITY * timeSinceLastFrame;

        if (speedy > 1000) {
            speedy = 1000;
        }
        if (speedy < -1000) {
            speedy = -1000;
        }

        y += speedy * timeSinceLastFrame;

        if (y < 0) {
            y = 0;
            speedy = 0;
        }

        if (y > 700) {
            y = 700;
            speedy = 0;
        }
        b.setY(y);
        timeAnimation += timeSinceLastFrame;
        if (timeAnimation >= 0.05) {
            timeAnimation = 0;
            currentLook++;
            currentLook %= 3;
        }

        //reCalc emitter posittions
        Point middle = new Point(x, y).plus(new Point(150, 100));
        Point v1 = new Point(30, -20);
        Point v2 = new Point(-33, -2);
        Point v3 = new Point(125, 25);

        v1 = v1.rotate(speedy / 50);
        v2 = v2.rotate(speedy / 50);
        v3 = v3.rotate(speedy / 50);

        Point p1 = middle.plus(v1);
        Point p2 = middle.plus(v2);
        Point p3 = middle.plus(v3);

        emitter1.setPosition(p1.getIntX(), p1.getIntY());
        emitter2.setPosition(p2.getIntX(), p2.getIntY());
        emitter3.setPosition(p3.getIntX(), p3.getIntY());

        if (timeLaser > 0) {
            timeLaser -= timeSinceLastFrame;
            timeSinceLastShot += timeSinceLastFrame;
            if (timeSinceLastShot > 0.2f) {
                timeSinceLastShot -= 0.2;

                Point v4 = new Point(130, 0);
                v4 = v4.rotate(speedy / 50);
                Point p4 = middle.plus(v4);

                laser.add(new Laser(p4.getX(), p4.getY(), v4.getX(), v4.getY()));
            }
        }

        for (Laser l : laser) {
            l.update(timeSinceLastFrame);
        }

        for (int i = 0; i < laser.size(); i++) {
            if (laser.get(i).isDead()) {
                laser.remove(i);
                i--;
            }
        }
    }

    public void render(Graphics2D g) {
        for (Laser l : laser) {
            l.render(g);
        }
        look = new BufferedImage(308, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = look.createGraphics();
        g2.drawImage(looks[currentLook], 0, 50, null);

        int damageState = (int) Math.round((1f - (health / 100f)) * (damageOverlays.length));

        emitter1.setEnabled(damageState >= 2);
        emitter2.setEnabled(damageState >= 3);
        emitter3.setEnabled(damageState >= 4);

        for (int i = 0; i < damageState; i++) {
            g2.drawImage(damageOverlays[i], 0, 50, null);
        }

        if (shieldEnabled) {
            g2.drawImage(shieldOverlay, 0, 50, null);
        }

        g2.dispose();
        look = GfxLoader.rotateImageDegree(look, speedy / 50);
        g.drawImage(look, x, (int) y, null);
    }

    public BufferedImage getImage() {
        return look;
    }

    public Bounding getBounding() {
        return b;
    }

    public int getHealth() {
        return health;
    }

    void damage(int damage) {
        if (shieldEnabled && damage > 0) {
            shieldEnabled = false;
            return;
        }
        health -= damage;
        if (health <= 0) {
            //Lost Game
            health = 0;
        }
    }

    void setLaser() {
        timeLaser = 5f;
    }

    void setShield() {
        shieldEnabled = true;
    }

    public ArrayList<Laser> getLaser() {
        return laser;
    }

}
