package ua.at.programmers.netorg;

import java.net.*;
import java.io.*;

public class Main {
    public void getUrl(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1251"));
        
        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
        
        if (reader == null)
            reader.close();
    }
    public void scanUrl(String sUrl, int i) throws IOException {
        SocketTU sock1 = new SocketTU(sUrl, i);
        sock1.open();
        sock1.close();
    }
    public void scanUrl(String sUrl) throws IOException {
        for (int i = 1; i < 1000; i++) {
            scanUrl(sUrl, i);
        }
    }
    public static void main(String[] args) throws IOException{
        Main main1 = new Main();
        if (args.length >= 2) {
            if (args[0].equals("-url"))
                main1.getUrl(args[1]);
            if (args[0].equals("-scan")) {
                if (args.length == 3)
                    main1.scanUrl(args[1], Integer.parseInt(args[2]));
                else
                    main1.scanUrl(args[1]);
            }
        }
    }
}
