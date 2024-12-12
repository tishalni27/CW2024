package com.example.demo.Level1AndChallenge;

import com.example.demo.LevelCommonElements.Actor.Projectile;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyProjectile.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


}
