package com.example.demo.Level1;

import com.example.demo.Level1AndChallenge.EnemyLevel1;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.LevelView;
import com.example.demo.Levels.LevelParent;
import com.example.demo.controller.GameController;

import static com.example.demo.Level1.LevelOneConfig.*;


public class LevelOne extends LevelParent {


	public LevelOne(double screenHeight, double screenWidth, GameController controller) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget())
			goToNextLevel(NEXT_LEVEL);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		/*killCountText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");

		killCountText.setLayoutX(650); // Adjust X position to avoid overlap
		killCountText.setLayoutY(30); // Adjust Y position
		killCountText.toFront(); // Ensure it's rendered above other elements
*/

		killCountText.setStyle(KILL_COUNT_TEXT_STYLE);
		positionKillCountText();
		// Make sure the killCountText is added to the root
		getRoot().getChildren().add(killCountText);
	}

	private void positionKillCountText() {
		double x = getScreenWidth() * KILL_COUNT_TEXT_X_FACTOR;
		double y = getScreenHeight() * KILL_COUNT_TEXT_Y_FACTOR;

		killCountText.setLayoutX(x);
		killCountText.setLayoutY(y);
		killCountText.toFront();
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyLevel1(getScreenWidth(), newEnemyInitialYPosition);
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
