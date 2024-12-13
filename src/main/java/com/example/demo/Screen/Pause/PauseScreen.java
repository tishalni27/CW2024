package com.example.demo.Screen.Pause;

import com.example.demo.Levels.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The PauseScreen class provides functionality for managing the pause screen during the game.
 * It handles the display of the pause screen and the actions associated with various buttons such as resume,
 * levels, quit, and retry.
 */
public class PauseScreen {

    // Paths to images used for buttons and background
    private static final String PAUSE_BUTTON_NAME = "/com/example/demo/images/pauseBtn.png";
    private static final String PAUSE_SCREEN_IMAGE = "/com/example/demo/images/pauseScreen.png";
    private static final String RESUME_BUTTON_NAME = "/com/example/demo/images/resumeBtn.png";
    private static final String LEVELS_BUTTON_NAME = "/com/example/demo/images/levelsBtn.png";
    private static final String QUIT_BUTTON_NAME = "/com/example/demo/images/quitBtn.png";
    private static final String RETRY_BUTTON_NAME = "/com/example/demo/images/retryBtn.png";

    // UI elements
    private final HBox container;
    private final double containerXPosition;
    private final double containerYPosition;
    private final LevelParent levelParent; // Reference to parent level to control game pause and resume
    private Scene previousScene; // Stores the previous scene to return to when resuming the game

    /**
     * Constructor for initializing the PauseScreen.
     *
     * @param xPosition Horizontal position of the pause screen container
     * @param yPosition Vertical position of the pause screen container
     * @param stageWidth Width of the game stage
     * @param stageHeight Height of the game stage
     * @param levelParent The parent level object to control game state (pause, resume)
     */
    public PauseScreen(double xPosition, double yPosition, double stageWidth, double stageHeight, LevelParent levelParent) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.levelParent = levelParent;
        this.container = new HBox();
        initializeContainer(stageWidth, stageHeight);
    }

    /**
     * Initializes the container and adds the pause button to the UI.
     *
     * @param stageWidth The width of the game stage
     * @param stageHeight The height of the game stage
     */
    private void initializeContainer(double stageWidth, double stageHeight) {
        // Set the container's position
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);

        // Create and add the pause button
        ImageView pauseButton = createPauseButton(stageWidth, stageHeight);
        container.getChildren().add(pauseButton);
    }

    /**
     * Creates the pause button with dynamic size and position based on the stage dimensions.
     *
     * @param stageWidth The width of the game stage
     * @param stageHeight The height of the game stage
     * @return The ImageView representing the pause button
     */
    private ImageView createPauseButton(double stageWidth, double stageHeight) {
        Image pauseImage = new Image(getClass().getResource(PAUSE_BUTTON_NAME).toExternalForm());
        ImageView pauseButton = new ImageView(pauseImage);

        // Adjust button size relative to stage dimensions
        pauseButton.setFitHeight(stageWidth * 0.08);
        pauseButton.setFitWidth(stageHeight * 0.08);
        pauseButton.setPreserveRatio(true);

        // Positioning the button at the top left corner
        pauseButton.setTranslateX(stageWidth * 0.12);
        pauseButton.setTranslateY(stageHeight * 0.01);

        // Adding functionality to pause the game when clicked
        pauseButton.setOnMouseClicked(e -> {
            System.out.println("Pause button clicked");
            if (levelParent != null) {
                levelParent.pauseGame();
            }
            showPauseScreen(pauseButton);
        });

        return pauseButton;
    }

    /**
     * Displays the pause screen with overlay, including buttons for resume, levels, quit, and retry.
     *
     * @param pauseButton The pause button that was clicked
     */
    private void showPauseScreen(ImageView pauseButton) {
        try {
            Stage stage = (Stage) pauseButton.getScene().getWindow();
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();

            // Add background overlay
            double rectangleMargin = 20; // Margin around the pause screen
            javafx.scene.shape.Rectangle background = new javafx.scene.shape.Rectangle(
                    stageWidth + rectangleMargin * 2, stageHeight + rectangleMargin * 2);
            background.setFill(javafx.scene.paint.Color.BLACK);

            // Show the pause screen image
            Image pauseScreenImage = new Image(getClass().getResource(PAUSE_SCREEN_IMAGE).toExternalForm());
            ImageView pauseScreenView = new ImageView(pauseScreenImage);
            pauseScreenView.setPreserveRatio(true);
            pauseScreenView.setFitWidth(stageWidth);
            pauseScreenView.setFitHeight(stageHeight);

            // Create buttons for resume, levels, quit, and retry
            ImageView resumeButton = createResumeButton(stageWidth, stageHeight);
            ImageView levelsButton = createLevelsButton(stageWidth, stageHeight);
            ImageView quitButton = createQuitButton(stageWidth, stageHeight);
            ImageView retryButton = createRetryButton(stageWidth, stageHeight);

            // Stack the background, pause screen, and buttons in a StackPane
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(background, pauseScreenView, resumeButton, levelsButton, quitButton, retryButton);

            // Set alignment for buttons
            StackPane.setAlignment(resumeButton, javafx.geometry.Pos.CENTER);
            StackPane.setAlignment(levelsButton, javafx.geometry.Pos.CENTER_LEFT);
            StackPane.setAlignment(quitButton, javafx.geometry.Pos.CENTER_RIGHT);
            StackPane.setAlignment(retryButton, javafx.geometry.Pos.BOTTOM_CENTER);

            // Save the previous scene and set the new pause screen scene
            previousScene = stage.getScene();
            Scene pauseScene = new Scene(stackPane, stageWidth, stageHeight);
            stage.setScene(pauseScene);
            stage.setFullScreen(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to load Pause screen");
        }
    }

    /**
     * Creates the resume button and sets its functionality and positioning.
     *
     * @param stageWidth The width of the game stage
     * @param stageHeight The height of the game stage
     * @return The ImageView representing the resume button
     */
    private ImageView createResumeButton(double stageWidth, double stageHeight) {
        Image resumeImage = new Image(getClass().getResource(RESUME_BUTTON_NAME).toExternalForm());
        ImageView resumeButton = new ImageView(resumeImage);

        // Adjust button size
        resumeButton.setFitHeight(stageHeight * 0.8);
        resumeButton.setFitWidth(stageWidth * 0.25);
        resumeButton.setPreserveRatio(true);
        resumeButton.setOnMouseClicked(this::resumeGame);

        // Position the button near the center of the screen
        resumeButton.setTranslateX(stageWidth * 0.13 - resumeButton.getFitWidth() / 2);
        resumeButton.setTranslateY(stageHeight * -0.10);
        return resumeButton;
    }

    /**
     * Resumes the game when the resume button is clicked.
     *
     * @param event The mouse event triggered by clicking the resume button
     */
    private void resumeGame(MouseEvent event) {
        System.out.println("Resume button clicked");
        if (levelParent != null) {
            levelParent.resumeGame();
        }
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setFullScreen(true);
    }

    /**
     * Creates the levels button and sets its functionality and positioning.
     *
     * @param stageWidth The width of the game stage
     * @param stageHeight The height of the game stage
     * @return The ImageView representing the levels button
     */
    private ImageView createLevelsButton(double stageWidth, double stageHeight) {
        Image levelsImage = new Image(getClass().getResource(LEVELS_BUTTON_NAME).toExternalForm());
        ImageView levelsButton = new ImageView(levelsImage);

        // Adjust button size
        levelsButton.setFitHeight(stageHeight * 0.8);
        levelsButton.setFitWidth(stageWidth * 0.25);
        levelsButton.setPreserveRatio(true);
        levelsButton.setOnMouseClicked(this::goToLevelSelection);

        // Position the button below the resume button
        levelsButton.setTranslateX(stageWidth * 0.5 - levelsButton.getFitWidth() / 2);
        levelsButton.setTranslateY(stageHeight * 0.05);
        return levelsButton;
    }

    /**
     * Navigates to the level selection screen when the levels button is clicked.
     *
     * @param event The mouse event triggered by clicking the levels button
     */
    private void goToLevelSelection(MouseEvent event) {
        System.out.println("Levels button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LevelChoose.fxml"));
            Scene levelSelectScene = new Scene(loader.load(), 1300, 750);
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(levelSelectScene);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load LevelChoose.fxml");
        }
    }

    /**
     * Creates the quit button and sets its functionality and positioning.
     *
     * @param stageWidth The width of the game stage
     * @param stageHeight The height of the game stage
     * @return The ImageView representing the quit button
     */
    private ImageView createQuitButton(double stageWidth, double stageHeight) {
        Image quitImage = new Image(getClass().getResource(QUIT_BUTTON_NAME).toExternalForm());
        ImageView quitButton = new ImageView(quitImage);

        // Adjust button size
        quitButton.setFitHeight(stageHeight * 0.8);
        quitButton.setFitWidth(stageWidth * 0.25);
        quitButton.setPreserveRatio(true);
        quitButton.setOnMouseClicked(this::quitGame);

        // Position the button below the levels button
        quitButton.setTranslateX(stageWidth * 0.07 - quitButton.getFitWidth() / 0.565);
        quitButton.setTranslateY(stageHeight * 0.17);
        return quitButton;
    }

    /**
     * Quits the game when the quit button is clicked.
     *
     * @param event The mouse event triggered by clicking the quit button
     */
    private void quitGame(MouseEvent event) {
        System.out.println("Quit button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
            Scene homeScreen = new Scene(loader.load(), 1300, 750);
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load HomeScreen.fxml");
        }
    }

    /**
     * Creates the retry button and sets its functionality and positioning.
     *
     * @param stageWidth The width of the game stage
     * @param stageHeight The height of the game stage
     * @return The ImageView representing the retry button
     */
    private ImageView createRetryButton(double stageWidth, double stageHeight) {
        Image retryImage = new Image(getClass().getResource(RETRY_BUTTON_NAME).toExternalForm());
        ImageView retryButton = new ImageView(retryImage);

        // Adjust button size
        retryButton.setFitHeight(stageHeight * 0.8);
        retryButton.setFitWidth(stageWidth * 0.25);
        retryButton.setPreserveRatio(true);

        // Retry game action (currently commented out)
        retryButton.setOnMouseClicked(this::retryGame);

        // Position the button below levels and quit buttons
        retryButton.setTranslateX(stageWidth * 0.5 - retryButton.getFitWidth() / 0.504);
        retryButton.setTranslateY(stageHeight * 0.015);
        return retryButton;
    }

    /**
     * Retries the game when the retry button is clicked (currently not implemented).
     *
     * @param event The mouse event triggered by clicking the retry button
     */
    private void retryGame(MouseEvent event) {
        System.out.println("Retry button clicked");
        // Functionality to retry the game can be implemented here
    }

    /**
     * Returns the container holding the pause screen elements.
     *
     * @return The HBox container
     */
    public HBox getContainer() {
        return container;
    }
}
