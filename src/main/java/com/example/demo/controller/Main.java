package com.example.demo.controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class for launching the JavaFX application.
 * This class sets up the primary stage, loads the FXML for the home screen,
 * and configures the stage's size and properties.
 */
public class Main extends Application {

	/**
	 * Starts the JavaFX application.
	 * This method is automatically called by the JavaFX framework when the application starts.
	 * It sets up the primary stage, loads the FXML file, and configures the scene and stage properties.
	 *
	 * @param stage the primary stage for this application
	 */
	@Override
	public void start(Stage stage) {
		try {
			// Remove the window bar (undecorated window)
			stage.initStyle(StageStyle.UNDECORATED);

			// Load the HomeScreen FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
			Parent root = loader.load();

			// Create a new scene and set it to the stage
			Scene scene = new Scene(root);

			// Get the screen dimensions
			Screen screen = Screen.getPrimary();
			double screenWidth = screen.getVisualBounds().getWidth();
			double screenHeight = screen.getVisualBounds().getHeight();

			// Set stage dimensions to fill the screen
			stage.setWidth(screenWidth);
			stage.setHeight(screenHeight);

			// Set the scene to the stage
			stage.setScene(scene);
			stage.setFullScreen(true);

			// Adjust the AnchorPane's size dynamically with the scene
			if (root instanceof AnchorPane) {
				AnchorPane anchorPane = (AnchorPane) root;
				// Bind AnchorPane's width and height properties to the scene's width and height
				anchorPane.prefWidthProperty().bind(scene.widthProperty());
				anchorPane.prefHeightProperty().bind(scene.heightProperty());
			}

			// Set an empty full screen exit hint (removes default exit hint)
			stage.setFullScreenExitHint("");

			// Show the stage (application window)
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();  // Print the exception if loading the FXML fails
		}
	}

	/**
	 * The main method that launches the JavaFX application.
	 * This is the entry point of the JavaFX application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		launch();  // Launch the application
	}
}
