package com.example.demo.LevelCommonElements.Actor;

import com.example.demo.LevelCommonElements.Destructible;

/**
 * Abstract class representing an active actor that is destructible.
 * A destructible actor can take damage and be destroyed.
 *
 * This class extends {@link ActiveActor} and implements {@link Destructible},
 * adding functionality for taking damage and being destroyed.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/** A flag to track whether the actor is destroyed. */
	private boolean isDestroyed;

	/**
	 * Constructs an {@link ActiveActorDestructible} with the specified image, position, and size.
	 *
	 * @param imageName The name of the image file used to represent the actor.
	 * @param imageHeight The height to which the image should be scaled.
	 * @param initialXPos The initial X position of the actor in the scene.
	 * @param initialYPos The initial Y position of the actor in the scene.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false; // Initially, the actor is not destroyed
	}

	/**
	 * Abstract method to update the position of the actor.
	 * Subclasses must implement this method to define how the actor's position changes.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to update the actor's state.
	 * This could include updating animations, behaviors, or other changes.
	 *
	 * Subclasses should provide specific implementation details.
	 */
	public abstract void updateActor();

	/**
	 * Abstract method to handle the actor taking damage.
	 * Subclasses must implement this method to define how the actor reacts to damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor, marking it as destroyed.
	 * This method is part of the {@link Destructible} interface.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed The new destroyed state of the actor.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks whether the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, {@code false} otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
