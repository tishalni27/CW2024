package com.example.demo.User;

import com.example.demo.Level3.UserShield;
import com.example.demo.LevelCommonElements.Actor.FighterPlane;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents the user-controlled plane in the game.
 * This class extends the {@link FighterPlane} class and handles the movement,
 * shooting projectiles, and managing the user's shield during the game.
 *
 * <p>The plane's movement is controlled by the player through keyboard inputs (up/down).
 * The user can also activate a shield by pressing the Enter key. Additionally, the plane can
 * shoot projectiles and keeps track of the number of enemies it has destroyed.</p>
 *
 * @see FighterPlane
 * @see UserShield
 */
public class UserPlane extends FighterPlane {

	// Path to the user's plane image
	private static final String IMAGE_NAME = "userplane.png";

	// Vertical movement bounds
	private static final double Y_UPPER_BOUND = 70;
	private static final double Y_LOWER_BOUND = 750.0;

	// Initial position of the user's plane
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;

	// Dimensions of the user's plane image
	private static final int IMAGE_HEIGHT = 45;

	// Vertical velocity for plane movement
	private static final int VERTICAL_VELOCITY = 8;

	// Projectile's horizontal position and vertical offset from plane
	private static final int PROJECTILE_X_POSITION = 180;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 30;

	// Velocity multiplier for controlling movement speed
	private int velocityMultiplier;

	// Number of kills the user has achieved
	private int numberOfKills;

	// The user's shield object
	private UserShield userShield;

	/**
	 * Constructs a new {@code UserPlane} object with the given initial health.
	 * Initializes the plane's image, position, and shield. The plane can be controlled
	 * by the player through keyboard input, and the shield can be toggled with the Enter key.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;

		// Initialize the shield
		this.userShield = new UserShield(this);

		// Set up key event handler for Enter key to toggle shield
		this.setOnKeyPressed(event -> handleKeyPress(event));
	}

	/**
	 * Handles key press events. Specifically, toggles the shield's activation
	 * when the Enter key is pressed.
	 *
	 * @param event the key event triggered by the player
	 */
	private void handleKeyPress(KeyEvent event) {
		// Check if Enter key is pressed
		if (event.getCode() == KeyCode.ENTER) {
			// Toggle the shield's state
			userShield.setShieldAllowed(!userShield.isShieldAllowed());
		}
	}

	/**
	 * Updates the position of the user plane by moving vertically and ensuring
	 * that it stays within the specified vertical bounds. It also updates the position
	 * of the shield if it is active.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();

			// Move the plane vertically based on velocity
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);

			double newPosition = getLayoutY() + getTranslateY();

			// If the new position is out of bounds, revert to the previous position
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}

			// Update shield position after moving
			userShield.updateShieldPosition();
		}
	}

	/**
	 * Updates the plane's state and position.
	 * This is a required method in the game's update cycle.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane. The projectile is created at the specified
	 * x and y coordinates relative to the plane.
	 *
	 * @return a new {@link UserProjectile} object representing the fired projectile
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the user plane is currently moving.
	 *
	 * @return true if the plane is moving (i.e., the velocity multiplier is non-zero),
	 *         false otherwise
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Moves the plane upward by setting the velocity multiplier to -1.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the plane downward by setting the velocity multiplier to 1.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the movement of the plane by setting the velocity multiplier to 0.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Returns the number of kills the user has achieved.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the number of kills by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Adds the user's shield to the game scene.
	 *
	 * @param root the root node of the scene to which the shield will be added
	 */
	public void addShieldToScene(Group root) {
		userShield.addShieldToScene(root);  // Adding the shield to the scene via UserShield
	}

	/**
	 * Sets whether the shield is allowed to be active or not.
	 *
	 * @param allowed true if the shield should be allowed, false otherwise
	 */
	public void setShieldAllowed(boolean allowed) {
		userShield.setShieldAllowed(allowed);
	}

	/**
	 * Updates the position of the shield, ensuring that it follows the user plane.
	 */
	public void updateShieldPosition() {
		if (userShield != null) {
			userShield.updateShieldPosition(); // Delegate to UserShield to update position
		}
	}

	/**
	 * Checks if the shield is currently allowed to be active.
	 *
	 * @return true if the shield is allowed, false otherwise
	 */
	public boolean isShieldAllowed() {
		return userShield.isShieldAllowed();
	}

}
