package com.example.demo.Level1;

import com.example.demo.CommonElements.FighterPlane;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 55;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -10.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
	private static final int INITIAL_HEALTH = 10;
	private static final double FIRE_RATE = .015;
	private static final double Y_UPPER_BOUND = 70;
	private static final double Y_LOWER_BOUND = 700.0;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);

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
