package com.example.demo.LevelCommonElements.Actor;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;

/**
 * Abstract class representing a projectile that is destructible.
 *
 * A projectile is an object that can be fired in the game world. It extends {@link ActiveActorDestructible},
 * which allows it to be destroyed upon taking damage. Projectiles move through the game world, and their
 * movement behavior is defined by subclasses.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a {@link Projectile} with the specified image, position, and size.
	 *
	 * @param imageName The name of the image file used to represent the projectile.
	 * @param imageHeight The height to which the image should be scaled.
	 * @param initialXPos The initial X position of the projectile in the scene.
	 * @param initialYPos The initial Y position of the projectile in the scene.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * This method is called when the projectile takes damage.
	 * The projectile is destroyed upon taking damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();  // Destroy the projectile when it takes damage
	}

	/**
	 * Abstract method for updating the position of the projectile.
	 *
	 * Subclasses must implement this method to define how the projectile moves in the game world.
	 */
	@Override
	public abstract void updatePosition();
}
