package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 28.05.2017.
 */
public class RegGUI {

    private JTextField textField;
    private JTextField jTextField;
    int counter = 0;
    double msDelay = 0;
    private Date start;
    private Date finish;
    private Date push;
    List<Long> betweenPush = new LinkedList<>();
    List<Long> pushDelay = new LinkedList<>();
    final double mainCoef = 2.0686576;
    final double speedCoef = 2.3060041;

    void setDisplayValue(String str) {
        jTextField.setText(str);
    }

    String getText() { return jTextField.getText(); }

    String getUsername() { return textField.getText(); }

    RegGUI() {

        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("Enter username");
        textField = new JTextField();
        JLabel jLabel1 = new JLabel("Enter the text below 10 times");
        JLabel jLabel2 = new JLabel("кто не работает тот ест.");
        jTextField = new JTextField();
        JButton jButton = new JButton("Send");

        textField.setPreferredSize(new Dimension(300, 25));
        jTextField.setPreferredSize(new Dimension(200, 27));

        jLabel2.setFont(new Font("Serif", Font.BOLD, 18));

        jPanel.add(jLabel);
        jPanel.add(textField);
        jPanel.add(jLabel1);
        jPanel.add(jLabel2);
        jPanel.add(jTextField);
        jPanel.add(jButton);

        RegFormButton regFormButton = new RegFormButton(this);
        jButton.addActionListener(regFormButton);

        JFrame jFrame = new JFrame("KeyboardHandwriting");
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setSize(350, 180);
        jFrame.setContentPane(jPanel);
        jFrame.setLocation(300, 320);

        //------------------jTextField actions------------------

        jTextField.addKeyListener(new KeyAdapter() {
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

        //-------------------------------------------------------
    }
}
