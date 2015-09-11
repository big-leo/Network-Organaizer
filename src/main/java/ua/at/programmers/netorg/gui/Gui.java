/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.at.programmers.netorg.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Insets;
import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

import ua.at.programmers.netorg.logic.SocketTU;
import ua.at.programmers.netorg.logic.WebScan;
/**
 *
 * @author bogdan
 */
public class Gui extends JFrame implements ActionListener, Runnable {
    private int sizeX = 500;
    private int sizeY = 150;
    private Panel panelMain;
    private Panel panelBorder;
    private Panel panelScan;
    private Panel panelLog;
    private JTabbedPane tabPane;
    private JLabel prm1;
    private JLabel prm2;
    private JTextField txtFldPrm1;
    private JTextField txtFldPrm2;
    private JButton btnScan;
    private JTextArea txtLog;

    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Network-Organizer GUI");
        setSize(sizeX, sizeY);
        setVisible(true);
        this.create_gui_items();
        panelMain.setLayout(new FlowLayout());
        panelBorder.setLayout(new GridLayout(0, 2));
        panelScan.setLayout(new GridLayout(0, 2));
        panelScan.add(prm1);
        panelScan.add(txtFldPrm1);
        panelScan.add(prm2);
        panelScan.add(txtFldPrm2);
        panelScan.add(btnScan);
        tabPane.addTab("Scan port", panelScan);
        WebScan webScan = new WebScan();
        Thread t = new Thread(webScan);
        t.start();
        System.out.println(webScan.getPanel());
        tabPane.addTab(webScan.getName(), webScan.getPanel());
        panelBorder.add(tabPane);
        JScrollPane scrollPaneLog = new JScrollPane(txtLog);
        panelBorder.add(scrollPaneLog);
        panelMain.add(panelBorder);
        this.add(panelMain);
        this.pack();
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
                    SocketTU.setLog(txtLog);
                    SocketTU.scan(prm1, intPrm2);
                }
                else {
                    SocketTU.setEcho(false);
                    SocketTU.setLog(txtLog);
                    SocketTU.scan(prm1);
                }
            } catch (IOException ioe) {}
        }
    }

    private void create_gui_items() {
        panelMain = new Panel();
        panelBorder = new Panel();
        panelScan = new Panel();
        tabPane = new JTabbedPane();
        prm1 = new JLabel("args[0]");
        prm2 = new JLabel("args[1]");
        txtFldPrm1 = new JTextField(10);
        txtFldPrm2 = new JTextField(10);
        btnScan = new JButton("scan");
        btnScan.setActionCommand("scan");
        btnScan.addActionListener(this);
        txtLog = new JTextArea(5, 20);
        txtLog.setMargin(new Insets(5, 5, 5, 5));
        txtLog.setEditable(false);
    }
}
