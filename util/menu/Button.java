package util.menu;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import util.controls.Mouse;
import util.geometry.Bounding;
import util.gfx.GfxLoader;
import util.sounds.SoundManager;

public abstract class Button {

    protected static BufferedImage b0, b1;
    
    static{
        b0 = GfxLoader.loadImage("button_0");
        b1 = GfxLoader.loadImage("button_1");
    }
    
    protected String title;
    protected Bounding b;
    protected boolean selected;
    protected boolean pushed;

    public Button(String title, Bounding b) {
        this.b = b;
        this.title = title;
        selected = false;
    }

    public boolean update() {
        if (b.intersects(Mouse.getPos())) {
            selected = true;
            if (Mouse.isKeyDown(MouseEvent.BUTTON1) && !pushed) {
                SoundManager.playSound(SoundManager.Sounds.button_press);
                pushed = true;
            }
        } else {
            selected = false;
        }
        if(pushed){
            if (!Mouse.isKeyDown(MouseEvent.BUTTON1)) {
                pushed = false;
                return true;
            }
        }
        return false;
    }

    public abstract void render(Graphics2D g);
}
