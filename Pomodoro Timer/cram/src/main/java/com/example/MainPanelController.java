package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainPanelController implements Initializable {

    // to store new task
    ArrayList<Task> tasksList = new ArrayList<>();

    @FXML
    private Button cancelButton;

    // to take input from combobox
    @FXML
    private ComboBox<Integer> hoursInput;
    @FXML
    private ComboBox<Integer> minutesInput;
    @FXML
    private ComboBox<Integer> secondsInput;

    @FXML
    private Text hoursTimer;

    @FXML
    private Text minutesTimer;

    @FXML
    private Text secondsTimer;

    // these two pane are used in stackpane and parallel transition
    @FXML
    private AnchorPane timerPane;
    @FXML
    private AnchorPane menuPane;

    @FXML
    private Button addButton;

    // Sound variable
    @FXML
    private ToggleGroup Sound;
    @FXML
    private ToggleButton fireSound;
    @FXML
    private ToggleButton lofiSound;
    @FXML
    private ToggleButton rainSound;

    // not implemented yet
    @FXML
    private ProgressBar timeProgressBar;

    @FXML
    private ToggleButton alarmButton;

    @FXML
    private Label notification;

    @FXML
    private Button doneButton;

    ObservableList<String> taskList = FXCollections.observableArrayList();

    @FXML
    private Button buttonStart;

    Map<Integer, String> numberMap;
    Integer currSeconds;
    Thread thrd;

    // alarm
    private File alarm;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean alarmStatus = false;

    // rain
    private File rainPath = new File("rainsound.mp3");
    private Media rain = new Media(rainPath.toURI().toString());
    private MediaPlayer rainPlayer = new MediaPlayer(rain);

    // lofi
    private File lofiPath = new File("lofisound.mp3");
    private Media lofi = new Media(lofiPath.toURI().toString());
    private MediaPlayer lofiPlayer = new MediaPlayer(lofi);

    // fire
    private File firePath = new File("firesound.mp3");
    private Media fire = new Media(firePath.toURI().toString());
    private MediaPlayer firePlayer = new MediaPlayer(fire);

    Integer hmsToSeconds(Integer h, Integer m, Integer s) {
        Integer hToSeconds = h * 3600;
        Integer mToSeconds = m * 60;
        Integer total = hToSeconds + mToSeconds + s;
        return total;
    }

    LinkedList<Integer> secondsToHms(Integer currSecond) {
        Integer hours = currSecond / 3600;
        currSecond = currSecond % 3600;
        Integer minutes = currSecond / 60;
        currSecond = currSecond % 60;
        Integer seconds = currSecond;
        LinkedList<Integer> answer = new LinkedList<>();
        answer.add(hours);
        answer.add(minutes);
        answer.add(seconds);
        return answer;
    }

    Integer studySecconds;

    @FXML
    void start(ActionEvent event) {
        if (taskInput.getValue() != null &&
                !taskInput.getValue().toString().isEmpty()) {
            alarm = new File("clock-alarm-8761.mp3");

            media = new Media(alarm.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            currSeconds = hmsToSeconds(hoursInput.getValue(), minutesInput.getValue(), secondsInput.getValue());
            studySecconds = hmsToSeconds(hoursInput.getValue(), minutesInput.getValue(), secondsInput.getValue());
            hoursInput.setValue(0);
            minutesInput.setValue(0);
            secondsInput.setValue(0);
            scrollUp();
            if (alarmButton.isSelected()) {
                alarmStatus = true;
            } else {
                alarmStatus = false;

            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You have not selected a task!");
            alert.showAndWait();
        }

    }

    boolean check = false;

    void startCountdown() {

        thrd = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // countdown here
                        setOutput();
                        Thread.sleep(1000);
                        if (currSeconds == 0) {

                            for (int i = 0; i < tasksList.size(); i++) {
                                if (tasksList.get(i).getName().equals(taskInput.getValue())) {
                                    tasksList.get(i).setCurrSeconds(tasksList.get(i).getCurrSeconds() + studySecconds);
                                    LinkedList<Integer> currStudyHms = secondsToHms(tasksList.get(i).getCurrSeconds());
                                    tasksList.get(i).setTimeStudying(numberMap.get(currStudyHms.get(0)) + "h " +
                                            numberMap.get(currStudyHms.get(1)) + "m "
                                            + numberMap.get(currStudyHms.get(2)) + "s ");

                                }
                            }
                            if (alarmStatus == true) {
                                mediaPlayer.play();
                            }
                            System.out.println("Finished");
                            scrollDown();
                            thrd.stop();
                        }
                        currSeconds -= 1;

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("ERROR");
                }
            }
        });
        thrd.start();

    }

    void setOutput() {
        LinkedList<Integer> currHms = secondsToHms(currSeconds);
        hoursTimer.setText(numberMap.get(currHms.get(0)));
        minutesTimer.setText(numberMap.get(currHms.get(1)));
        secondsTimer.setText(numberMap.get(currHms.get(2)));

    }

    @FXML
    void unStart(ActionEvent event) {
        try {
            currSeconds = 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    void scrollUp() {

        Duration duration = new Duration(100);
        TranslateTransition trl1 = new TranslateTransition();
        trl1.setDuration(duration);
        trl1.setNode(menuPane);

        // set transition
        trl1.setToX(0);
        trl1.setToY(-324);

        TranslateTransition trl2 = new TranslateTransition();
        trl2.setDuration(duration);
        trl2.setNode(timerPane);

        // set transition
        trl2.setFromX(0);
        trl2.setFromY(324);
        trl2.setToX(0);
        trl2.setToY(0);

        ParallelTransition pt = new ParallelTransition(trl1, trl2);

        pt.play();
        pt.setOnFinished(e -> {
            try {
                System.out.println("Start CountDown...");
                startCountdown();
            } catch (Exception e2) {
                // TODO: handle exception
            }
        });

    }

    void scrollDown() {
        TranslateTransition trl1 = new TranslateTransition();
        trl1.setDuration(Duration.millis(100));
        trl1.setToX(0);
        trl1.setToY(-324);
        trl1.setNode(timerPane);
        TranslateTransition trl2 = new TranslateTransition();
        trl2.setDuration(Duration.millis(100));
        trl2.setFromX(0);
        trl2.setFromY(324);
        trl2.setToX(0);
        trl2.setToY(0);
        trl2.setNode(menuPane);
        ParallelTransition pt = new ParallelTransition(trl1, trl2);

        pt.play();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // TODO Auto-generated method stub
        ObservableList<Integer> hoursList = FXCollections.observableArrayList();
        ObservableList<Integer> minutesAndSecondsLists = FXCollections.observableArrayList();
        // taskList.getItems().addAll("Task1", "Task2", "Task3");

        for (int i = 0; i <= 60; i++) {
            if (0 <= i && i <= 24) {
                hoursList.add(new Integer(i));
            }
            minutesAndSecondsLists.add(new Integer(i));
        }
        hoursInput.setItems(hoursList);
        hoursInput.setValue(0);

        minutesInput.setItems(minutesAndSecondsLists);
        minutesInput.setValue(0);

        secondsInput.setItems(minutesAndSecondsLists);
        secondsInput.setValue(0);

        numberMap = new TreeMap<Integer, String>();
        for (Integer i = 0; i <= 60; i++) {
            if (0 <= i && i <= 9) {
                numberMap.put(i, "0" + i.toString());
            } else {
                numberMap.put(i, i.toString());
            }
        }

    }

    int index = 0;

    @FXML
    private ComboBox<String> taskInput;

    @FXML
    void add(ActionEvent event) {

        if (taskInput.getValue() != null &&
                !taskInput.getValue().toString().isEmpty()) {

            tasksList.add(new Task(taskInput.getValue(), index));
            taskInput.getItems().add(tasksList.get(index).getName());
            notification.setText("Your task was successfully added");
            index++;
            taskInput.setValue(null);

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You have not entered a task!");
            alert.showAndWait();
        }
    }

    ArrayList<Task> DoneLists = new ArrayList<>();

    int index1 = 0;
    String result;

    @FXML
    void done(ActionEvent event) {

        if (taskInput.getValue() != null &&
                !taskInput.getValue().toString().isEmpty()) {
            for (int i = 0; i < tasksList.size(); i++) {
                if (tasksList.get(i).getName().equals(taskInput.getValue())) {
                    taskInput.getItems().remove(tasksList.get(i).getName());
                    DoneLists.add(index1, tasksList.remove(i));
                    index1++;
                    notification.setText("Finish a task, congrats!");

                }
            }

            taskInput.setValue(null);

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You have not selected a task!");
            alert.showAndWait();
        }

    }

    @FXML
    void tgAlarm(ActionEvent event) {

    }

    @FXML
    void tgButton(ActionEvent event) {

    }

    @FXML
    void displayHand(MouseEvent event) {
        buttonStart.setCursor(Cursor.HAND);
        alarmButton.setCursor(Cursor.HAND);
    }

    // function for toggle group sound button
    @FXML
    void toggleSound(ActionEvent event) {
        if (lofiSound.isSelected()) {
            lofiPlayer.play();

        }
        if (!(lofiSound.isSelected())) {
            lofiPlayer.stop();
        }
        if (rainSound.isSelected()) {
            rainPlayer.play();

        }
        if (!(rainSound.isSelected())) {
            rainPlayer.stop();
        }

        if (fireSound.isSelected()) {
            firePlayer.play();

        }
        if (!(fireSound.isSelected())) {
            firePlayer.stop();
        }

    }

    // variables to switch scene
    @FXML
    private Button HistoryButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

    // method to switch scene as well as send data to history scene
    @FXML
    void switchToHistoryWindow(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("History.fxml"));
        root = loader.load();

        // communicate between scene
        HistoryWindowController historyWindowController = loader.getController();
        historyWindowController.displayTasks(DoneLists);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
