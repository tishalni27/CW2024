package com.example.demo.Level2;

import com.example.demo.Level2.EnemyLevelTwo.ShieldImage;
import com.example.demo.LevelView;
import javafx.scene.Group;

/**
 * The LevelView for Level 2 of the game.
 * This class extends {@link LevelView} and is responsible for displaying elements specific to Level 2, including the shield image for the boss.
 */
public class LevelViewLevelTwo extends LevelView {

	// Constants for the shield's initial position
	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;

	// The root group to which visual elements are added
	private final Group root;

	// The shield image associated with the boss
	private final ShieldImage shieldImage;

	/**
	 * Constructs a new LevelViewLevelTwo instance.
	 * Initializes the root group and the shield image to be displayed in this level.
	 *
	 * @param root The root group where visual elements will be added.
	 * @param heartsToDisplay The number of hearts to be displayed for the player's health.
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);  // Initialize the parent class with the root and hearts
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}

	/**
	 * Adds the shield image to the root of the level.
	 * This method ensures the shield is added to the scene if it hasn't been added already.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	/**
	 * Shows the shield at the specified position.
	 * The shield is placed at the given coordinates and made visible.
	 *
	 * @param xPosition The X coordinate where the shield should be placed.
	 * @param yPosition The Y coordinate where the shield should be placed.
	 */
	public void showShield(double xPosition, double yPosition) {
		shieldImage.showShield(xPosition, yPosition);
		// Ensure the shield is added to the root if it's not already there
		if (!root.getChildren().contains(shieldImage)) {
			addImagesToRoot();
		}
	}

	/**
	 * Hides the shield from the view.
	 * This method is used to deactivate the shield and remove it from view.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Updates the position of the shield.
	 * This method moves the shield to the specified coordinates.
	 *
	 * @param x The new X coordinate for the shield's position.
	 * @param y The new Y coordinate for the shield's position.
	 */
	public void updateShieldPosition(double x, double y) {
		shieldImage.showShield(x, y);
	}
}
