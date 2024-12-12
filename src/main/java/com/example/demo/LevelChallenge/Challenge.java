package com.example.demo.LevelChallenge;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.Level1AndChallenge.EnemyPlane;
import com.example.demo.LevelView;
import com.example.demo.Levels.LevelParent;
import com.example.demo.controller.GameController;
import static com.example.demo.LevelChallenge.ChallengeConfig.*;
public class Challenge extends LevelParent {



    public Challenge(double screenHeight, double screenWidth, GameController controller) {
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

        // Make sure the killCountText is added to the root
        scoreText.setStyle(SCORE_TEXT_STYLE);
        scoreText.setLayoutX(SCORE_TEXT_X_POSITION);  // Use constant for X position
        scoreText.setLayoutY(SCORE_TEXT_Y_POSITION);
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
