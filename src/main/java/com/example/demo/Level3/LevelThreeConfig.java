package com.example.demo.Level3;

public class LevelThreeConfig {

    public static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";
    public static final int TOTAL_ENEMIES = 5;
    public static final int KILLS_TO_ADVANCE = 10;
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    public static final int PLAYER_INITIAL_HEALTH = 5;

    // Custom bounds for enemy spawning
    public static final double ENEMY_SPAWN_MIN_Y = 100.0;  // Minimum Y position for enemy spawn
    public static final double ENEMY_SPAWN_MAX_Y = 700.0;  // Maximum Y position for enemy spawn
}
