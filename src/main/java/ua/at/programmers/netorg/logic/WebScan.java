package ua.at.programmers.netorg.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.Url;

import javax.swing.JTextArea;

import ua.at.programmers.netorg.interfaces.IntPlugin;

public class WebScan implements Runnable, IntPlugin {
    private String name = "Url";
    private String sUrl;
    private JTextArea txtLog;

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
                this.msg(s);
            }

            if (reader == null) {
                reader.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void setLog(JTextArea txtLog) {
        this.txtLog = txtLog;
    }

    private void msg(String msg) {
        if (this.txtLog == null) {
            System.out.println(msg);
        }
        else {
            this.txtLog.append(msg + "\n");
        }
    }
}
