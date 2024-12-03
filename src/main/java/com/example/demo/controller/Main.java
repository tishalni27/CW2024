package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
//Main class


	@Override
	public void start(Stage stage)  {
		try{
			//to remove window bar
			stage.initStyle(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene (root);
			Screen screen = Screen.getPrimary();
			double screenWidth =screen.getVisualBounds().getWidth();
			double screenHeight = screen.getVisualBounds().getHeight();

			stage.setWidth(screenWidth);
			stage.setHeight(screenHeight);

			//set scene to stage
			stage.setScene(scene);
			stage.setFullScreen(true);

			if(root instanceof AnchorPane){
				AnchorPane anchorPane=(AnchorPane) root;
				// Bind AnchorPane's width and height properties to scene's width and height
				anchorPane.prefWidthProperty().bind(scene.widthProperty());
				anchorPane.prefHeightProperty().bind(scene.heightProperty());
			}
			stage.setFullScreenExitHint("");
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch();
	}
}