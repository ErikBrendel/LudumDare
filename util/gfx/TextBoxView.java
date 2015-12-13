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
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Erik
 */
public class TextBoxView extends View {

    public static final int msPerLetter = 20;

    private String text;
    private ArrayList<String> lines = null;
    private long startTypingAnimation = Long.MIN_VALUE; //moment when typing animation started
    private boolean onlyAnimateLastLine = false;
    private boolean isAnimating = false; //only needed to skip animation if there is any
    private int scrollProgress = 0;
    private final Object scrollLock = new Object();
    private Font font;

    public TextBoxView(String text) {
        this(text, 20, 550, 1560, 330, new Font(Font.MONOSPACED, Font.PLAIN, 65));
    }

    public TextBoxView(String text, Font f) {
        this(text, 20, 550, 1560, 330, f);
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
    public boolean onKeyPressed(KeyEvent e) {
        synchronized (scrollLock) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (!isAnimating) { //check if not animating at moment
                    scrollProgress++;
                    startTypingAnimation = System.currentTimeMillis();
                    onlyAnimateLastLine = true;
                } else {
                    startTypingAnimation = System.currentTimeMillis() - 10000;
                }
            }
            return true;
        }
    }

    @Override
    protected void paint(Graphics2D g) {
        synchronized (scrollLock) {
            Font oldFont = g.getFont();
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getSize().x, getSize().y);
            g.setColor(new Color(30, 30, 30));
            g.fillRect(5, 5, getSize().x - 10, getSize().y - 10);

            g.setColor(new Color(200, 200, 220));
            //g.drawString(text, 20, 100);
            if (lines == null) {
                lines = splitIntoLines(text, g, getSize().x - 20);
            }
            if (startTypingAnimation < 0) {
                startTypingAnimation = System.currentTimeMillis();
            }
            int visibleLetterCount = (int) ((System.currentTimeMillis() - startTypingAnimation) / msPerLetter);
            int displayedLetterCount = 0;
            isAnimating = false;

            int end = lines.size() - scrollProgress;

            int linesVisible = (int) ((getSize().y - 20) / (double) g.getFont().getSize());

            if (end <= linesVisible - 2) {
                canBeRemoved = true;
                return;
            }
            end = Math.min(end, linesVisible);
            for (int i = 0; i < end; i++) {
                String lineNow = lines.get(i + scrollProgress);
                if (!onlyAnimateLastLine || i == linesVisible - 1) {
                    if (lineNow.length() > visibleLetterCount - displayedLetterCount) {
                        int len = visibleLetterCount - displayedLetterCount;
                        lineNow = lineNow.substring(0, len);
                        displayedLetterCount += len;
                        isAnimating = true;
                    } else {
                        displayedLetterCount += lineNow.length();
                    }
                }
                g.drawString(lineNow, 10, g.getFont().getSize() + (i * g.getFont().getSize()));
            }

            g.setFont(oldFont);
        }
    }

    public static final ArrayList<String> splitIntoLines(String fullText, Graphics2D g, int maxPixelSize) {
        ArrayList<String> result = new ArrayList<>();
        String[] givenLines = fullText.split("\\n");
        for (String givenLine : givenLines) {
            String[] words = givenLine.split(" ");
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
            result.addAll(lines);
        }
        return result;
    }

    public static Rectangle2D getSize(Graphics g, String s) {
        FontMetrics fm = g.getFontMetrics(g.getFont());
        return fm.getStringBounds(s, g);
    }
}
