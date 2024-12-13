package com.example.demo.LevelCommonElements.Actor;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;

/**
 * Abstract class representing a fighter plane that is destructible and capable of firing projectiles.
 * The fighter plane has health that can be reduced when it takes damage, and it can fire projectiles.
 *
 * This class extends {@link ActiveActorDestructible} and provides functionality for handling health,
 * taking damage, and firing projectiles.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/** The health of the fighter plane. */
	private int health;

	/**
	 * Constructs a {@link FighterPlane} with the specified image, position, health, and size.
	 *
	 * @param imageName The name of the image file used to represent the fighter plane.
	 * @param imageHeight The height to which the image should be scaled.
	 * @param initialXPos The initial X position of the fighter plane in the scene.
	 * @param initialYPos The initial Y position of the fighter plane in the scene.
	 * @param health The initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method to fire a projectile from the fighter plane.
	 *
	 * Subclasses must implement this method to define how the fighter plane fires projectiles.
	 *
	 * @return The projectile fired by the fighter plane, represented as an instance of {@link ActiveActorDestructible}.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the fighter plane when it takes damage.
	 * If the health reaches zero, the fighter plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy(); // Destroy the fighter plane when health reaches zero
		}
	}

	/**
	 * Gets the X position of the projectile based on an offset.
	 *
	 * @param xPositionOffset The offset to apply to the X position of the projectile.
	 * @return The X position for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Gets the Y position of the projectile based on an offset.
	 *
	 * @param yPositionOffset The offset to apply to the Y position of the projectile.
	 * @return The Y position for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the fighter plane has reached zero.
	 *
	 * @return {@code true} if the health is zero, {@code false} otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return The current health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}
}
