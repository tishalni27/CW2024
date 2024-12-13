package com.example.demo.Level1;

/**
 * Configuration class for Level 1 of the game. This class contains constants related to
 * the game level's background, enemies, player configuration, and UI settings.
 *
 * The class is designed to prevent instantiation by throwing an AssertionError in its
 * private constructor.
 */
public class LevelOneConfig {


    // Game Level Configuration Constants


    /**
     * Path to the background image for Level 1.
     */
    public static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

    /**
     * The next level to load after advancing from Level 1.
     */
    public static final String NEXT_LEVEL = "com.example.demo.Level2.LevelTwo";



    // Enemy Spawning Parameters


    /**
     * Total number of enemies in Level 1.
     */
    public static final int TOTAL_ENEMIES = 5;

    /**
     * The number of kills required for advancing to the next level.
     */
    public static final int KILLS_TO_ADVANCE = 10;

    /**
     * Probability of spawning an enemy in Level 1.
     */
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;



    // Player Configuration


    /**
     * The initial health of the player at the start of Level 1.
     */
    public static final int PLAYER_INITIAL_HEALTH = 5;



    // UI Configuration

    /**
     * X position factor for displaying the kill count text on the screen.
     */
    public static final double KILL_COUNT_TEXT_X_FACTOR = 0.42;

    /**
     * Y position factor for displaying the kill count text on the screen.
     */
    public static final double KILL_COUNT_TEXT_Y_FACTOR = 0.04;

    /**
     * CSS style for the kill count text.
     */
    public static final String KILL_COUNT_TEXT_STYLE =
            "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;";

    // Prevent Instantiation

    /**
     * Private constructor to prevent instantiation of the configuration class.
     */
    private LevelOneConfig() {
        throw new AssertionError("Cannot be instantiated");
    }
}
