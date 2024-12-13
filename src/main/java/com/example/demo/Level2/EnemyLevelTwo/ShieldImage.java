package com.example.demo.Level2.EnemyLevelTwo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the shield image that can be displayed around the Boss.
 * This class extends {@link ImageView} to manage the display and behavior of the shield.
 */
public class ShieldImage extends ImageView {

	// Constants for the shield image dimensions
	private static final int SHIELD_WIDTH = 80;
	private static final int SHIELD_HEIGHT = 131;

	/**
	 * Constructs a new ShieldImage and sets its initial position and image.
	 * The shield image is loaded from the resources folder and placed at the specified coordinates.
	 *
	 * @param xPosition The initial X position of the shield image.
	 * @param yPosition The initial Y position of the shield image.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		// Load the image from resources
		String IMAGE_NAME = getClass().getResource("/com/example/demo/images/shield.png").toExternalForm();
		this.setImage(new Image(IMAGE_NAME));

		// Set the initial position and visibility
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setVisible(true);  // The shield is visible by default
		this.setFitHeight(SHIELD_HEIGHT);
		this.setFitWidth(SHIELD_WIDTH);
	}

	/**
	 * Displays the shield at the specified coordinates.
	 * This method is used to show the shield when it's activated.
	 *
	 * @param xPosition The X position where the shield should be placed.
	 * @param yPosition The Y position where the shield should be placed.
	 */
	public void showShield(double xPosition, double yPosition) {
		this.setVisible(true);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		System.out.println("Shield shown at: (" + xPosition + ", " + yPosition + ")");
	}

	/**
	 * Hides the shield.
	 * This method is used to deactivate and hide the shield when it's no longer needed.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}
