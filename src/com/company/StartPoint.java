package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 28.05.2017.
 */
public class StartPoint {

    public StartPoint() {

        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("Choose the action    ");
        JButton jButton = new JButton("Register");
        JButton jButton1 = new JButton("Log in");

        jLabel.setFont(new Font("Serif", Font.BOLD, 18));

        jPanel.add(jLabel);
        jPanel.add(jButton);
        jPanel.add(jButton1);

        RegButton regButton = new RegButton(this);
        LogInButton logInButton = new LogInButton(this);

        jButton.addActionListener(regButton);
        jButton1.addActionListener(logInButton);

        JFrame jFrame = new JFrame("Start Point");
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setSize(350, 80);
        jFrame.add(jPanel);
        jFrame.setLocation(500, 210);
    }
}
