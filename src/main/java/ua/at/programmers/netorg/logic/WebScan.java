package ua.at.programmers.netorg.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;

import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;

import ua.at.programmers.netorg.interfaces.IntPlugin;

public class WebScan implements ActionListener, IntPlugin {
    private String name = "Url";
    private String sUrl;
    private Panel panelUrl;
    private JLabel labelUrl;
    private JTextField txtUrl;
    private JButton btnGetUrl;
    private JButton btnSaveAs;
    private JTextArea txtLog; //for out log
    private JFileChooser fileChooser;

    @Override
    public void run() {
        this.create_gui_items();
        panelUrl.setLayout(new GridLayout(0, 2));
        panelUrl.add(labelUrl);
        panelUrl.add(txtUrl);
        panelUrl.add(btnGetUrl);
        panelUrl.add(btnSaveAs);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("geturl".equals(event.getActionCommand())) {
            sUrl = txtUrl.getText();
            try {
                URL url = new URL(sUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1251"));

                String s;
                while ((s = reader.readLine()) != null) {
                    this.msg(s);
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
        else if ("saveas".equals(event.getActionCommand())) {
            sUrl = txtUrl.getText();
            try {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                }
                int returnVal = fileChooser.showSaveDialog(panelUrl);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    URL url = new URL(sUrl);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1251"));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileChooser.getSelectedFile().getPath())));

                    String s;
                    while ((s = reader.readLine()) != null) {
                        writer.write(s);
                        writer.write("\n");
                    }

                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.flush();
                        writer.close();
                    }
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }

    private void create_gui_items() {
        panelUrl = new Panel();
        labelUrl = new JLabel("Url");
        txtUrl = new JTextField(10);
        btnGetUrl = new JButton("Get url");
        btnGetUrl.setActionCommand("geturl");
        btnGetUrl.addActionListener(this);
        btnSaveAs = new JButton("Save as");
        btnSaveAs.setActionCommand("saveas");
        btnSaveAs.addActionListener(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Panel getPanel() {
        return this.panelUrl;
    }

    @Override
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
