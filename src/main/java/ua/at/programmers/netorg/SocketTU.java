package ua.at.programmers.netorg;

import java.io.*;
import java.net.*;

public class SocketTU {
    static int nextId = 0;
    private int id;
    private SocketAddress sockaddr;
    private Socket socket;
    private boolean isOpen;
    private String error;

    public SocketTU(String sUrl, int port) {
        sockaddr = new InetSocketAddress(sUrl, port);
    }
    public void open() throws IOException {
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
            System.out.println((isOpen) ? "open" : "close");
            if ((error != null) && (error.length() != 0))
                System.out.println(error);
        } catch (SocketTimeoutException toe) {
            error = "Timeout";
        }
        catch (ConnectException ce) {
            error = "port is closing";
        }
    }
    public void close() throws IOException {
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
        }
        System.out.println((isOpen) ? "open" : "close");
    }
}
