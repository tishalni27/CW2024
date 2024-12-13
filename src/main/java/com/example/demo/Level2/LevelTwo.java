package com.example.demo.Level2;

import com.example.demo.Level2.EnemyLevelTwo.Boss;
import com.example.demo.LevelView;
import com.example.demo.Levels.LevelParent;
import com.example.demo.controller.GameController;

import static com.example.demo.Level2.LevelTwoConfig.*;

/**
 * Represents Level 2 of the game, where the player faces off against a Boss.
 * This class extends {@link LevelParent} and implements specific functionality for managing the boss battle and level transitions.
 */
public class LevelTwo extends LevelParent {

	// The boss for this level
	private final Boss boss;

	// The view for the level
	private LevelViewLevelTwo levelView;

	/**
	 * Constructs a new LevelTwo instance.
	 * Initializes the level view, the boss, and the parent level with necessary parameters.
	 *
	 * @param screenHeight The height of the screen for this level.
	 * @param screenWidth The width of the screen for this level.
	 * @param controller The controller for managing the game state.
	 */
	public LevelTwo(double screenHeight, double screenWidth, GameController controller) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		boss = new Boss(levelView);
	}

	/**
	 * Initializes the friendly units for this level.
	 * Adds the player (user) to the level's root.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if the game is over.
	 * The game is over if the player (user) is destroyed or if the boss is defeated.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns the boss enemy when there are no other enemies present in the level.
	 * This method ensures that the boss appears only once.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Instantiates the level view for Level 2.
	 * Creates a new instance of {@link LevelViewLevelTwo} and returns it.
	 *
	 * @return The level view for Level 2.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

}
