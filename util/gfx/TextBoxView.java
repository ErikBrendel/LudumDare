/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Erik
 */
public class TextBoxView extends View {

    private String text;
    private ArrayList<String> lines = null;
    private Font font;

    public TextBoxView(String text) {
        this(text, 20, 450, 1560, 430, new Font("Helvetica", Font.PLAIN, 120));
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
        //g.drawString(text, 20, 100);
        if (lines == null) {
            lines = splitIntoLines(text, g, getSize().x - 20);
        }
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 10, g.getFont().getSize() + (i * g.getFont().getSize()));
        }

        g.setFont(oldFont);
    }

    public static final ArrayList<String> splitIntoLines(String fulltext, Graphics2D g, int maxPixelSize) {
        String[] words = fulltext.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] += " ";
        }

        ArrayList<String> lines = new ArrayList<>();

        int counter = 0;

        while (counter < words.length) {
            String line = words[counter];
            
            
            while (counter < words.length - 1 && getSize(g, line).getWidth() + getSize(g, words[counter + 1]).getWidth() < maxPixelSize) {
                counter++;
                line += words[counter];
            }
            lines.add(line);
            counter++;
        }

        return lines;
    }

    public static Rectangle2D getSize(Graphics g, String s) {
        FontMetrics fm = g.getFontMetrics(g.getFont());
        return fm.getStringBounds(s, g);
    }
}
