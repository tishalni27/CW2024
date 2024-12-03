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
    private static final String RETRY_BUTTON_NAME = "/com/example/demo/images/retryBtn.png";

    //pause buttons dimensions
    //private static final int BUTTON_HEIGHT=65;
    //private static final int BUTTON_WIDTH=65;
    private final HBox container;
    private final double containerXPosition;
    private final double containerYPosition;
    private final LevelParent levelParent;// reference to parent level when controlling pause and resume
    private Scene previousScene; //store previous scene to return when resuming game
    public PauseScreen(double xPosition, double yPosition, double stageWidth, double stageHeight, LevelParent levelParent) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.levelParent = levelParent;
        this.container = new HBox();
        initializeContainer(stageWidth, stageHeight);
    }

    private void initializeContainer(double stageWidth, double stageHeight) {
        // Set the container's position
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);

        // Create the pause button with dynamic size and position
        ImageView pauseButton = createPauseButton(stageWidth, stageHeight);

        // Add the pause button to the container
        container.getChildren().add(pauseButton);
    }

    //Pause Game
    private ImageView createPauseButton(double stageWidth, double stageHeight){
        Image pauseImage = new Image(getClass().getResource(PAUSE_BUTTON_NAME).toExternalForm());
        ImageView pauseButton = new ImageView(pauseImage);
        //Adjust pause button based on stage dimensions
        pauseButton.setFitHeight(stageWidth * 0.08);
        pauseButton.setFitWidth(stageHeight* 0.08);

        pauseButton.setPreserveRatio(true);

        //position to top left
        pauseButton.setTranslateX(stageWidth * 0.12);
        pauseButton.setTranslateY(stageHeight * 0.01);

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
            Stage stage =(Stage)pauseButton.getScene().getWindow();
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();
            Image pauseScreenImage = new Image(getClass().getResource(PAUSE_SCREEN_IMAGE).toExternalForm());

            //create imageview for pause screen
            ImageView pauseScreenView = new ImageView(pauseScreenImage);
            pauseScreenView.setPreserveRatio(true);
            pauseScreenView.setFitWidth(stageWidth);//set the width of the pause screen
            pauseScreenView.setFitHeight(stageHeight);// set height of pause screen

            ImageView resumeButton = createResumeButton(stageWidth,stageHeight);
            ImageView levelsButton = createLevelsButton(stageWidth,stageHeight);
            ImageView quitButton = createQuitButton(stageWidth,stageHeight);
            ImageView retryButton = createRetryButton(stageWidth,stageHeight);
            //created the StackPane to overlay pause screen image and buttons
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(pauseScreenView, resumeButton, levelsButton,quitButton,retryButton);

           //set alignment position for all the buttons in pause screen
            StackPane.setAlignment(resumeButton, javafx.geometry.Pos.CENTER);  // Center the resume button
            StackPane.setAlignment(levelsButton, javafx.geometry.Pos.CENTER_LEFT); // Align levels button to the left
            StackPane.setAlignment(quitButton, javafx.geometry.Pos.CENTER_RIGHT); // Align quit button to the right
            StackPane.setAlignment(retryButton, javafx.geometry.Pos.BOTTOM_CENTER);

            previousScene = stage.getScene();//save the previous scene
            //set the new scene with pause screen overlay
            Scene pauseScene = new Scene(stackPane,stageWidth,stageHeight);
            stage.setScene(pauseScene);
            stage.setFullScreen(true);


         }   catch (Exception ex){
                ex.printStackTrace();
                System.err.println("Failed to load Pause screen");//debug statement
             }
    }

    //RESUME
    private ImageView createResumeButton(double stageWidth, double stageHeight){
        Image resumeImage = new Image(getClass().getResource(RESUME_BUTTON_NAME).toExternalForm());
        ImageView resumeButton = new ImageView(resumeImage);
        // Adjust button size based on stage width and height
        resumeButton.setFitHeight(stageHeight * 0.8);
        resumeButton.setFitWidth(stageWidth * 0.25);
        resumeButton.setPreserveRatio(true);
        resumeButton.setOnMouseClicked(this::resumeGame);
        // Positioning relative to stage size (adjust X and Y to fit)
        resumeButton.setTranslateX(stageWidth * 0.13 - resumeButton.getFitWidth() / 2);  // Center horizontally
        resumeButton.setTranslateY(stageHeight * -0.10);  // Position near the middle vertically
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

    private ImageView createLevelsButton(double stageWidth, double stageHeight){
        Image levelsImage = new Image(getClass().getResource(LEVELS_BUTTON_NAME).toExternalForm());
        ImageView levelsButton = new ImageView(levelsImage);
        // Adjust button size based on stage width and height
        levelsButton.setFitHeight(stageHeight * 0.8);
        levelsButton.setFitWidth(stageWidth * 0.25);
        levelsButton.setPreserveRatio(true);
        // Button action for going to level selection screen
        levelsButton.setOnMouseClicked(this::goToLevelSelection);
        // Positioning relative to stage size (adjusting X and Y to fit)
        levelsButton.setTranslateX(stageWidth * 0.5 - levelsButton.getFitWidth() / 2);  // Center horizontally
        levelsButton.setTranslateY(stageHeight * 0.05); // Position below resume button

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

    private ImageView createQuitButton(double stageWidth, double stageHeight) {
        Image quitImage = new Image(getClass().getResource(QUIT_BUTTON_NAME).toExternalForm());
        ImageView quitButton = new ImageView(quitImage);
        // Adjust button size based on stage width and height
        quitButton.setFitHeight(stageHeight * 0.8);
        quitButton.setFitWidth(stageWidth * 0.25);
        quitButton.setPreserveRatio(true);
        // Button action for quitting the application
        quitButton.setOnMouseClicked(this::quitGame);
        // Positioning relative to stage size (adjust X and Y to fit)
        quitButton.setTranslateX(stageWidth * 0.07 - quitButton.getFitWidth() / 0.565);  // Center horizontally
        quitButton.setTranslateY(stageHeight * 0.17);//below levels button
        return quitButton;
    }

    private void quitGame(MouseEvent event){
        System.out.println("Quit button clicked");//debug statement
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
            Scene homeScreen = new Scene(loader.load(), 1300, 750);
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);  // Switch to the level selection screen
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load LevelChoose.fxml");
        }

    }

    private ImageView createRetryButton(double stageWidth, double stageHeight) {
        Image retryImage = new Image(getClass().getResource(RETRY_BUTTON_NAME).toExternalForm());
        ImageView retryButton = new ImageView(retryImage);

        // Adjust button size based on stage width and height
        retryButton.setFitHeight(stageHeight * 0.8);  // Button height 10% of screen height
        retryButton.setFitWidth(stageWidth * 0.25);    // Button width 20% of screen width
        retryButton.setPreserveRatio(true);

        // Button action for retrying the game (e.g., restarting the level)
        retryButton.setOnMouseClicked(this::retryGame);

        // Positioning relative to stage size (adjust X and Y to fit)
        retryButton.setTranslateX(stageWidth * 0.5 - retryButton.getFitWidth() / 0.504);  // Center horizontally
        retryButton.setTranslateY(stageHeight * 0.015); // Position below levels and quit buttons

        return retryButton;
    }

    private void retryGame(MouseEvent event){
        System.out.println("Retry button clicked");//debugging statement

        /*if(levelParent !=null){
            levelParent.retryGame();
        }

        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setFullScreen(true);*/
    }



    public HBox getContainer(){
        return container;
    }












}
