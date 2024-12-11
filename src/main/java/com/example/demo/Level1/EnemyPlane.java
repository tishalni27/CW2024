package com.example.demo.Level1;

import com.example.demo.CommonElements.FighterPlane;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 55;
	private static final int HORIZONTAL_VELOCITY = -2;
	private static final double PROJECTILE_X_POSITION_OFFSET = 0.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 18.0;
	private static final int INITIAL_HEALTH = 10;
	private static final double FIRE_RATE = .015;
	private static final double SCREEN_HEIGHT = 1200; // Fullscreen height
	private static final double Y_UPPER_BOUND = SCREEN_HEIGHT * 0.1;
	private static final double Y_LOWER_BOUND = SCREEN_HEIGHT * 0.9;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, Math.max(Y_UPPER_BOUND, Math.min(initialYPos, Y_LOWER_BOUND)), INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		double initialYPosition = getTranslateY();

		// Update horizontal movement
		moveHorizontally(HORIZONTAL_VELOCITY);

		// Get the new Y position
		double newYPosition = getLayoutY() + getTranslateY();

		// Boundary check for vertical movement
		if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
			setTranslateY(initialYPosition); // Reset position if out of bounds
		}
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
