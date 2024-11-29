package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PauseScreen {
    //images
    private static final String PAUSE_BUTTON_NAME ="/com/example/demo/images/pauseBtn.png";
    private static final String PAUSE_SCREEN_IMAGE ="/com/example/demo/images/pauseScreen.png";

    //buttons
    private static final int BUTTON_HEIGHT=65;
    private static final int BUTTON_WIDTH=65;
    private final HBox container;
    private final double containerXPosition;
    private final double containerYPosition;
    private final LevelParent levelParent;// reference to parent level when controlling pause and resume
    private Scene previousScene; //store previous scene to return when resuming game
    public PauseScreen(double xPosition, double yPosition, LevelParent levelParent){
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.levelParent = levelParent;
        this.container = new HBox();
        initializeContainer();

    }

    private void initializeContainer(){
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
        //create the pause button and add to the container
        ImageView pauseButton = createPauseButton();
        container.getChildren().add(pauseButton);//add pausebutton to container
    }

    //Pause Game
    private ImageView createPauseButton(){
        Image pauseImage = new Image(getClass().getResource(PAUSE_BUTTON_NAME).toExternalForm());
        ImageView pauseButton = new ImageView(pauseImage);
        pauseButton.setFitHeight(BUTTON_HEIGHT);
        pauseButton.setFitWidth(BUTTON_WIDTH);
        pauseButton.setPreserveRatio(true);
        pauseButton.setOnMouseClicked(e->{
            System.out.println("Pause button clicked");//debug statement
            if(levelParent!=null) {
                levelParent.pauseGame();
            }
            showPauseScreen(pauseButton);

        });

        return pauseButton;

    }

    private void showPauseScreen(ImageView pauseButton) {
    try {
        Image pauseScreenImage = new Image(getClass().getResource(PAUSE_SCREEN_IMAGE).toExternalForm());
        ImageView pauseScreenView = new ImageView(pauseScreenImage);
        pauseScreenView.setFitWidth(1350);//set the width of the pause screen
        pauseScreenView.setFitHeight(800);// set height of pause screen

        //created the StackPane to overlay pause screen image and buttons
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(pauseScreenView);

        //store previous scene before replacing
        Stage stage = (Stage) pauseButton.getScene().getWindow();
        previousScene = stage.getScene();//save the previous scene
        //set the new scene with pause screen overlay
        Scene pauseScene = new Scene(stackPane, 1350, 800);
        stage.setScene(pauseScene);
    } catch (Exception ex){
        ex.printStackTrace();
        System.err.println("Failed to load Pause screen");//debug statement
    }
    }


    public HBox getContainer(){
        return container;
    }












}