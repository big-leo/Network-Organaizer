/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.at.programmers.netorg.gui;

import ua.at.programmers.netorg.interfaces.IntPlugin;
import ua.at.programmers.netorg.plugin.PluginLoad;

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

import java.util.LinkedList;
import java.util.List;

import java.io.OutputStream;

import ua.at.programmers.netorg.plugin.PluginLoad;
import ua.at.programmers.netorg.shell.ShellBuilder;

/**
 *
 * @author bogdan
 */
public class Gui extends JFrame implements ActionListener, Runnable {

    private int sizeX = 500;
    private int sizeY = 150;
    private Panel panelMain;
    private Panel panelBorder;
    private Panel panelLog;
    private Panel panelScript;
    private JTabbedPane tabPane;
    private JTextArea txtLog;
    private OutLog outLog;
    private JTextArea txtScript;
    private JButton btnRun;
    private List<IntPlugin> pluginsList;

    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Network-Organizer GUI");
        setSize(sizeX, sizeY);
        setVisible(true);
        this.create_gui_items();
        panelMain.setLayout(new FlowLayout());
        panelBorder.setLayout(new GridLayout(3, 0));
        panelLog.setLayout(new FlowLayout());
        panelScript.setLayout(new BorderLayout());
        pluginsList = PluginLoad.load(pluginsList);
        if ( pluginsList != null )
        for (IntPlugin plugin : pluginsList) {
            plugin.setLog(txtLog);
            Thread t = new Thread(plugin);
            t.start();
            while (plugin.getPanel() == null)
                System.out.println("wait for runing threads with plugins");
            tabPane.addTab(plugin.getName(), plugin.getPanel());
        }
        panelBorder.add(tabPane);
        JScrollPane scrollPaneLog = new JScrollPane(txtLog);
        JScrollPane scrollPanelScript = new JScrollPane(txtScript);
        panelLog.add(scrollPaneLog);
        panelScript.add(scrollPanelScript, BorderLayout.CENTER);
        panelScript.add(btnRun, BorderLayout.PAGE_END);
        panelBorder.add(panelScript);
        panelBorder.add(panelLog);
        panelMain.add(panelBorder);
        this.add(panelMain);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("RunScript".equals(event.getActionCommand())) {
            ShellBuilder shell = new ShellBuilder(outLog);
            shell.getResult(txtScript.getText());
            //if (result != null) {
            //  System.out.println(result);
            //}
        }
    }

    private void create_gui_items() {
        panelMain = new Panel();
        panelBorder = new Panel();
        panelLog = new Panel();
        panelScript = new Panel();
        tabPane = new JTabbedPane();
        txtLog = new JTextArea(10, 30);
        outLog = new OutLog();
        txtLog.setMargin(new Insets(5, 5, 5, 5));
        txtLog.setEditable(false);
        txtScript = new JTextArea(10, 30);
        txtScript.setMargin(new Insets(5, 5, 5, 5));
        txtScript.setEditable(true);
        btnRun = new JButton("Run Script");
        btnRun.setActionCommand("RunScript");
        btnRun.addActionListener(this);
    }

    private class OutLog extends OutputStream {

        @Override
        public void write(int b) {
            txtLog.append(String.valueOf((char) b));
        }
    }
}
