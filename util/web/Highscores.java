/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.web;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik
 */
public class Highscores {
    
    private static final String host = "http://87.106.83.248/";

    public static void upload(String username, int score) {
        String fullUrl = host + "uploadStats.php?name=" + username + "&score=" + score;
        String returnString = StaticConnections.getWebContent(fullUrl);
        if (!returnString.equals("ok")) {
            System.err.println("ERROR: Unexpected server response: \"" + returnString + "\"");
        }
    }

    /**
     * this method returns two arraylists, one of String, one of integer,
     * containing player names and scores. They both have the same size.
     * @return
     */
    public static ArrayList[] getStats() {
        String fullUrl = host + "getStats.php";
        String returnString = StaticConnections.getWebContent(fullUrl);
        
        
        
        
        
        ArrayList<String> playerNames = new ArrayList<String>() {
            {
                add("test1");
                add("test2");
            }
        };
        ArrayList<Integer> playerScores = new ArrayList<Integer>() {
            {
                add(123);
                add(63);
            }
        };
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList[] pack = new ArrayList[2];
        pack[0] = playerNames;
        pack[1] = playerScores;
        return pack;
    }
}
