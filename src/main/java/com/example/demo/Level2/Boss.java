package com.example.demo.Level2;

import com.example.demo.CommonElements.FighterPlane;
import com.example.demo.Level1.ActiveActorDestructible;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1200.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 55.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .05;
	private static final int IMAGE_HEIGHT = 80;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 40;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 140;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int MAX_FRAMES_WITH_SHIELD = 40;//changed max frame because its too long
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private final LevelViewLevelTwo levelView;
	public Boss(LevelViewLevelTwo levelView) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		this.levelView = levelView;
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		//update shield's position based on boss's position
		updateShieldPosition();
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		//activate shield if its needed
		if (!isShielded && shieldShouldBeActivated()) {
			activateShield();
		} else if (isShielded) {
			framesWithShieldActivated++;

			//check if shield has been activated for max frames
			if (shieldExhausted()) {
				deactivateShield();
			}
		}

		updatePosition();//updates boss's position and shield in each frame
	}

	private void updateShieldPosition() {
		if (isShielded) {
			// Get the boss's current X and Y positions
			double bossXPosition = getTranslateX() + getLayoutX();
			double bossYPosition = getTranslateY() + getLayoutY();

			// Adjust shield's position relative to the boss
			double shieldOffsetX = -75;  // Offset for shield on X axis
			double shieldOffsetY = 10;  // Offset for shield on Y axis

			// Update the shield's position in LevelView
			levelView.updateShieldPosition(bossXPosition + shieldOffsetX, bossYPosition + shieldOffsetY);
		}
	}

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

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
		levelView.showShield(getTranslateX()+50, getTranslateY()+50);
		System.out.println("Shield Activated!");
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		levelView.hideShield();
		System.out.println("Shield Deactivated");
	}

}
