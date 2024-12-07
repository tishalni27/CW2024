package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;

public class Controller implements Observer {

	//private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Levels.LevelOne";
	private final Stage stage;
	private LevelParent currentLevel;
	private Parent root;
	//private Scene scene;

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
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			currentLevel.addObserver(this);
			Scene scene = currentLevel.initializeScene(stage.getWidth(), stage.getHeight());
			stage.setScene(scene);
			currentLevel.startGame();
			stage.setFullScreen(true);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
