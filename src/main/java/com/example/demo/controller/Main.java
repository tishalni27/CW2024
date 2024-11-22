package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
//Main class
	private static final int SCREEN_WIDTH = 1200;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky ";
	//private Controller myController;

	@Override
	public void start(Stage stage)  {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene (root, SCREEN_WIDTH, SCREEN_HEIGHT);
			stage.setScene(scene);

			stage.setTitle(TITLE);
			stage.setResizable(false);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch();
	}
}