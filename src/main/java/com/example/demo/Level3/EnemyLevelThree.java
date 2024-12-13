package com.example.demo.Level3;

import com.example.demo.LevelCommonElements.Actor.FighterPlane;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.Level1AndChallenge.EnemyProjectile;

/**
 * Represents an enemy in Level 3 of the game.
 * This class extends {@link FighterPlane} and adds behavior specific to the enemy fighter plane in Level 3, including movement and projectile firing.
 */
public class EnemyLevelThree extends FighterPlane {

    // Constants for the enemy's image, movement, and projectile
    private static final String IMAGE_NAME = "enemyLevel3.png";
    private static final int IMAGE_HEIGHT = 70;
    private static final int HORIZONTAL_VELOCITY = -4;
    private static final double PROJECTILE_X_POSITION_OFFSET = -40.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;
    private static final int INITIAL_HEALTH = 10;
    private static final double FIRE_RATE = 0.03;

    /**
     * Constructs a new enemy for Level 3.
     * Initializes the enemy with its image, initial health, and starting position.
     *
     * @param initialXPos The initial X position of the enemy.
     * @param initialYPos The initial Y position of the enemy.
     */
    public EnemyLevelThree(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the enemy's position by moving it horizontally.
     * The enemy moves at a constant horizontal velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Determines whether the enemy should fire a projectile and returns it.
     * The enemy fires a projectile based on a random chance determined by the fire rate.
     *
     * @return A new {@link EnemyProjectile} if the enemy fires, or null if no projectile is fired.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Updates the enemy actor.
     * This method is called every frame to update the enemy's position and behavior.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}
