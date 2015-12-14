/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Erik
 */
public class VideoTransmissionFilter extends ImageFilter {

    private static final int FADE_IN_MS = 2000;
    private static final float NoiseStayFactor = 0.3f;

    private long lastMS = -1;
    private long lifeStart = -1;

    @Override
    public BufferedImage filter(BufferedImage img) {
        int blackRGB = new Color(0, 0, 0).getRGB();
        BufferedImage render = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (new Color(img.getRGB(x, y), true).getAlpha() > 50) {
                    render.setRGB(x, y, img.getRGB(x, y));
                } else {
                    render.setRGB(x, y, blackRGB);
                }
            }
        }
        //update logic
        if (lastMS == -1) {
            lastMS = System.currentTimeMillis();
        }
        if (lifeStart == -1) {
            lifeStart = System.currentTimeMillis();
        }
        int deltaTimeMS = (int) (System.currentTimeMillis() - lastMS);
        int lifeTimeMS = (int) (System.currentTimeMillis() - lifeStart);

        //draw logic
        float fadeFactor = 1 - NoiseStayFactor;
        if (lifeTimeMS < FADE_IN_MS) {
            float fadeInFactor = (lifeTimeMS / (float) FADE_IN_MS);
            if (fadeInFactor < fadeFactor) {
                fadeFactor = fadeInFactor;
            }
        }
        render = generateNoise(render, fadeFactor);

        return render;
    }

    private static BufferedImage generateNoise(BufferedImage img, float factor) {
        Random r = new Random();
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color c = new Color(img.getRGB(x, y), true);
                int noise = r.nextInt(255);

                float mixR = (c.getRed() * factor) + (noise * (1f - factor));
                float mixG = (c.getGreen() * factor) + (noise * (1f - factor));
                float mixB = (c.getBlue() * factor) + (noise * (1f - factor));
                Color mix = new Color((int) mixR, (int) mixG, (int) mixB);
                img.setRGB(x, y, mix.getRGB());
            }
        }
        return img;
    }

}
