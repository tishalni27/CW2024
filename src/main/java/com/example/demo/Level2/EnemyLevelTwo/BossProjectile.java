package com.example.demo.Level2.EnemyLevelTwo;

import com.example.demo.LevelCommonElements.Actor.Projectile;

/**
 * Represents the projectile fired by the Boss in Level 2.
 * This class is a subclass of {@link Projectile} and handles the movement and behavior of the Boss's projectile.
 */
public class BossProjectile extends Projectile {

	// Constants for the Boss's projectile
	private static final String IMAGE_NAME = "bossProjectile.png";
	private static final int IMAGE_HEIGHT = 65;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 1050;

	/**
	 * Constructs a new BossProjectile with the specified initial Y position.
	 *
	 * @param initialYPos The initial Y position for the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the Boss's projectile.
	 * The projectile moves horizontally based on the specified velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the Boss's projectile.
	 * This method is called to update the projectile's position and any other behaviors.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
