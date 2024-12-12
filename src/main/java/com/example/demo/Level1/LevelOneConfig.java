package com.example.demo.Level1;

public class LevelOneConfig {
    // Game Level Configuration Constants
    public static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    public static final String NEXT_LEVEL = "com.example.demo.Level2.LevelTwo";

    // Enemy Spawning Parameters
    public static final int TOTAL_ENEMIES = 5;
    public static final int KILLS_TO_ADVANCE = 10;
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    // Player Configuration
    public static final int PLAYER_INITIAL_HEALTH = 5;

    // UI Configuration
    public static final double KILL_COUNT_TEXT_X_FACTOR = 0.42;
    public static final double KILL_COUNT_TEXT_Y_FACTOR = 0.04;
    public static final String KILL_COUNT_TEXT_STYLE =
            "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;";

    // Prevent instantiation
    private LevelOneConfig() {
        throw new AssertionError("Cannot be instantiated");
    }
}
