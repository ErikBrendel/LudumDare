/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.web;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;

/**
 *
 * @author Erik
 */
public class Highscores {

    private static final String host = "http://87.106.83.248/";

    /**
     *
     * @param username
     * @param score
     * @return true if new personal highscore
     */
    public static boolean upload(String username, int score) {
        String fullUrl = host + "uploadStats.php?name=" + username + "&score=" + Crypt.encode(score) + "&mac=" + getMac();
        String returnString = StaticConnections.getWebContent(fullUrl);
        System.err.println("returnString = " + returnString);
        return returnString.equals("better");
    }

    /**
     * this method returns two arraylists, one of String, one of integer,
     * containing player names and scores. They both have the same size.
     *
     * @return
     */
    public static ArrayList[] getStats() {
        String fullUrl = host + "getStats.php";
        String returnString = StaticConnections.getWebContent(fullUrl);

        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<Integer> playerScores = new ArrayList<>();
        try {
            String[] scorePairs = returnString.split(";");
            for (String scorePair : scorePairs) {
                String[] data = scorePair.split("=");
                String name = data[0];
                int score = Crypt.decode(data[1]);
                playerNames.add(name);
                playerScores.add(score);
            }

            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }/**/
        } catch (Exception ex) {
            //some error!
            System.err.println("BAD SERVER RESPONSE: \n" + returnString);
            ex.printStackTrace();
        }
        ArrayList[] pack = new ArrayList[2];
        pack[0] = playerNames;
        pack[1] = playerScores;
        return pack;
    }
    
    public static String getMac() {
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (Exception ex) {
        }
        return "NO-MAC-FOUND";
    }
}
