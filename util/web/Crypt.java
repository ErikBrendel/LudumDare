/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.web;

import java.util.Random;

/**
 *
 * @author Erik
 */
public class Crypt {
    
    private static int repeatCount = 10;
    
    public static String encode(int score) {
        String scoreString = "" + score;
        String complete = "";
        Random r = new Random();
        for (int i = 0; i < repeatCount; i++) {
            int offset = r.nextInt(10);
            complete = complete + offset;
            for (int j = 0; j < scoreString.length(); j++) {
                int digit = Integer.valueOf("" + scoreString.charAt(j));
                digit += offset;
                digit = digit % 10;
                complete += digit;
            }
        }
        return complete;
    }
    public static int decode(String msg) {
        return Integer.valueOf(msg);
    }
}
