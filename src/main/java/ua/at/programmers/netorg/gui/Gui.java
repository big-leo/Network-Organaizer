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

import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

import ua.at.programmers.netorg.logic.SocketTU;
/**
 *
 * @author bogdan
 */
public class Gui extends JFrame implements ActionListener, Runnable {
    private int sizeX = 300;
    private int sizeY = 200;
    private Panel panelMain;
    private Panel panelCtrl;
    private JLabel prm1;
    private JLabel prm2;
    private JTextField txtfldprm1;
    private JTextField txtfldprm2;
    private JButton btnscan;
    
    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Network-Organizer GUI");
        setSize(sizeX, sizeY);
        setVisible(true);
        this.create_gui_items();
        panelMain.setLayout(new FlowLayout());
        panelCtrl.setLayout(new GridLayout(0, 2));
        panelCtrl.add(prm1);
        panelCtrl.add(txtfldprm1);
        panelCtrl.add(prm2);
        panelCtrl.add(txtfldprm2);
        panelCtrl.add(btnscan);
        panelMain.add(panelCtrl);
        this.add(panelMain);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if ("scan".equals(event.getActionCommand())) {
            String prm1, prm2;
            int intPrm2 = 0;
            prm1 = txtfldprm1.getText();
            prm2 = txtfldprm2.getText();
            try {
                intPrm2 = Integer.parseInt(prm2);
            } catch (NumberFormatException nfe) {}
            try {
                if (intPrm2 > 0) {
                    SocketTU.setEcho(true);
                    SocketTU.scan(prm1, intPrm2);
                }
                else {
                    SocketTU.setEcho(false);
                    SocketTU.scan(prm1);
                }
            } catch (IOException ioe) {}
        }
    }

    private void create_gui_items() {
        panelMain = new Panel();
        panelCtrl = new Panel();
        prm1 = new JLabel("args[0]");
        prm2 = new JLabel("args[1]");
        txtfldprm1 = new JTextField(10);
        txtfldprm2 = new JTextField(10);
        btnscan = new JButton("scan");
        btnscan.setActionCommand("scan");
        btnscan.addActionListener(this);
    }
}
