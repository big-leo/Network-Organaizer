package ua.at.programmers.netorg.interfaces;

import java.awt.Panel;

import javax.swing.JTextArea;

public interface IntPlugin extends Runnable {

    public String getName();

    public Panel getPanel();

    public void setLog(JTextArea txtLog);
}
