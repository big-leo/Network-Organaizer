package ua.at.programmers.netorg;

import javax.swing.SwingUtilities;

import java.io.IOException;

import ua.at.programmers.netorg.gui.Gui;
//import ua.at.programmers.netorg.console.Menu;

public class Main {

    public void scanUrl(String sUrl, int i) throws IOException {
        //SocketTU.scan(sUrl, i);
    }

    public void scanUrl(String sUrl) throws IOException {
        //SocketTU.scan(sUrl);
    }

    public static void main(String[] args) throws IOException {
        Main main1 = new Main();
        if (args.length == 0) {
            SwingUtilities.invokeLater(new Gui());
        }
        else if (args.length == 1) {
            if (args[0].equals("-h")) {
                System.out.println("for run GUI run without parameters");
                System.out.println("-h for print help");
                System.out.println("-url http://i.ua for print page from url");
                System.out.println("192.168.0.1 22 (or i.ua 80) for scan open port");
            }
            else {
                //SocketTU.scan(args[0]);
            }
        }
        else if (args.length == 2) {
            if (args[0].equals("-url")) {
                /*                WebScan wscan = new WebScan(args[1]);
                Thread tUrl = new Thread(wscan);
                tUrl.start();*/
            }
            else {
                //SocketTU.scan(args[0], Integer.parseInt(args[1]));
            }
        }
    }
}
