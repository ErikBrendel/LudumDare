package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import util.controls.KeyBoard;
import util.controls.Mouse;

public abstract class Project extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private JFrame f = new JFrame();

    private long thisFrame;
    private long lastFrame;
    public float timeSinceLastFrame;
    Thread t;

    private int offsetX, offsetY;

    private double scale;

    public abstract void init();

    public Project() {
        t = new Thread(this);
        t.setName("repaint-Thread");
        preInit(3200, 1800);
        start();
    }

    public void preInit(int width, int height) {
        Dimension d = new Dimension(width, height);

        setMinimumSize(new Dimension(160, 90));
        setMaximumSize(new Dimension(16000, 9000));
        setPreferredSize(d);

        f = new JFrame();
        f.setUndecorated(true);

        f.add(this);
        f.pack();
        f.setMinimumSize(new Dimension(160, 90));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setVisible(true);
        addKeyListener(new KeyBoard());
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        addMouseListener(new Mouse());

        f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//		f.setSize(new Dimension(1000, 300));
        f.setLocationRelativeTo(null);
        reSize();
        requestFocus();
    }

    public void reSize() {
        int width = f.getWidth();
        int height = f.getHeight();
        if (width / 16d > height / 9d) {
            scale = height / 900d;
            offsetY = 0;
            offsetX = (int) (width - scale * 1600) / 2;
        } else {
            scale = width / 1600d;
            offsetX = 0;
            offsetY = (int) (height - scale * 900) / 2;
        }
        Mouse.setScale(scale);
    }

    private void loop() {
        lastFrame = System.nanoTime();
        while (!KeyBoard.isKeyDown(KeyEvent.VK_ESCAPE)) {
            thisFrame = System.nanoTime();
            timeSinceLastFrame = (thisFrame - lastFrame) / 1000000000f;
            lastFrame = thisFrame;
            update();
            render();
            // try {
            // Thread.sleep(10);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
        }
        System.exit(0);
    }

    public abstract void update();

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.translate(offsetX, offsetY);
        g.scale(scale, scale);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 900);

        doRenderStuff(g);

        g.setColor(Color.BLACK);
        if (offsetX == 0) {
            if (offsetY != 0) {
                g.fillRect(0, (int) (-offsetY / scale), 1600, (int) (offsetY / scale));
                g.fillRect(0, 900, 1600, (int) (offsetY / scale) + 20);
            }
        } else {
            g.fillRect((int) (-offsetX / scale), 0, (int) (offsetX / scale), 900);
            g.fillRect(1600, 0, (int) (offsetX / scale) + 20, 900);
        }

        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", 0, 40));
        g.drawString(String.valueOf((int) (1 / timeSinceLastFrame)), 10, 40);

        g.dispose();
        bs.show();
    }

    public void start() {
        t.start();
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        t.stop();
    }

    @Override
    public void run() {
        init();
        loop();
    }

    public abstract void doRenderStuff(Graphics2D g);
}
