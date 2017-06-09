package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 28.05.2017.
 */
public class RegButton implements ActionListener {

    StartPoint parent;

    RegButton (StartPoint parent) {
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {

        RegGUI regGUI = new RegGUI();
    }
}
