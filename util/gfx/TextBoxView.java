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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.sounds.SoundManager;

/**
 *
 * @author Erik
 */
public class TextBoxView extends View {

    public static final int msPerLetter = 30;
    public static final int animationMS = 100;

    private String text;
    private ArrayList<String> lines = null;
    private long startTypingAnimation = Long.MIN_VALUE; //moment when typing animation started
    private boolean onlyAnimateLastLine = false;
    private boolean isAnimating = false; //only needed to skip animation if there is any
    private int scrollProgress = 0;
    private final Object scrollLock = new Object();
    private Font font;

    private boolean showFace = false;
    private BufferedImage[] faces = null;
    private long animateStart = -1;

    public TextBoxView(String text) {
        this(text, 20, 550, 1560, 330, new Font(Font.MONOSPACED, Font.PLAIN, 60));
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

    public boolean isAnimating() {
        return isAnimating;
    }

    public void showFace(boolean show) {
        this.showFace = show;
    }

    public void setFaces(String f1, String f2) {
        faces = new BufferedImage[2];
        faces[0] = GfxLoader.loadImage(f1);
        faces[1] = GfxLoader.loadImage(f2);
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
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getSize().x, getSize().y);
            g.setColor(new Color(30, 30, 30));
            g.fillRect(5, 5, getSize().x - 10, getSize().y - 10);

            g.setColor(new Color(200, 200, 220));
            //g.drawString(text, 20, 100);
            if (lines == null) {
                int maxWidth = getSize().x - 20;
                if (showFace) {
                    maxWidth -= viewSize.y;
                }
                lines = splitIntoLines(text, g, maxWidth);
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
                        SoundManager.playSound(SoundManager.Sounds.typing);
                    } else {
                        displayedLetterCount += lineNow.length();
                    }
                }
                int drawX = 10;
                if (showFace) {
                    drawX += viewSize.y;
                }
                g.drawString(lineNow, drawX, g.getFont().getSize() + (i * g.getFont().getSize()));
            }

            if (showFace) {
                int img = 0;
                if (isAnimating) {
                    if (animateStart == -1) {
                        animateStart = System.currentTimeMillis();
                    }
                    int delta = (int) (System.currentTimeMillis() - animateStart);
                    if (delta % (2 * animationMS) >= animationMS) {
                        img = 0;
                    } else {
                        img = 1;

                    }
                } else {
                    img = 0;
                    animateStart = -1;
                }
                g.drawImage(faces[img], 10, 10, viewSize.y - 20, viewSize.y - 20, null);
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
