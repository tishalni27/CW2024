package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class UIController {


    private Stage stage;

    private GameController myController;

    private String gameLevel = "None";

    @FXML
    public void chooseLevel(ActionEvent event) {
        loadScene(event, "/LevelChoose.fxml");
    }

    @FXML
    public void startLevel1(ActionEvent event) {
        startLevel(event, "com.example.demo.Level1.LevelOne");
    }

    @FXML
    public void startLevel2(ActionEvent event) {
        startLevel(event, "com.example.demo.Level2.LevelTwo");
    }

    @FXML
    public void startLevel3(ActionEvent event) {
        startLevel(event, "com.example.demo.Level3.LevelThree");
    }

    @FXML
    public void unlimitedKills(ActionEvent event) {
        startLevel(event, "com.example.demo.LevelChallenge.Challenge");
    }

    @FXML
    public void howToPlay(ActionEvent event) {
        loadScene(event, "/GameRules.fxml");
    }

    @FXML
    public void homeScreen(ActionEvent event) {
        loadScene(event, "/HomeScreen.fxml");
    }

    @FXML
    public void exitGame(ActionEvent event) {
        // Display a confirmation alert before exiting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("You're about to exit the game.");
        alert.setContentText("Are you sure you want to quit?");

        // Ensure the alert is on top of the game screen
        Stage gameStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        alert.initOwner(gameStage);  // Set the alert's owner to the game stage

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                gameStage.close();
                System.out.println("Game exited successfully.");
            }
        });
    }

    // Load any scene (refactored to reduce redundancy)
    private void loadScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setFullScreen(true);

        } catch (IOException e) {
            showErrorAlert("Error loading " + fxmlPath + ": " + e.getMessage());
        }
    }

    // Refactor startLevel to reduce repetition
    private void startLevel(ActionEvent event, String levelClass) {
        System.out.println("Started " + levelClass);
        // gameLevel = levelClass;
        try {
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            myController = new GameController(stage);
            myController.launchGame(levelClass);
        } catch (Exception e) {
            showErrorAlert("Error starting game: " + e.getMessage());
        }
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getGameLevel() {
        return gameLevel != null ? gameLevel : "None"; // Ensure gameLevel never returns null
    }
}
