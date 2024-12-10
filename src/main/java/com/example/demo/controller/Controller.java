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

public class Controller implements Observer {

	//private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Levels.LevelOne";
	private final Stage stage;
	private LevelParent currentLevel;
	private Parent root;
	//private Scene scene;
	private String gameLevel;

	public Controller(Stage stage) {

		this.stage = stage;
	}

	public void launchGame(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
			stage.setFullScreen(true);
			stage.show();
			try {
				goToLevel(className);
			} catch (Exception e){
				showErrorAlert(" Error launching game:" + e.getMessage());
			}
	}

	private void showErrorAlert(String s) {
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

			if (currentLevel !=null){
				currentLevel.stopGame();
			}

			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Controller.class);
			currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(),this);
			currentLevel.addObserver(this);
			Scene scene = currentLevel.initializeScene(stage.getWidth(), stage.getHeight());
			stage.setScene(scene);
			currentLevel.startGame();
			stage.setFullScreen(true);

			//setter for GameLevel
		    setGameLevel(className);

	}

	@Override
	public void update(Observable o, Object arg1) {
		if (arg1 instanceof String) {
			String message = (String) arg1; //cast argument to string
			if ("loseGame".equals(message)) {
				// When the message is "loseGame", execute the method to show the retry button
				showRetryButton();
			}


			try {
				goToLevel(message);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException |
					 IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getClass().toString());
				alert.show();
			}
		}
	}

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
	public void retryLevel() {
		if (currentLevel != null) {
			try {
				launchGame(gameLevel);
			} catch (Exception e) {
				showErrorAlert("Retry failed: " + e.getMessage());
			}
		}
	}
	public void setGameLevel(String levelName) {
		this.gameLevel = levelName;
		System.out.println("Game level set to: " + gameLevel);
	}


}
