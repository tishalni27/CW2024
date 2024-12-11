package com.example.demo.Levels;

import com.example.demo.Level1.ActiveActorDestructible;
import com.example.demo.Level1.EnemyPlane;
import com.example.demo.LevelView;
import com.example.demo.controller.Controller;

public class LevelUnlimited extends LevelParent {


    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";

    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelUnlimited(double screenHeight, double screenWidth, Controller controller) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseChallenge();
        }

    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
        killCountText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");

        killCountText.setLayoutX(650); // Adjust X position to avoid overlap
        killCountText.setLayoutY(30); // Adjust Y position
        killCountText.toFront(); // Ensure it's rendered above other elements

        // Make sure the killCountText is added to the root
        getRoot().getChildren().add(killCountText);
        scoreText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        scoreText.setLayoutX(650);  // Adjust X position for points
        scoreText.setLayoutY(60);  // Adjust Y position for points
        scoreText.toFront();
        getRoot().getChildren().add(scoreText);
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

}
