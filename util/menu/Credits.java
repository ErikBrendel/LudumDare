/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import util.controls.KeyBoard;

/**
 *
 * @author Markus
 */
public class Credits extends MenuState{

    
    
    @Override
    public int update(float timeSinceLastFrame) {
        if(KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE)){
            return 0;
        }
        return 3;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawString("TODO: Credits", 700, 300);
    }
    
}
