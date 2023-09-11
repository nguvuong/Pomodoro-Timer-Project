package com.example;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HistoryWindowController {

    @FXML
    private Button homeButton;
    private Stage stage;
    private Scene scene;
    @FXML
    private Label nameLabel;

    public void switchToMainWindow(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    String result = "";

    public void displayTasks(ArrayList<Task> DoneList) {
        for (int i = 0; i < DoneList.size(); i++) {
            result += DoneList.get(i).toString() + i + ": " + DoneList.get(i).getName() + " studyHours="
                    + DoneList.get(i).getTimeStudying() + "\n";
            ;
        }
        nameLabel.setText(result);
    }
}
