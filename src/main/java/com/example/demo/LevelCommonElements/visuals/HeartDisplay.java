package com.example.demo.LevelCommonElements.visuals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a visual display of hearts in the game.
 * <p>
 * This class is responsible for creating and displaying a set of heart images
 * on the screen to represent the player's health. The hearts are displayed in
 * a horizontal box (HBox) at a specified position and can be updated or removed.
 * </p>
 */
public class HeartDisplay {

	// Constants for heart image properties
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0; // Used to remove the first heart in the list

	// Instance variables for container and positioning
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@link HeartDisplay} and initializes the heart container
	 * and the hearts to be displayed.
	 *
	 * @param xPosition The X position of the heart display on the screen.
	 * @param yPosition The Y position of the heart display on the screen.
	 * @param heartsToDisplay The number of hearts to display initially.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container that holds the heart images.
	 * The container is an {@link HBox} positioned at the specified coordinates.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes and adds the specified number of heart images to the container.
	 * The hearts are positioned in a horizontal layout within the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the container.
	 * This simulates the player losing health by removing a heart from the display.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Resets the heart display by clearing all hearts and reinitializing the original number of hearts.
	 */
	public void reset() {
		// Clear all hearts from the container
		container.getChildren().clear();

		// Reinitialize the hearts
		initializeHearts();
	}

	/**
	 * Returns the container that holds the heart images.
	 *
	 * @return The {@link HBox} container with the heart images.
	 */
	public HBox getContainer() {
		return container;
	}
}
