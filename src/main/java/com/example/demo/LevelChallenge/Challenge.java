package com.example.demo.LevelChallenge;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.Level1AndChallenge.EnemyLevel1;
import com.example.demo.LevelView;
import com.example.demo.Levels.LevelParent;
import com.example.demo.controller.GameController;

import static com.example.demo.LevelChallenge.ChallengeConfig.*;

/**
 * The Challenge class represents a specific challenge level in the game.
 * It extends from LevelParent and contains logic to handle the gameplay,
 * enemy spawning, and checking for game over conditions.
 */
public class Challenge extends LevelParent {

    /**
     * Constructor to initialize the Challenge level with the specified screen height, width, and game controller.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth The width of the game screen.
     * @param controller The game controller to handle game events.
     */
    public Challenge(double screenHeight, double screenWidth, GameController controller) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
    }

    /**
     * Checks if the game is over. If the player is destroyed, the challenge is lost.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseChallenge();
        }
    }

    /**
     * Initializes the friendly units (player) and the score display on the screen.
     * Adds the user plane and score text to the root.
     */
    @Override
    protected void initializeFriendlyUnits() {
        // Add the user plane to the root
        getRoot().getChildren().add(getUser());

        // Set up and display the score text
        scoreText.setStyle(SCORE_TEXT_STYLE);
        scoreText.setLayoutX(SCORE_TEXT_X_POSITION);  // Use constant for X position
        scoreText.setLayoutY(SCORE_TEXT_Y_POSITION);  // Use constant for Y position
        scoreText.toFront();  // Ensure score text appears above other elements
        getRoot().getChildren().add(scoreText);
    }

    /**
     * Spawns enemy units based on a random probability.
     * The number of enemies is limited by the constant TOTAL_ENEMIES.
     * New enemies are spawned at a random Y position.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                // Random Y position for new enemy
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyLevel1(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates the LevelView for this level with the root and initial player health.
     *
     * @return The LevelView instance for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the player has reached the kill target required to advance to the next level.
     *
     * @return True if the player has killed enough enemies to advance, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
