package ua.at.programmers.netorg.logic;

import java.io.*;
import java.net.*;

public class WebScan implements Runnable{
    private String sUrl;

    public WebScan(String sUrl) {
        this.sUrl = sUrl;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.sUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1251"));
            
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
            
            if (reader == null) {
                reader.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
