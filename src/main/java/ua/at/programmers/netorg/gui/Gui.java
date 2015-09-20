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
import java.io.File;
import java.io.FileFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;

//import ua.at.programmers.netorg.logic.SocketTU;
//import ua.at.programmers.netorg.logic.WebScan;

import ua.at.programmers.netorg.interfaces.IntPlugin;
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
    private JTabbedPane tabPane;
    private JTextArea txtLog;
    private List<IntPlugin> pluginsList;

    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Network-Organizer GUI");
        setSize(sizeX, sizeY);
        setVisible(true);
        this.create_gui_items();
        panelMain.setLayout(new FlowLayout());
        panelBorder.setLayout(new GridLayout(0, 2));
        pluginsList = new LinkedList<IntPlugin>();
        loadPlugins("plugins");
        for (IntPlugin plugin : pluginsList) {
            plugin.setLog(txtLog);
            Thread t = new Thread(plugin);
            t.start();
            while (plugin.getPanel() == null)
                System.out.println("null");
            tabPane.addTab(plugin.getName(), plugin.getPanel());
        }
        panelBorder.add(tabPane);
        JScrollPane scrollPaneLog = new JScrollPane(txtLog);
        panelBorder.add(scrollPaneLog);
        panelMain.add(panelBorder);
        this.add(panelMain);
        this.pack();
    }

    private void loadPlugins(String pluginsDir) {
        File plugDir = new File(pluginsDir);
        File[] jars = plugDir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.isFile() && file.getName().endsWith(".jar");
                }
            });
        for (int i = 0; i < jars.length; i++) {
            try {
                URL url = jars[i].toURI().toURL();
                //System.out.println(url);
                URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
                JarFile jarFile = new JarFile(url.getFile());
                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    if ((name.endsWith(".class")) && !(name.endsWith("IntPlugin.class"))) {
                        //System.out.println(name);
                        name = name.replace(".class", "");
                        name = name.replace("/", ".");
                        Class plugin = classLoader.loadClass(name);
                        //run construct
                        pluginsList.add((IntPlugin) plugin.newInstance());
                    }
                }
            } catch(MalformedURLException me) {
                System.out.println("Malformed URL Exception");
            } catch(ClassNotFoundException cnfe) {
                System.out.println("Class not found");
            } catch(IOException ioe) {
                System.out.println("IOException");
            } catch(InstantiationException ie) {
                System.out.println("Instantiation Exception");
            } catch(IllegalAccessException iae) {
                System.out.println("Illegal Access Exception");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("line".equals(event.getActionCommand())) {}
    }

    private void create_gui_items() {
        panelMain = new Panel();
        panelBorder = new Panel();
        tabPane = new JTabbedPane();
        txtLog = new JTextArea(5, 20);
        txtLog.setMargin(new Insets(5, 5, 5, 5));
        txtLog.setEditable(false);
    }
}
