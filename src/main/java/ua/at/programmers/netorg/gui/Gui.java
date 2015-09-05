/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.at.programmers.netorg.gui;

import javax.swing.JFrame;

/**
 *
 * @author bogdan
 */
public class Gui extends JFrame {
    private int sizeX = 300;
    private int sizeY = 200;
    
    public Gui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Network-Organizer GUI");
        setSize(sizeX, sizeY);
        setVisible(true);    
    }
}
