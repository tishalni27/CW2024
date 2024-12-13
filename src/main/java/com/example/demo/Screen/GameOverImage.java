package com.example.demo.Screen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a "Game Over" image displayed in the game.
 * It extends {@link ImageView} to show a "Game Over" image at a specified position
 * within the game window. The image is loaded from the resources and is fixed at a
 * size of 500x500 pixels.
 *
 * <p>The class is used to display the Game Over screen when the game ends, providing
 * visual feedback to the player about the outcome of the game.</p>
 *
 * @see ImageView
 */
public class GameOverImage extends ImageView {

	// The path to the Game Over image resource
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a new {@code GameOverImage} object at the specified position.
	 * The image is set to a fixed size of 500x500 pixels.
	 *
	 * @param xPosition the X-coordinate for the position of the image in the game window
	 * @param yPosition the Y-coordinate for the position of the image in the game window
	 */
	public GameOverImage(double xPosition, double yPosition) {
		// Load the Game Over image from resources
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));

		// Set the image's width and height to 500x500 pixels
		setFitHeight(500);
		setFitWidth(500);

		// Set the layout position of the image
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}
