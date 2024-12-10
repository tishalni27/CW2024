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
            myController.launchGame("com.example.demo.Levels.LevelOne");
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
            myController.launchGame("com.example.demo.Levels.LevelTwo");
        } catch (Exception e){
            showErrorAlert("Error starting game:" + e.getMessage());
        }

    }

    @FXML
    public void startLevel3(ActionEvent event){
        System.out.println("Started Level 3");
        try{
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            myController = new Controller (stage);
            myController.launchGame("com.example.demo.Levels.LevelThree");
        } catch (Exception e){
            showErrorAlert("Error starting game:" + e.getMessage());
        }

    }


    @FXML
    public void exitGame(ActionEvent event){
        //Display confirmation before exiting game
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("You are about to exit the game");
        alert.setContentText("Are you sure you want to quit?");

        //make sure alert is on top of game screen
        Stage gameStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        alert.initOwner(gameStage);  // Set the alert's owner to the game stage

        // Use a non-blocking dialog to prevent issues with modality
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // Close the application
                gameStage.close();
                System.out.println("Game exited successfully.");
            }
        });
    }

        @FXML
        public void homeScreen(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
            Parent root = loader.load();

            //get current stage and set new stage
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);  // Create a new scene with the loaded FXML
            stage.setScene(scene);  // Set the scene for the current stage
            stage.show();
            stage.setFullScreen(true);
        }catch (IOException e){
            showErrorAlert("Error loading HomeScreen:" + e.getMessage());
            //debugging statement
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
