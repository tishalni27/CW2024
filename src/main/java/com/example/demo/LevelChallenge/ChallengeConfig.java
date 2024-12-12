package com.example.demo.LevelChallenge;

public class ChallengeConfig {
    // Game Level Configuration Constants
    public static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";

    // Enemy Spawning Parameters
    public static final int TOTAL_ENEMIES = 5;
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    public static final int KILLS_TO_ADVANCE = 10;

    // Player Configuration
    public static final int PLAYER_INITIAL_HEALTH = 5;

    // UI Configuration
    public static final double SCORE_TEXT_X_POSITION = 690.0; // Adjusted X position for points
    public static final double SCORE_TEXT_Y_POSITION = 10.0;  // Adjusted Y position for points
    public static final String SCORE_TEXT_STYLE =
            "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;";

    // Prevent instantiation
    private ChallengeConfig() {
        throw new AssertionError("Cannot be instantiated");
    }
}
