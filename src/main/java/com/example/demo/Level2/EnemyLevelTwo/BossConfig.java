package com.example.demo.Level2.EnemyLevelTwo;

/**
 * Configuration class for the Boss enemy in Level 2.
 * This class holds constant values related to the Boss's behavior, such as fire rate, shield probability, initial health, and shield duration.
 * These constants are used throughout the Boss class to manage its mechanics.
 */
public class BossConfig {

    /**
     * The fire rate of the Boss. Determines the probability that the Boss will fire a projectile in a given frame.
     * Value between 0 and 1, where higher values increase the likelihood of firing.
     */
    public static final double BOSS_FIRE_RATE = 0.04;

    /**
     * The probability that the Boss will activate its shield during a given frame.
     * Value between 0 and 1, where higher values increase the likelihood of shield activation.
     */
    public static final double BOSS_SHIELD_PROBABILITY = 0.01;

    /**
     * The initial health of the Boss at the start of the level.
     * This value represents how much damage the Boss can take before being defeated.
     */
    public static final int BOSS_INITIAL_HEALTH = 50;

    /**
     * The maximum number of frames that the Boss's shield can stay activated.
     * After this duration, the shield will be deactivated.
     */
    public static final int MAX_FRAMES_WITH_SHIELD = 40;

}
