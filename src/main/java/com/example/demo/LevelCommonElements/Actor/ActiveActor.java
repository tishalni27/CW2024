package com.example.demo.LevelCommonElements.Actor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class representing an active actor in the game.
 * Active actors are entities that have a visible representation (an image)
 * and can move within the game scene.
 *
 * This class extends {@link ImageView} to provide functionality for managing
 * the actor's image and position in the game scene.
 */
public abstract class ActiveActor extends ImageView {

	/** The base location for images used by actors. */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an {@link ActiveActor} with the specified image, position, and size.
	 *
	 * @param imageName The name of the image file used to represent the actor.
	 * @param imageHeight The height to which the image should be scaled.
	 * @param initialXPos The initial X position of the actor in the scene.
	 * @param initialYPos The initial Y position of the actor in the scene.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Set the image for the actor
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));

		// Set the initial position and size of the actor
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true); // Maintain the image's aspect ratio
	}

	/**
	 * Abstract method that must be implemented by subclasses to update the actor's position.
	 * This method should define how the actor moves or changes in the game.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified amount.
	 * This method updates the actor's X position.
	 *
	 * @param horizontalMove The distance to move the actor horizontally.
	 */
	public void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 * This method updates the actor's Y position.
	 *
	 * @param verticalMove The distance to move the actor vertically.
	 */
	public void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
