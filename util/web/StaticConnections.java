/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Erik Brendel
 */
public class StaticConnections {

    //get Web url content
    public static void getAsyncWebContent(String url, WebContentRequester requester, int identifier) {
        Timer dl = new Timer();
        dl.schedule(new AsyncDownloader(url, requester, identifier), 0);
    }
    private static class AsyncDownloader extends TimerTask {
        public AsyncDownloader(String url, WebContentRequester requester, int identifier) {
            URL = url;
            REQUESTER = requester;
            IDENTIFIER = identifier;
        }
        String URL;
        WebContentRequester REQUESTER;
        int IDENTIFIER;
        @Override
        public void run() {
            String response = getWebContent(URL);
            System.out.println("[WEBHANDLER] Request for " + URL + " finished!");
            System.out.println("[WEBHANLDER] Content: " + response);
            REQUESTER.recieveWebContent(response, IDENTIFIER);
        }
    }

    public static String getWebContent(String url2) {
        String erg = "";

        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(url2);
            is = url.openStream(); // deswegen try
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                erg += line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {

            }
        }
        return erg;
    }
}
