package com.example.demo;

import com.example.demo.LevelCommonElements.visuals.HeartDisplay;
import com.example.demo.Screen.GameOverImage;
import com.example.demo.Screen.WinImage;
import javafx.scene.Group;

/**
 * This class represents the visual aspects of a level in the game.
 * It handles displaying elements such as the heart display, win screen, and game over screen.
 * The {@code LevelView} class is responsible for updating and managing the UI components that
 * represent the player's current state during the game, such as the number of hearts and the
 * display for winning or losing the game.
 *
 * <p>The class uses JavaFX {@link Group} as a container to manage visual components, such as
 * the {@link WinImage}, {@link GameOverImage}, and {@link HeartDisplay}.</p>
 */
public class LevelView {

	// Default positions for various UI elements on the screen
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 400;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 470;
	private static final int LOSS_SCREEN_Y_POSITION = 55;

	// Instance variables for UI elements
	private final Group root;            // The root node for all visual elements in the level
	private final WinImage winImage;     // Win screen image
	private final GameOverImage gameOverImage;  // Game over screen image
	private final HeartDisplay heartDisplay;    // Heart display UI element for showing player's lives

	/**
	 * Constructs a {@code LevelView} with the provided root group and number of hearts to display.
	 * Initializes the heart display, win image, and game over image elements at their default positions.
	 *
	 * @param root the {@link Group} container that holds all visual elements in the level
	 * @param heartsToDisplay the number of hearts to display, representing the player's lives
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
	}

	/**
	 * Displays the heart display on the screen. This UI element represents the player's remaining lives.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen and shows the win screen animation.
	 * This is typically shown when the player wins the level.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game over image on the screen.
	 * This is typically shown when the player loses the level.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Removes hearts from the heart display based on the remaining number of hearts.
	 * This method updates the heart display by removing heart icons as the player loses lives.
	 *
	 * @param heartsRemaining the number of hearts to remain after removal
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
}
