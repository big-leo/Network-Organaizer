package ua.at.programmers.netorg.logic;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class SocketTU {
    private static int nextId = 0;
    private int id;
    private SocketAddress sockaddr;
    private Socket socket;
    private boolean isOpen;
    private String error;
    private String sUrl;
    private int port;
    private static boolean withEcho = true;
    private static JTextArea txtLog;

    public static void scan(String sUrl, int port) throws IOException {
        SocketTU sock = new SocketTU( sUrl, port );
        sock.open();
        sock.close();
    }

    public static void scan(String sUrl) throws IOException {
        for (int i = 0; i < 1000; i++) {
            SocketTU.scan(sUrl, i);
        }
    }

    public static void setEcho(boolean withEcho) {
        SocketTU.withEcho = withEcho;
    }

    public static void setLog(JTextArea txtLog) {
        SocketTU.txtLog = txtLog;
    }

    private SocketTU(String sUrl, int port) {
        sockaddr = new InetSocketAddress(sUrl, port);
        this.sUrl = sUrl;
        this.port = port;
    }

    private void open() throws IOException {
        try {
            if (socket != null) 
                socket.close();
            if (socket == null) {
                socket = new Socket();
                socket.connect(sockaddr, 1000);
                if (socket != null) {
                    isOpen = true;
                }
            }
            if (isOpen)
                this.msg("open " + sUrl + " " + port );
                //System.out.println((isOpen) ? "open" : "close");
        } catch (SocketTimeoutException toe) {
            error = "Timeout";
        }
        catch (ConnectException ce) {
            error = "port is closing";
        }
        if ((withEcho) && (error != null) && (error.length() != 0)) {
            this.msg(error);
            error = null;
        }
    }

    private void close() throws IOException {
        if (socket != null) {
            try { 
                socket.close();
                if (socket == null)
                    isOpen = false;
            } catch (SocketTimeoutException toe) {
                error = "Timeout";
            }
            catch (ConnectException ce) {
                error = "port is closing";
            }
            if ((withEcho) && (error != null) && (error.length() != 0))
                this.msg(error);
        }
        //System.out.println((isOpen) ? "open" : "close");
    }

    private void msg(String msg) {
        if (SocketTU.txtLog == null) {
            System.out.println(msg);
        }
        else {
            SocketTU.txtLog.append(msg + "\n");
        }
    }
}
