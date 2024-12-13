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
/**
 * Class to control all UI features before the game
 */
public class UIController {

    // Instance variables
    private Stage stage;
    private GameController myController;
    private String gameLevel = "None";

    /**
     * Load a scene based on the provided FXML file path.
     *
     * @param event the ActionEvent triggered by the button press
     * @param fxmlPath the path to the FXML file to load
     */
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

    /**
     * Start the level based on the class name.
     *
     * @param event the ActionEvent triggered by the button press
     * @param levelClass the fully qualified class name of the level to start
     */
    private void startLevel(ActionEvent event, String levelClass) {
        System.out.println("Started " + levelClass);
        try {
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            myController = new GameController(stage);
            myController.launchGame(levelClass);
        } catch (Exception e) {
            showErrorAlert("Error starting game: " + e.getMessage());
        }
    }

    /**
     * Show an error alert with a given message.
     *
     * @param message the error message to display
     */
    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Return the current game level.
     *
     * @return the current game level, or "None" if not set
     */
    public String getGameLevel() {
        return gameLevel != null ? gameLevel : "None"; // Ensure gameLevel never returns null
    }

    // FXML ActionEvent methods

    /**
     * Action handler for choosing a level.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void chooseLevel(ActionEvent event) {
        loadScene(event, "/LevelChoose.fxml");
    }

    /**
     * Action handler for starting Level 1.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void startLevel1(ActionEvent event) {
        startLevel(event, "com.example.demo.Level1.LevelOne");
    }

    /**
     * Action handler for starting Level 2.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void startLevel2(ActionEvent event) {
        startLevel(event, "com.example.demo.Level2.LevelTwo");
    }

    /**
     * Action handler for starting Level 3.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void startLevel3(ActionEvent event) {
        startLevel(event, "com.example.demo.Level3.LevelThree");
    }

    /**
     * Action handler for starting the Unlimited Kills challenge.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void unlimitedKills(ActionEvent event) {
        startLevel(event, "com.example.demo.LevelChallenge.Challenge");
    }

    /**
     * Action handler for displaying the How to Play screen.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void howToPlay(ActionEvent event) {
        loadScene(event, "/GameRules.fxml");
    }

    /**
     * Action handler for navigating back to the Home screen.
     *
     * @param event the ActionEvent triggered by the button press
     */
    @FXML
    public void homeScreen(ActionEvent event) {
        loadScene(event, "/HomeScreen.fxml");
    }

    /**
     * Action handler for exiting the game with a confirmation prompt.
     *
     * @param event the ActionEvent triggered by the button press
     */
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
}
