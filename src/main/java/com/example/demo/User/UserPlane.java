package com.example.demo.User;

import com.example.demo.Level3.UserShield;
import com.example.demo.LevelCommonElements.Actor.FighterPlane;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 70;
	private static final double Y_LOWER_BOUND = 750.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 45;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 180;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 30;
	private int velocityMultiplier;
	private int numberOfKills;
	private UserShield userShield;


	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;

		//shieldAllowed = false; // Default to false
		// Initialize shield
		//shield = createShield();
		//updateShieldPosition();
		this.userShield = new UserShield(this);
		// Set up key event handler for Enter key
		this.setOnKeyPressed(event -> handleKeyPress(event));
	}

	private void handleKeyPress(KeyEvent event) {
		// Check if Enter key is pressed
		if (event.getCode() == KeyCode.ENTER) {
			// Toggle shieldAllowed when Enter is pressed
			userShield.setShieldAllowed(!userShield.isShieldAllowed());
		}
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}

			// Update shield position after moving
			userShield.updateShieldPosition();
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	/*public void reset(){
		// Reset position
		this.setTranslateX(0); // Reset horizontal translation
		this.setTranslateY(0); // Reset vertical translation
		this.setLayoutX(INITIAL_X_POSITION); // Set to initial X position
		this.setLayoutY(INITIAL_Y_POSITION); // Set to initial Y position

		//reset velocity multiplier
		this.velocityMultiplier = 0;
		this.numberOfKills =0;

	}*/
	public void addShieldToScene(Group root) {
		userShield.addShieldToScene(root);  // Adding the shield to the scene via UserShield
	}
	public void setShieldAllowed(boolean allowed) {
		userShield.setShieldAllowed(allowed);
	}

	public void updateShieldPosition() {
		if (userShield != null) {
			userShield.updateShieldPosition(); // Delegate to UserShield to update position
		}
	}


	public boolean isShieldAllowed() {
		return userShield.isShieldAllowed();
	}

}
