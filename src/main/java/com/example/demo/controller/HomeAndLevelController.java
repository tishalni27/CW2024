package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeAndLevelController {

    @FXML
    public Label titleLabel;
    @FXML
    public Button startButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Controller myController;

    @FXML
    public void chooseLevel (ActionEvent event) throws IOException {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/LevelChoose.fxml"));
           Parent root = loader.load();
           stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
           stage.setFullScreen(true);
           System.out.println("Game Started");
       } catch (IOException e){
           showErrorAlert("Error loading LevelChoose.fxml" + e.getMessage());
       }
    }

    @FXML
    public void startLevel1(ActionEvent event){
        System.out.println("Started Level 1");
        try{
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            myController = new Controller (stage);
            myController.launchGame("com.example.demo.LevelOne");
        } catch (Exception e){
            showErrorAlert("Error starting game:" + e.getMessage());
        }

    }

    @FXML
    public void startLevel2(ActionEvent event){
        System.out.println("Started Level 2");
        try{
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            myController = new Controller (stage);
            myController.launchGame("com.example.demo.LevelTwo");
        } catch (Exception e){
            showErrorAlert("Error starting game:" + e.getMessage());
        }

    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
