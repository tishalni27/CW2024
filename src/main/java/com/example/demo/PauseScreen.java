package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PauseScreen {
    //images
    private static final String PAUSE_BUTTON_NAME ="/com/example/demo/images/pauseBtn.png";
    private static final String PAUSE_SCREEN_IMAGE ="/com/example/demo/images/pauseScreen.png";
    private static final String RESUME_BUTTON_NAME="/com/example/demo/images/resumeBtn.png";
    private static final String LEVELS_BUTTON_NAME = "/com/example/demo/images/levelsBtn.png";
    private static final String QUIT_BUTTON_NAME = "/com/example/demo/images/quitBtn.png";

    //pause buttons dimensions
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

            ImageView resumeButton = createResumeButton();
            ImageView levelsButton = createLevelsButton();
            ImageView quitButton = createQuitButton();
            //created the StackPane to overlay pause screen image and buttons
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(pauseScreenView, resumeButton, levelsButton,quitButton);
            StackPane.setAlignment(resumeButton,null);

            //manually adjusting the position of the buttons
            resumeButton.setTranslateY(2);
            resumeButton.setTranslateY(-98);

            levelsButton.setTranslateX(-2);
            levelsButton.setTranslateY(25);

            quitButton.setTranslateX(-2);
            quitButton.setTranslateY(127);

            //store previous scene before replacing
            Stage stage = (Stage) pauseButton.getScene().getWindow();
            previousScene = stage.getScene();//save the previous scene
            //set the new scene with pause screen overlay
            Scene pauseScene = new Scene(stackPane, 1350, 800);
            stage.setScene(pauseScene);
         }   catch (Exception ex){
                ex.printStackTrace();
                System.err.println("Failed to load Pause screen");//debug statement
             }
    }

    //RESUME
    private ImageView createResumeButton(){
        Image resumeImage = new Image(getClass().getResource(RESUME_BUTTON_NAME).toExternalForm());
        ImageView resumeButton = new ImageView(resumeImage);
        resumeButton.setFitHeight(350);
        resumeButton.setFitWidth(350);
        resumeButton.setPreserveRatio(true);
        resumeButton.setOnMouseClicked(this::resumeGame);
        return resumeButton;
    }

    private void resumeGame(MouseEvent event){
        System.out.println("Resume button clicked");//debug
        if(levelParent !=null){
            levelParent.resumeGame();
        }
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setScene(previousScene);  // Set the previous scene (game screen)

    }

    private ImageView createLevelsButton(){
        Image levelsImage = new Image(getClass().getResource(LEVELS_BUTTON_NAME).toExternalForm());
        ImageView levelsButton = new ImageView(levelsImage);
        levelsButton.setFitHeight(350);
        levelsButton.setFitWidth(350);
        levelsButton.setPreserveRatio(true);
        // Button action for going to level selection screen
        levelsButton.setOnMouseClicked(this::goToLevelSelection);
        return levelsButton;

    }

    private void goToLevelSelection(MouseEvent event){
        System.out.println("Levels button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LevelChoose.fxml"));
            Scene levelSelectScene = new Scene(loader.load(), 1300, 750);  // Adjust the scene size as needed
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(levelSelectScene);  // Switch to the level selection screen
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load LevelChoose.fxml");
        }




    }

    private ImageView createQuitButton() {
        Image quitImage = new Image(getClass().getResource(QUIT_BUTTON_NAME).toExternalForm());
        ImageView quitButton = new ImageView(quitImage);
        quitButton.setFitHeight(350);
        quitButton.setFitWidth(350);
        quitButton.setPreserveRatio(true);
        // Button action for quitting the application
        quitButton.setOnMouseClicked(this::quitGame);
        return quitButton;
    }

    private void quitGame(MouseEvent event){
        System.out.println("Quit button clicked");//debug statement
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
            Scene homeScreen = new Scene(loader.load(), 1300, 750);  // Adjust the scene size as needed
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);  // Switch to the level selection screen
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load LevelChoose.fxml");
        }

    }



    public HBox getContainer(){
        return container;
    }












}
