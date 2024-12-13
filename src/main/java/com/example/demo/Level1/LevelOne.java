package com.example.demo.Level1;

import com.example.demo.Level1AndChallenge.EnemyLevel1;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.LevelView;
import com.example.demo.Levels.LevelParent;
import com.example.demo.controller.GameController;

import static com.example.demo.Level1.LevelOneConfig.*;

/**
 * Represents Level 1 of the game.
 * This class is responsible for setting up the level, including enemy spawning,
 * checking for game over conditions, and initializing the friendly units and level view.
 */
public class LevelOne extends LevelParent {

	/**
	 * Constructs a LevelOne object with the specified screen dimensions and game controller.
	 *
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 * @param controller The game controller to handle user input and game actions.
	 */
	public LevelOne(double screenHeight, double screenWidth, GameController controller) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
	}

	/**
	 * Checks if the game is over. If the player is destroyed, the game ends.
	 * If the player has reached the required number of kills, the game progresses to the next level.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the friendly units for the level, including adding the player and displaying
	 * the kill count text.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());

		// Set style and position for kill count text
		killCountText.setStyle(KILL_COUNT_TEXT_STYLE);
		positionKillCountText();
		getRoot().getChildren().add(killCountText);
	}

	/**
	 * Spawns enemy units on the screen based on the spawn probability and the maximum number
	 * of enemies that can be on the screen.
	 */
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

	/**
	 * Instantiates the LevelView object which manages the graphical display of the level.
	 *
	 * @return The LevelView object for the current level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Positions the kill count text on the screen based on the screen width and height.
	 * The position is determined using predefined factors for X and Y positioning.
	 */
	private void positionKillCountText() {
		double x = getScreenWidth() * KILL_COUNT_TEXT_X_FACTOR;
		double y = getScreenHeight() * KILL_COUNT_TEXT_Y_FACTOR;
		killCountText.setLayoutX(x);
		killCountText.setLayoutY(y);
		killCountText.toFront();
	}

	/**
	 * Checks if the player has reached the required number of kills to advance to the next level.
	 *
	 * @return True if the player has killed enough enemies, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
