package com.example.demo.Screen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a "You Win" image displayed in the game.
 * It extends {@link ImageView} to show a "You Win" image at a specified position
 * within the game window. The image is loaded from the resources and is displayed
 * at a size of 600x500 pixels.
 *
 * <p>The class is used to display the "You Win" image when the player successfully
 * completes the game or level, providing visual feedback to the player about their victory.</p>
 *
 * @see ImageView
 */
public class WinImage extends ImageView {

	// The path to the "You Win" image resource
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	// Fixed dimensions for the image
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * Constructs a new {@code WinImage} object at the specified position.
	 * The image is initially hidden and set to a fixed size of 600x500 pixels.
	 *
	 * @param xPosition the X-coordinate for the position of the image in the game window
	 * @param yPosition the Y-coordinate for the position of the image in the game window
	 */
	public WinImage(double xPosition, double yPosition) {
		// Load the "You Win" image from resources
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));

		// Initially set the image to be invisible
		this.setVisible(false);

		// Set the image's width and height
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);

		// Set the layout position of the image
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Makes the "You Win" image visible.
	 * This method can be called to display the "You Win" image once the player
	 * has won the game.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}
