package com.example.demo.Level1AndChallenge;

import com.example.demo.LevelCommonElements.Actor.Projectile;

/**
 * Represents a projectile fired by the enemy in the game. This class extends the `Projectile`
 * class and defines the specific behavior, appearance, and movement for the enemy's projectile.
 */
public class EnemyProjectile extends Projectile {

	/**
	 * The image name used for the enemy's projectile.
	 */
	private static final String IMAGE_NAME = "enemyProjectile.png";

	/**
	 * The height of the enemy's projectile image.
	 */
	private static final int IMAGE_HEIGHT = 30;

	/**
	 * The horizontal velocity of the enemy's projectile movement.
	 */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Creates a new `EnemyProjectile` instance at the specified initial position.
	 *
	 * @param initialXPos the initial X position of the projectile
	 * @param initialYPos the initial Y position of the projectile
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile. The projectile moves horizontally based on
	 * its horizontal velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor's state, including updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
