package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
//Main class
	private static final int SCREEN_WIDTH = 1200;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky ";
	//private Controller myController;

	@Override
	public void start(Stage stage)  {
		try{
			//to remove window bar
			stage.initStyle(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene (root, SCREEN_WIDTH, SCREEN_HEIGHT);
			stage.setScene(scene);

			stage.setTitle(TITLE);
			stage.setFullScreen(true);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch();
	}
}