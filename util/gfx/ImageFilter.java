/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author Erik
 */
public abstract class ImageFilter {
    public abstract BufferedImage filter(BufferedImage img);
}
