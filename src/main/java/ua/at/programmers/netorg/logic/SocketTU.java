package ua.at.programmers.netorg.logic;

import java.io.*;
import java.net.*;

import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import ua.at.programmers.netorg.interfaces.IntPlugin;

public class SocketTU implements ActionListener, IntPlugin {
    private String name = "Scan port";
    private static int nextId = 0;
    private int id;
    private SocketAddress sockaddr;
    private Socket socket;
    private boolean isOpen;
    private String error;
    private String sUrl;
    private int port;
    private static boolean withEcho = true;
    private Panel panelScan;
    private JLabel prm1;
    private JLabel prm2;
    private JTextField txtFldPrm1;
    private JTextField txtFldPrm2;
    private JButton btnScan;
    private JTextArea txtLog;
    private InputStream is;
    private OutputStream os;

    @Override
    public void run() {
        this.create_gui_items();
        panelScan.setLayout(new GridLayout(0, 2));
        panelScan.add(prm1);
        panelScan.add(txtFldPrm1);
        panelScan.add(prm2);
        panelScan.add(txtFldPrm2);
        panelScan.add(btnScan);
    }

    public static void setEcho(boolean withEcho) {
        SocketTU.withEcho = withEcho;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Panel getPanel() {
        return this.panelScan;
    }

    @Override
    public void setLog(JTextArea txtLog) {
        this.txtLog = txtLog;
    }

    private void open() throws IOException {
        try {
            if ((socket == null) || (socket.isClosed())) {
                socket = new Socket();
                sockaddr = new InetSocketAddress(sUrl, port);
                socket.connect(sockaddr, 1000);
                if ((socket.isConnected()) && (socket.isClosed() == false)) {
                    isOpen = true;
                }
            }
            if (isOpen)
                this.msg("open " + sUrl + " " + port );
        } catch (SocketTimeoutException toe) {
            //System.out.println(toe);
            error = "Timeout";
        }
        catch (ConnectException ce) {
            //System.out.println(ce);
            error = "port is closing";
        }
        if ((withEcho) && (error != null) && (error.length() != 0)) {
            this.msg(error);
            error = null;
        }
    }

    private void close() throws IOException {
        if ((socket != null) && !(socket.isClosed())){
            try {
                socket.close();
                if (socket.isClosed())
                    isOpen = false;
            } catch (SocketTimeoutException toe) {
                error = "Timeout";
            }
            catch (ConnectException ce) {
                System.out.println(ce);
                error = "port is closing";
            }
            if ((withEcho) && (error != null) && (error.length() != 0))
                this.msg(error);
        }
        //System.out.println((isOpen) ? "open" : "close");
    }

    private void msg(String msg) {
        if (txtLog == null) {
            System.out.println(msg);
        }
        else {
            txtLog.append(msg + "\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("scan".equals(event.getActionCommand())) {
            String prm1, prm2;
            int intPrm2 = 0;
            prm1 = txtFldPrm1.getText();
            prm2 = txtFldPrm2.getText();
            try {
                intPrm2 = Integer.parseInt(prm2);
            } catch (NumberFormatException nfe) {}
            try {

                if (intPrm2 > 0) {
                    SocketTU.setEcho(true);
                    this.sUrl = prm1;
                    this.port = intPrm2;
                    this.open();
                    this.close();
                }
                else {
                    SocketTU.setEcho(false);
                    for (int i = 0; i < 1000; i++) {
                        this.sUrl = prm1;
                        this.port = i;
                        this.open();
                        this.close();
                    }
                }
            } catch (IOException ioe) {
                //System.out.println(ioe);
            }
        }
    }

    private void create_gui_items() {
        panelScan = new Panel();
        prm1 = new JLabel("args[0]");
        prm2 = new JLabel("args[1]");
        txtFldPrm1 = new JTextField(10);
        txtFldPrm2 = new JTextField(10);
        btnScan = new JButton("scan");
        btnScan.setActionCommand("scan");
        btnScan.addActionListener(this);
    }
}
