package com.example.demo.User;

import com.example.demo.CommonElements.FighterPlane;
import com.example.demo.Level1.ActiveActorDestructible;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int numberOfKills;
	private ImageView shield;
	private boolean shieldAllowed; // Global flag for shield usage

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;

		shieldAllowed = false; // Default to false
		// Initialize shield
		shield = createShield();
		updateShieldPosition();

		// Set up key event handler for Enter key
		this.setOnKeyPressed(event -> handleKeyPress(event));
	}
	private ImageView createShield() {
		ImageView shield = new ImageView(new Image(getClass().getResource("/com/example/demo/images/Usershield.png").toExternalForm()));
		shield.setFitHeight(100);
		shield.setFitWidth(100);
		shield.setVisible(false); // Initially, shield is hidden
		return shield;
	}

	private void handleKeyPress(KeyEvent event) {
		// Check if Enter key is pressed
		if (event.getCode() == KeyCode.ENTER) {
			// Toggle shieldAllowed when Enter is pressed
			setShieldAllowed(!shieldAllowed);
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
			updateShieldPosition();
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
	public void updateShieldPosition() {
		if (shield != null) {
			double shieldX = this.getLayoutX() + 155; // Add offset for positioning
			double shieldY = this.getLayoutY() + 30 + this.getTranslateY();
			shield.setLayoutX(shieldX);
			shield.setLayoutY(shieldY);
		}
	}
	public void addShieldToScene(Group root) {
		if (!root.getChildren().contains(shield)) {
			root.getChildren().add(shield);
		}
		shield.setVisible(shieldAllowed && shield.isVisible()); // Make sure visibility matches the flag
	}

	// Method to enable or disable the shield
	public void setShieldAllowed(boolean allowed) {
		this.shieldAllowed = allowed;
		if (!allowed && shield != null) {
			shield.setVisible(false); // Hide the shield if globally disabled
		}
		if (allowed && shield != null) {
			shield.setVisible(true);
		}
	}

	public boolean isShieldAllowed() {
		return shieldAllowed;
	}

}
