/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Maceina;
import edu.ktu.ds.lab2.gui.MA_MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
/**
 *
 * @author maceina
 */
public class TestExecution extends Application {

    public static void main(String[] args) {
        TestExecution.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
//        TestManual.executeTest();
        MA_MainWindow.createAndShowGui(primaryStage);
    }
}
