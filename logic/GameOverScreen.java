/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Erik
 */
public class GameOverScreen {

    void draw(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(200, 200, 1200, 500);
    }
    
}
