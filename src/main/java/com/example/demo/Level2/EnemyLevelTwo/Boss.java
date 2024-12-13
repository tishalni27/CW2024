package com.example.demo.Level2.EnemyLevelTwo;

import com.example.demo.Level2.LevelViewLevelTwo;
import com.example.demo.LevelCommonElements.Actor.FighterPlane;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import static com.example.demo.Level2.EnemyLevelTwo.BossConfig.*;

import java.util.*;

/**
 * Represents the Boss enemy in Level 2 of the game.
 * The Boss controls movement patterns, shield activation, projectile firing, and damage-taking mechanics.
 * It is a subclass of {@link FighterPlane}.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1200.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 55.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final int IMAGE_HEIGHT = 80;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 140;
	private static final int Y_POSITION_LOWER_BOUND = 600;

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private final LevelViewLevelTwo levelView;

	/**
	 * Constructs a Boss instance with the specified level view.
	 *
	 * @param levelView The level view that will be used to update the shield's position.
	 */
	public Boss(LevelViewLevelTwo levelView) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, BOSS_INITIAL_HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		this.levelView = levelView;
		initializeMovePattern();
	}

	/**
	 * Updates the position of the Boss. The Boss moves vertically based on the current move pattern,
	 * and its shield position is updated accordingly.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();

		// Prevent the Boss from moving beyond the screen's vertical bounds
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}

		// Update the shield's position based on the boss's position
		updateShieldPosition();
	}

	/**
	 * Updates the Boss's state, including its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the Boss fires in the current frame.
	 *
	 * @return A new {@link BossProjectile} instance if the Boss fires, or null if it does not.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles damage taken by the Boss. If the Boss is shielded, it does not take damage.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the movement pattern of the Boss, which is a random sequence of vertical movements.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status. The shield is activated if necessary and deactivated when exhausted.
	 */
	private void updateShield() {
		if (!isShielded && shieldShouldBeActivated()) {
			activateShield();
		} else if (isShielded) {
			framesWithShieldActivated++;

			// Check if shield has been activated for the max duration
			if (shieldExhausted()) {
				deactivateShield();
			}
		}

		updatePosition(); // Updates boss's position and shield in each frame
	}

	/**
	 * Updates the position of the shield relative to the Boss's position.
	 */
	private void updateShieldPosition() {
		if (isShielded) {
			// Get the Boss's current X and Y positions
			double bossXPosition = getTranslateX() + getLayoutX();
			double bossYPosition = getTranslateY() + getLayoutY();

			// Adjust shield's position relative to the Boss
			double shieldOffsetX = -75;  // X-axis offset for shield
			double shieldOffsetY = 10;   // Y-axis offset for shield

			// Update the shield's position in LevelView
			levelView.updateShieldPosition(bossXPosition + shieldOffsetX, bossYPosition + shieldOffsetY);
		}
	}

	/**
	 * Retrieves the next move for the Boss from the movement pattern.
	 * The Boss moves according to the shuffled move pattern, and the sequence is reset after a number of moves.
	 *
	 * @return The next vertical move for the Boss.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the Boss should fire a projectile in the current frame.
	 *
	 * @return True if the Boss fires in the current frame, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Retrieves the initial Y position for the projectile fired by the Boss.
	 *
	 * @return The initial Y position for the Boss's projectile.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the Boss's shield should be activated based on a random probability.
	 *
	 * @return True if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the Boss's shield has been active for the maximum number of frames.
	 *
	 * @return True if the shield is exhausted, false otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the Boss's shield, resetting the frame count and updating the level view.
	 */
	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
		levelView.showShield(getTranslateX() + 50, getTranslateY() + 50);
		System.out.println("Shield Activated!");
	}

	/**
	 * Deactivates the Boss's shield and updates the level view.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		levelView.hideShield();
		System.out.println("Shield Deactivated");
	}
}
