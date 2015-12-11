/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Erik
 */
public class TextBoxView extends View {
    
    private String text;
    private Font font;
    
    public TextBoxView(String text) {
        this(text, 20, 450, 1560, 430, new Font("Helvetica", Font.PLAIN, 20));
    }
    public TextBoxView(String text, Font f) {
        this(text, 20, 450, 1560, 430, f);
    }
    
    public TextBoxView(String text, int x, int y, int w, int h, Font f) {
        this(text, new Point(x, y), new Point(w, h), f);
    }
    
    public TextBoxView(String text, Point start, Point size, Font f) {
        super(start, size);
        this.text = text;
        this.font = f;
    }

    @Override
    protected void paint(Graphics2D g) {
        Font oldFont = g.getFont();
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().x, getSize().y);
        g.setColor(Color.WHITE);
        g.fillRect(5, 5, getSize().x - 10, getSize().y - 10);
        
        g.setColor(Color.BLACK);
        g.drawString(text, 20, 20);
        
        
        g.setFont(oldFont);
    }
}
