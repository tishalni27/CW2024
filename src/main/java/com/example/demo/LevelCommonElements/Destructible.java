package com.example.demo.LevelCommonElements;

/**
 * Represents an entity that can be destroyed or take damage.
 * <p>
 * This interface defines the methods that must be implemented by any class
 * whose objects are capable of taking damage and being destroyed. This is
 * typically used for actors in the game (e.g., enemies, projectiles) that can
 * be damaged and eventually destroyed when their health reaches zero.
 * </p>
 */
public interface Destructible {

	/**
	 * Applies damage to the destructible entity. The exact behavior is
	 * dependent on the implementing class (e.g., reducing health).
	 */
	void takeDamage();

	/**
	 * Destroys the destructible entity. This could involve marking the
	 * entity as destroyed, playing an animation, or removing it from the game.
	 */
	void destroy();
}
