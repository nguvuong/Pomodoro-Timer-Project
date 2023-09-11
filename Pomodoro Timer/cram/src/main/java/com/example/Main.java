package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO Auto-generated method stub
        try {
            primaryStage.setTitle("Pomodoro Timer");
            // set icon for the app
            Image icon = new Image("AdobeStock_554175244_Preview.jpeg");
            primaryStage.getIcons().add(icon);

            Parent root = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("resource exception");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
