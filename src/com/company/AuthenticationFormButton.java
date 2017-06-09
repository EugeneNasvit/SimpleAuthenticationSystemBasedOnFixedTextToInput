package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 28.05.2017.
 */
public class AuthenticationFormButton implements ActionListener {

    AuthenticationGUI parent;
    final double mainCoef = 2.0686576;

    AuthenticationFormButton(AuthenticationGUI parent) {
        this.parent = parent;
    }

    private static String writeFileIntoString(String filePath, String text) {
        try {
            StringBuilder builder = new StringBuilder();
            FileInputStream inputStream = new FileInputStream(filePath);
            InputStreamReader streamReader = new InputStreamReader(inputStream, "Windows-1251");
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                builder.append(currentLine);
            }
            text = builder.toString();
        } catch (Exception e) {
            e.getMessage();
        }
        return text;
    }

    public void actionPerformed(ActionEvent event) {

        if (parent.betweenPush.size() > 40) {
            while(parent.betweenPush.size() != 24) {
                parent.betweenPush.remove(0);
            }
        }

        if (parent.pushDelay.size() > 0) {
            while(parent.pushDelay.size() != 0) {
                parent.pushDelay.remove(0);
            }
        }

        for (int i = 1; i < parent.betweenPush.size(); i++) {
            parent.pushDelay.add(parent.betweenPush.get(i) - parent.betweenPush.get(i - 1));
        }

        String username = parent.getUsername();
        File file = new File("C:\\" + username);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "This username doesn't exists! Register it firstly!");
        } else {
            String text = parent.getText();
            if (!text.equals("кто не работает тот ест.")) {
                JOptionPane.showMessageDialog(null, "Incorrect input!");
                parent.setDisplayValue("");
            } else {
                //List<Long> list = parent.pushDelay;
                List<Double> expectations = new LinkedList<>();
                List<Double> significantCoefs = new LinkedList<>();
                double expectation = 0;

                for (int i = 0; i < parent.pushDelay.size(); i++) {
                    for (int j = 0; j < parent.pushDelay.size(); j++) {
                        if (j != i) {
                            expectation += parent.pushDelay.get(j);
                        }
                    }
                    expectation /= (parent.pushDelay.size() - 1);
                    expectations.add(expectation);
                }

                double sum = 0;
                double deviation;
                double coefficient;

                for (int i = 0; i < parent.pushDelay.size(); i++) {
                    for (int j = 0; j < parent.pushDelay.size(); j++) {
                        if (j != i) {
                            sum += Math.pow((parent.pushDelay.get(j) - expectations.get(i)), 2);
                        }
                    }
                    deviation = Math.pow((sum / (parent.pushDelay.size() - 2)), 0.5);

                    coefficient = Math.abs((parent.pushDelay.get(i) - expectations.get(i)) / deviation);

                    if (coefficient <= mainCoef) {
                        significantCoefs.add((double) parent.pushDelay.get(i));
                    }
                }

                double min = significantCoefs.get(0);
                double max = min;
                for (int i = 0; i < significantCoefs.size(); i++) {
                    if (parent.pushDelay.get(i) > max) {
                        max = parent.pushDelay.get(i);
                    }
                    if (parent.pushDelay.get(i) < min) {
                        min = parent.pushDelay.get(i);
                    }
                }

                double speed =  parent.msDelay;

                //--------------------Checking-----------------------------

                String minCoefficientForSpeed = writeFileIntoString("C:\\" + username + "\\MinCoefficientForSpeed.txt", "");
                String maxCoefficientForSpeed = writeFileIntoString("C:\\" + username + "\\MaxCoefficientForSpeed.txt", "");
                String minCoefficientForInterval = writeFileIntoString("C:\\" + username + "\\MinCoefficientForInterval.txt", "");
                String maxCoefficientForInterval = writeFileIntoString("C:\\" + username + "\\MaxCoefficientForInterval.txt", "");

                double minSpeed = Double.parseDouble(minCoefficientForSpeed);
                double maxSpeed = Double.parseDouble(maxCoefficientForSpeed);
                double minInterval = Double.parseDouble(minCoefficientForInterval);
                double maxInterval = Double.parseDouble(maxCoefficientForInterval);

                if ((speed >= minSpeed && speed <= maxSpeed) && (max <= maxInterval && min >= minInterval)) {
                    JOptionPane.showMessageDialog(null, "Hello" + username);
                } else {
                    JOptionPane.showMessageDialog(null, "You are not a " + username + ". Try again later!");
                }

                //---------------------------------------------------------

            }
        }
    }
}
