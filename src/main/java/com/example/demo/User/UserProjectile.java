package com.example.demo.User;

import com.example.demo.CommonElements.Projectile;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userProjectile.png";
	private static final int IMAGE_HEIGHT = 8;
	private static final int HORIZONTAL_VELOCITY = 15;

	public UserProjectile(double initialXPos, double initialYPos) {
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
