package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;
/**
 * The {@code GameController} class is responsible for managing the game flow,
 * transitioning between levels, and handling game events such as starting a level,
 * retrying a level, and showing error alerts. This class implements the {@link Observer}
 * interface to receive updates from the current game level.
 */
public class GameController implements Observer {

	private final Stage stage;
	private LevelParent currentLevel;
	private Parent root;
	private String gameLevel;
	/**
	 * Constructs a new GameController for managing and controlling the game level.
	 *
	 * @param stage the primary Stage for the game, used to display the game scenes
	 */
	public GameController(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by initializing and transitioning to the specified level.
	 *
	 * @param className the class name of the level to load
	 * @throws ClassNotFoundException if the class is not found
	 * @throws NoSuchMethodException if the constructor is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the class cannot be instantiated
	 * @throws IllegalAccessException if access to the constructor is denied
	 * @throws IllegalArgumentException if illegal arguments are provided
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	public void launchGame(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		stage.setFullScreen(true);
		stage.show();

		try {
			goToLevel(className);
		} catch (Exception e) {
			showErrorAlert("Error launching game: " + e.getMessage());
		}
	}

	/**
	 * Displays an error alert with the provided message.
	 *
	 * @param message the error message to display
	 */
	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
	}

	/**
	 * Transitions to the specified level by loading the class dynamically.
	 *
	 * @param className the class name of the level
	 * @throws ClassNotFoundException if the class is not found
	 * @throws NoSuchMethodException if the constructor is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the class cannot be instantiated
	 * @throws IllegalAccessException if access to the constructor is denied
	 * @throws IllegalArgumentException if illegal arguments are provided
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if (currentLevel != null) {
			currentLevel.stopGame(); // Stop the current level if one exists
		}

		// Dynamically load the level class and initialize it
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class, GameController.class);
		currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), this);

		// Add this GameController as an observer of the current level
		currentLevel.addObserver(this);

		// Initialize and set the scene for the level
		Scene scene = currentLevel.initializeScene(stage.getWidth(), stage.getHeight());
		stage.setScene(scene);

		// Start the level
		currentLevel.startGame();
		stage.setFullScreen(true);

		// Set the game level name
		setGameLevel(className);
	}

	/**
	 * Updates the game state based on the observed message.
	 *
	 * @param o the observable object
	 * @param arg the argument passed from the observed object
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String message = (String) arg;

			if ("loseGame".equals(message)) {
				// Show the retry button when the game is lost
				showRetryButton();
			}

			// Try to go to the next level based on the message
			try {
				goToLevel(message);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException |
					 IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				showErrorAlert(e.getClass().toString());
			}
		}
	}

	/**
	 * Displays the retry button on the current scene.
	 */
	public void showRetryButton() {
		Scene scene = stage.getScene();
		if (scene != null) {
			if (scene.getRoot() instanceof VBox layout) {
				Button retryButton = new Button("Retry Level");
				retryButton.setOnAction(event -> retryLevel());
				layout.getChildren().add(retryButton);
			} else {
				System.err.println("Root node is not a VBox. Retry button cannot be added.");
			}
		}
	}

	/**
	 * Retries the current level by launching it again.
	 */
	public void retryLevel() {
		if (currentLevel != null) {
			try {
				launchGame(gameLevel); // Retry the game with the current game level
			} catch (Exception e) {
				showErrorAlert("Retry failed: " + e.getMessage());
			}
		}
	}

	/**
	 * Sets the current game level.
	 *
	 * @param levelName the name of the level to set
	 */
	public void setGameLevel(String levelName) {
		this.gameLevel = levelName;
		System.out.println("Game level set to: " + gameLevel);
	}
}
