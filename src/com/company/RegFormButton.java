package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 28.05.2017.
 */
public class RegFormButton implements ActionListener {

    RegGUI parent;
    List<Double> expectations = new LinkedList<>();
    List<Double> significantCoefs = new LinkedList<>();

    RegFormButton (RegGUI parent) {
        this.parent = parent;
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

            String text = parent.getText();
            for (int i = 1; i < parent.betweenPush.size(); i++) {
                parent.pushDelay.add(parent.betweenPush.get(i) - parent.betweenPush.get(i - 1));
            }

            if (!text.equals("кто не работает тот ест.")) {
                JOptionPane.showMessageDialog(null, "Incorrect input!");
                parent.setDisplayValue("");
            } else if (parent.pushDelay.size() != 23) {
                System.out.println(parent.pushDelay.size());
                JOptionPane.showMessageDialog(null, "Invalid input!");
            } else {
                parent.counter++;
                String username = parent.getUsername();
                File file = new File("C:\\" + username);
                File file1 = new File("C:\\" + username + "\\FullTimeMeasurements.txt");
                if (!file.exists()) {
                    file.mkdir();
                }

                if (!file1.exists()) {
                    create(file1);
                }

                //----------------------Write processed data----------------------

                getVector();

                //----------------------------------------------------------------

                writeMsDelay(file1);
                parent.setDisplayValue("");


                if (parent.counter == 10) {

                    double listMin = significantCoefs.get(0);
                    double listMax = listMin;

                    for (int i = 0; i < significantCoefs.size(); i++) {
                        if (significantCoefs.get(i) > listMax) {
                            listMax = significantCoefs.get(i);
                        }
                        if (significantCoefs.get(i) < listMin) {
                            listMin = significantCoefs.get(i);
                        }
                    }

                    File file4 = new File("C:\\" + username + "\\MinCoefficientForInterval.txt");
                    File file5 = new File("C:\\" + username + "\\MaxCoefficientForInterval.txt");
                    writeCoefficients(file4, file5, listMin, listMax);

                    //--------------------------------------------------------------

                    //List<Double> list =  processing.getFileContent("C:\\" + username + "\\FullTimeMeasurements.txt");
                    List<Double> temporaryList = new LinkedList<>();
                    List<Double> list = getListFromFile(username, temporaryList);
                    List<Double> speedList = new LinkedList<>();
                    for (int i = 0; i < list.size(); i++) {
                        speedList.add(24 / list.get(i));
                    }

                    //--------------------Speed data processing--------------------

                    List<Double> speedMathExpectation = speedList;
                    double expectation = 0;
                    double sum = 0;
                    double deviation;
                    double coefficient;
                    List<Double> vector = new LinkedList<>();

                    for (int j = 0; j < 10; j++) {
                        for (int i = 0; i < list.size(); i++) {
                            if (i != j) {
                                expectation += list.get(i);
                            }
                        }
                        speedMathExpectation.add(expectation / (list.size() - 1));
                        for (int i = 0; i < list.size(); i++) {
                            for (int k = 0; k < list.size(); k++) {
                                if (k != i) {
                                    sum += Math.pow((list.get(k) - speedMathExpectation.get(j)), 2);
                                }
                            }
                        }
                        deviation = Math.pow((sum / (list.size() - 2)), 0.5);
                        coefficient = Math.abs((list.get(j) - speedMathExpectation.get(j)) / deviation);
                        if (coefficient <= parent.speedCoef) {
                            vector.add(list.get(j));
                        }
                    }

                    //-----------------------Get max/min values--------------------

                    double min = vector.get(0);
                    double max = min;
                    for (int i = 0; i < vector.size(); i++) {
                        if (list.get(i) > max) {
                            max = list.get(i);
                        }
                        if (list.get(i) < min) {
                            min = list.get(i);
                        }
                    }

                    File file2 = new File("C:\\" + username + "\\MinCoefficientForSpeed.txt");
                    File file3 = new File("C:\\" + username + "\\MaxCoefficientForSpeed.txt");
                    writeCoefficients(file2, file3, min, max);

                    //-------------------------------------------------------------

                    JOptionPane.showMessageDialog(null, "All attempts are spent!");
                }
            }
    }

    private void create(File file) {
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void writeMsDelay(File file) {
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(parent.msDelay + ",");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void writeCoefficients(File file, File file1, double min, double max) {
        try {
            FileWriter fileWriter3 = new FileWriter(file, true);
            FileWriter fileWriter4 = new FileWriter(file1, true);
            fileWriter3.write(min + "");
            fileWriter3.flush();
            fileWriter4.write(max + "");
            fileWriter4.flush();
            fileWriter3.close();
            fileWriter4.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private List<Double> getListFromFile(String username, List<Double> list) {
        try {
            list = getFileContent("C:\\" + username + "\\FullTimeMeasurements.txt");
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    List<Double> getFileContent(String filepath) throws Exception{

        StringBuilder builder = new StringBuilder();
        FileInputStream inputStream = new FileInputStream(filepath);
        InputStreamReader streamReader = new InputStreamReader(inputStream, "Windows-1251");
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) {builder.append(currentLine);}
        String result = builder.toString();
        List<String> list =  Arrays.asList(result.split("\\s*,\\s*"));
        List<Double> res = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            res.add(Double.parseDouble(str));
        }
        return res;
    }

    void getMathematicalExpectation() {

        if (expectations.size() > 0) {
            expectations.clear();
        }

        List<Long> list = new LinkedList<>();
        list.addAll(parent.pushDelay);

        double expectation = 0;

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    expectation += list.get(j);
                }
            }
            expectation /= (list.size() - 1);
            expectations.add(expectation);
        }
    }

    void getVector() {

        getMathematicalExpectation();

        List<Long> list = new LinkedList<>();
        list.addAll(parent.pushDelay);
        if (significantCoefs.size() > 0) {
            significantCoefs.clear();
        }
        double sum = 0;
        double deviation;
        double coefficient;

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (j != i) {
                    sum += Math.pow((list.get(j) - expectations.get(i)), 2);
                }
            }
            deviation = Math.pow((sum / (list.size() - 2)), 0.5);

            coefficient = Math.abs((list.get(i) - expectations.get(i)) / deviation);
            if (coefficient <= parent.mainCoef) {
                significantCoefs.add((double) list.get(i));
            }
        }
    }
}
