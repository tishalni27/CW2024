package com.example.demo.User;

import com.example.demo.LevelCommonElements.Actor.Projectile;

/**
 * Represents a projectile fired by the user plane in the game.
 * This class extends {@link Projectile} and defines the behavior of the user's projectiles,
 * including their movement speed and initial position.
 *
 * <p>The projectile moves horizontally from the user's plane and updates its position
 * as part of the game's update cycle. The projectile is rendered with a specific image
 * and height.</p>
 *
 * @see Projectile
 */
public class UserProjectile extends Projectile {

	// Path to the user's projectile image
	private static final String IMAGE_NAME = "userProjectile.png";

	// Height of the user's projectile image
	private static final int IMAGE_HEIGHT = 8;

	// Horizontal velocity for the projectile's movement
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a new {@code UserProjectile} object at the specified initial position.
	 * The projectile is created with an image, height, and its horizontal movement speed.
	 *
	 * @param initialXPos the initial X-coordinate for the projectile's position
	 * @param initialYPos the initial Y-coordinate for the projectile's position
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		// Call the superclass constructor to initialize the image, height, and position
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally based on its
	 * velocity.
	 */
	@Override
	public void updatePosition() {
		// Move the projectile horizontally by the defined velocity
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile. This method is part of the game's update cycle
	 * and calls the {@link #updatePosition()} method to move the projectile.
	 */
	@Override
	public void updateActor() {
		// Update the position of the projectile
		updatePosition();
	}
}
