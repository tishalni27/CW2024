package com.example.demo.Level1AndChallenge;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.LevelCommonElements.Actor.FighterPlane;

/**
 * Represents the enemy for Level 1
 */
public class EnemyLevel1 extends FighterPlane {

	/**
	 * The image name used for the enemy in Level 1.
	 */
	private static final String IMAGE_NAME = "enemyLevel1.png";

	/**
	 * The height of the enemy image.
	 */
	private static final int IMAGE_HEIGHT = 55;

	/**
	 * The horizontal velocity of the enemy's movement.
	 */
	private static final int HORIZONTAL_VELOCITY = -5;

	/**
	 * The X position offset for the projectile fired by the enemy.
	 */
	private static final double PROJECTILE_X_POSITION_OFFSET = -10.0;

	/**
	 * The Y position offset for the projectile fired by the enemy.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 18.0;

	/**
	 * The initial health of the enemy.
	 */
	private static final int INITIAL_HEALTH = 1;

	/**
	 * The fire rate of the enemy, representing the probability of firing a projectile during each update.
	 */
	private static final double FIRE_RATE = 0.01;

	/**
	 * The screen height used to calculate vertical bounds.
	 */
	private static final double SCREEN_HEIGHT = 1200; // Fullscreen height

	/**
	 * The upper vertical boundary for the enemy's movement.
	 */
	private static final double Y_UPPER_BOUND = SCREEN_HEIGHT * 0.1;

	/**
	 * The lower vertical boundary for the enemy's movement.
	 */
	private static final double Y_LOWER_BOUND = SCREEN_HEIGHT * 0.9;

	/**
	 * Creates a new `EnemyLevel1` instance at the specified initial position.
	 * The Y-position is clamped within the defined vertical bounds to prevent the enemy
	 * from moving outside the screen's vertical limits.
	 *
	 * @param initialXPos the initial X position of the enemy on the screen
	 * @param initialYPos the initial Y position of the enemy on the screen
	 */
	public EnemyLevel1(double initialXPos, double initialYPos) {
		// Ensure the Y position is within the bounds
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos,
				Math.max(Y_UPPER_BOUND, Math.min(initialYPos, Y_LOWER_BOUND)), INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy. The enemy moves horizontally based on its velocity.
	 * The Y position is checked to ensure it stays within the vertical bounds. If the enemy
	 * goes out of bounds, it resets to its original Y position.
	 */
	@Override
	public void updatePosition() {
		// Move horizontally based on horizontal velocity
		moveHorizontally(HORIZONTAL_VELOCITY);

		// Store the initial Y position
		double initialYPosition = getTranslateY();

		// Get the new Y position based on translation
		double newYPosition = getLayoutY() + getTranslateY();

		// Check if the new Y position is out of bounds
		if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
			setTranslateY(initialYPosition); // Reset Y position if out of bounds
		}
	}

	/**
	 * Fires a projectile from the enemy at a certain rate. If a projectile is fired,
	 * it returns a new projectile object; otherwise, it returns null.
	 *
	 * @return a new `EnemyProjectile` if the projectile is fired, or null if not
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		// Fire a projectile based on the fire rate
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the enemy actor, including its position and potentially firing a projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition(); // Update the enemy's position
	}
}
