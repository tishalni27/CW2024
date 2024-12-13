package com.example.demo.Level3;

/**
 * Configuration class for Level Three.
 * Contains all the static constants required for setting up the gameplay parameters in Level Three.
 */
public class LevelThreeConfig {

    /**
     * The background image file path for Level Three.
     */
    public static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";

    /**
     * The total number of enemies that can be present in Level Three.
     */
    public static final int TOTAL_ENEMIES = 5;

    /**
     * The number of kills required to advance to the next level.
     */
    public static final int KILLS_TO_ADVANCE = 10;

    /**
     * The probability of spawning an enemy at each spawn attempt.
     * The value is between 0 and 1, with 0 being no chance and 1 being 100% chance.
     */
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    /**
     * The initial health of the player at the start of Level Three.
     */
    public static final int PLAYER_INITIAL_HEALTH = 5;

    // Custom bounds for enemy spawning
    /**
     * The minimum Y position for spawning enemies.
     */
    public static final double ENEMY_SPAWN_MIN_Y = 100.0;

    /**
     * The maximum Y position for spawning enemies.
     */
    public static final double ENEMY_SPAWN_MAX_Y = 700.0;
}
