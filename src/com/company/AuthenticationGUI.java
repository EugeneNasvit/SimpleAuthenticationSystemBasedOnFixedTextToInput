package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Created by User on 28.05.2017.
 */
public class AuthenticationGUI {

    private JTextField jTextField;
    private JTextField jTextField1;
    double msDelay = 0;
    private Date start;
    private Date finish;
    private Date push;
    List<Long> betweenPush = new LinkedList<>();
    List<Long> pushDelay = new LinkedList<>();

    void setDisplayValue(String str) {
        jTextField1.setText(str);
    }

    String getText() { return jTextField1.getText(); }

    String getUsername() { return jTextField.getText(); }

    AuthenticationGUI() {

        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("Enter your registered username");
        jTextField = new JTextField();
        JLabel jLabel1 = new JLabel("     Enter the text below              ");
        JLabel jLabel2 = new JLabel("кто не работает тот ест.");
        jTextField1 = new JTextField();
        JButton jButton = new JButton("Send");

        jTextField.setPreferredSize(new Dimension(300, 25));
        jTextField1.setPreferredSize(new Dimension(200, 27));

        //-----------------------jTextField1 actions------------------

        jTextField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent event) {
                char c = event.getKeyChar();
                if (c != ';') {
                    push = new Date();
                    betweenPush.add(push.getTime());
                }

                if (c == 'к') {
                    start = new Date();
                }

                if (c == '.') {
                    finish = new Date();
                    msDelay = ((double) (finish.getTime()) - (double) (start.getTime())) / 1000;
                }
            }
        });

        /*for (int i = 1; i < betweenPush.size(); i++) {
            pushDelay.add(betweenPush.get(i) - betweenPush.get(i - 1));
        }*/
        //------------------------------------------------------------

        AuthenticationFormButton authenticationFormButton = new AuthenticationFormButton(this);
        jButton.addActionListener(authenticationFormButton);

        jLabel2.setFont(new Font("Serif", Font.BOLD, 18));

        jPanel.add(jLabel);
        jPanel.add(jTextField);
        jPanel.add(jLabel1);
        jPanel.add(jLabel2);
        jPanel.add(jTextField1);
        jPanel.add(jButton);

        JFrame jFrame = new JFrame("Authentication");
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setSize(350, 180);
        jFrame.setContentPane(jPanel);
        jFrame.setLocation(700, 320);
    }
}
