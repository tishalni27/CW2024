package com.example.demo.LevelChallenge;

/**
 * The ChallengeConfig class contains configuration constants for the Challenge level.
 * It defines the background image, enemy spawning parameters, player settings,
 * and UI configurations related to the level.
 *
 * This class is designed to store configuration values and is not meant to be instantiated.
 */
public class ChallengeConfig {

    // Game Level Configuration Constants

    /** The file path to the background image for the Challenge level. */
    public static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";

    // Enemy Spawning Parameters

    /** The total number of enemies to spawn in the Challenge level. */
    public static final int TOTAL_ENEMIES = 5;

    /** The probability (between 0 and 1) that an enemy will spawn. */
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    /** The number of kills required by the player to advance to the next level. */
    public static final int KILLS_TO_ADVANCE = 10;

    // Player Configuration

    /** The initial health of the player at the start of the Challenge level. */
    public static final int PLAYER_INITIAL_HEALTH = 5;

    // UI Configuration

    /** The X position for the score text on the screen. */
    public static final double SCORE_TEXT_X_POSITION = 690.0;

    /** The Y position for the score text on the screen. */
    public static final double SCORE_TEXT_Y_POSITION = 10.0;

    /** The style of the score text, including font size, weight, and color. */
    public static final String SCORE_TEXT_STYLE =
            "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;";

    // Prevent instantiation of this class

    /**
     * Private constructor to prevent instantiation of the ChallengeConfig class.
     * This class is meant to be a static utility class, so it should not be instantiated.
     *
     * @throws AssertionError If an attempt is made to instantiate this class.
     */
    private ChallengeConfig() {
        throw new AssertionError("Cannot be instantiated");
    }
}
