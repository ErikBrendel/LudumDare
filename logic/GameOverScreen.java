/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import main.Options;
import util.controls.KeyBoard;
import util.controls.Mouse;
import util.gfx.TextBoxView;
import util.web.Highscores;

/**
 *
 * @author Erik
 */
public class GameOverScreen {

    public GameOverScreen() {
        Mouse.wheelRotation = 0;
    }

    float timeVisible = 0;
    private boolean showHighScores = false;
    private boolean highScoresAvailable = false;
    private ArrayList<String> playerNames = null;
    private ArrayList<Integer> playerScores = null;
    private boolean newHighscore = false;

    int update(float timeSinceLastFrame) {
        timeVisible += timeSinceLastFrame;
        if (KeyBoard.isKeyDown(KeyEvent.VK_ENTER)) {
            if (showHighScores) {
                if (Options.username.length() >= 3) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                showHighScores = true;
                KeyBoard.setReleased(KeyEvent.VK_ENTER);
                new Thread() {
                    public void run() {
                        newHighscore = Highscores.upload(Options.username, (int) (Options.score));
                        ArrayList[] stats = Highscores.getStats(); //this method may take a while
                        playerNames = stats[0];
                        playerScores = stats[1];
                        highScoresAvailable = true;
                    }
                }.start();
                return 1;
            }
        }
        return 1;
    }

    void draw(Graphics2D g) {
        int wheelRotation = Mouse.wheelRotation;
        if (timeVisible < 2) {
            g.setColor(new Color(50, 50, 50, (int) (timeVisible * 110f)));
            g.fillRect(200, 200, 1200, 500);
        } else {
            g.setColor(new Color(50, 50, 50, 220));
            g.fillRect(200, 200, 1200, 500);

            g.setColor(Color.LIGHT_GRAY);
            Font big = new Font("Helvetica", Font.BOLD, 100);
            Font small = new Font("Helvetica", Font.BOLD, 40);
            Font smaller = new Font("Helvetica", Font.BOLD, 30);

            String go = "Game Over";
            String score = "Score: " + (int) Options.score;
            String usernameText = "Username: " + Options.username;
            String restart = "Press ENTER to restart";
            String newHigh = "New personal best!";

            g.setFont(big);
            int gowidth = (int) TextBoxView.getSize(g, go).getWidth();
            int scorewidth = (int) TextBoxView.getSize(g, score).getWidth();
            g.setFont(small);
            int userNameTextWidth = (int) TextBoxView.getSize(g, usernameText).getWidth();
            int restartWidth = (int) TextBoxView.getSize(g, restart).getWidth();
            int newHighWidth = (int) TextBoxView.getSize(g, newHigh).getWidth();

            if (!showHighScores && timeVisible % 0.5f < 0.3f) {
                usernameText += "|";
            }

            int drawBoxWidth = 1200;
            if (showHighScores) {
                drawBoxWidth = 600;
            }

            g.setFont(big);
            g.drawString(go, 200 + (drawBoxWidth - gowidth) / 2, 330);
            g.drawString(score, 200 + (drawBoxWidth - scorewidth) / 2, 450);

            g.setFont(small);
            g.drawString(usernameText, 200 + (drawBoxWidth - userNameTextWidth) / 2, 520);
            g.drawString(restart, 200 + (drawBoxWidth - restartWidth) / 2, 620);

            g.setFont(smaller);
            if (showHighScores) {
                Point highStart = new Point(200 + drawBoxWidth + 50, 200 + 50);
                Point highSize = new Point(1200 - drawBoxWidth - 100, 500 - 100);

                if (newHighscore) {
                    g.setFont(small);
                    g.setColor(new Color(150, 0, 0));
                    g.drawString(newHigh, 200 + (drawBoxWidth - newHighWidth) / 2, 240);
                    g.setColor(Color.LIGHT_GRAY);
                    g.setFont(smaller);
                }

                if (highScoresAvailable) {
                    //g.setColor(Color.LIGHT_GRAY);
                    //g.fillRect(highStart.x, highStart.y, highSize.x, highSize.y);
                    int segmentHieght = 50;
                    int maxAmount = highSize.y / segmentHieght;
                    int showAmount = Math.min(playerNames.size(), maxAmount);
                    if (wheelRotation > playerNames.size() - maxAmount) {
                        Mouse.wheelRotation = Math.max(0, playerNames.size() - maxAmount);
                        wheelRotation = Mouse.wheelRotation;
                    }
                    if (wheelRotation < 0) {
                        Mouse.wheelRotation = 0;
                        wheelRotation = Mouse.wheelRotation;
                    }
                    Point segSize = new Point(highSize.x, segmentHieght);
                    for (int i = 0; i < showAmount; i++) {
                        Color bg;
                        if ((i + wheelRotation) % 2 == 0) {
                            bg = new Color(0, 0, 0, 50);
                        } else {
                            bg = new Color(0, 0, 0, 20);
                        }
                        Point segmentStart = new Point(highStart.x, highStart.y + (i * segSize.y));
                        g.setColor(bg);
                        g.fillRect(segmentStart.x, segmentStart.y, segSize.x, segSize.y);
                        g.setColor(Color.WHITE);
                        g.drawString((i + wheelRotation + 1) + ":", segmentStart.x + 10, segmentStart.y + segSize.y - 13);
                        g.drawString(playerNames.get(i + wheelRotation), segmentStart.x + 60, segmentStart.y + segSize.y - 13);
                        String pScore = "" + playerScores.get(i + wheelRotation);
                        Rectangle2D pScoreSize = TextBoxView.getSize(g, pScore);
                        g.drawString(pScore, segmentStart.x + segSize.x - (int) (pScoreSize.getWidth()) - 10, segmentStart.y + segSize.y - 13);
                    }
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(1350, 250 + (int)(((double) wheelRotation / (double) (playerNames.size() - 8)) * 320.), 6, 80);
                } else {
                    String loading = "Loading highscores...";
                    Rectangle2D loadingSize = TextBoxView.getSize(g, loading);
                    g.drawString(loading, highStart.x + (int) (highSize.x - loadingSize.getWidth()) / 2,
                            highStart.y + (int) (highSize.y - loadingSize.getHeight()) / 2);
                }
            }
        }
    }

    public boolean onKeyPressed(KeyEvent e) {
        if (!showHighScores) {
            char c = e.getKeyChar();
            if (TextBoxView.isPrintableChar(c)) {
                if (c != ' ' && c != ':' && c != ';' && c != '=') {
                    if (Options.username.length() < 20) {
                        Options.username += c;
                    }
                }
                return true;
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (Options.username.length() > 0) {
                    Options.username = Options.username.substring(0, Options.username.length() - 1);
                }
                return true;
            }
        }
        return false;
    }
}
