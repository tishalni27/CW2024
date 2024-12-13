package com.example.demo.Level3;

import com.example.demo.User.UserPlane;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents the shield for the user's plane in Level Three.
 * It controls the shield's visibility, position, and whether it is enabled or not.
 */
public class UserShield {

    /** The reference to the user plane for positioning the shield. */
    private final UserPlane userPlane;

    /** The image view representing the shield. */
    private ImageView shield;

    /** Flag to indicate whether the shield is allowed to be visible or not. */
    private boolean shieldAllowed;

    /**
     * Constructor to initialize the UserShield with a reference to the user plane.
     *
     * @param userPlane The user plane to which the shield is attached.
     */
    public UserShield(UserPlane userPlane) {
        this.userPlane = userPlane;
        this.shieldAllowed = false;
        this.shield = createShield();
        updateShieldPosition();
    }

    /**
     * Creates and initializes the shield image view.
     * The shield is initially hidden.
     *
     * @return The image view of the shield.
     */
    private ImageView createShield() {
        ImageView shield = new ImageView(new Image(getClass().getResource("/com/example/demo/images/Usershield.png").toExternalForm()));
        shield.setFitHeight(120); // Set the shield's height
        shield.setFitWidth(120);  // Set the shield's width
        shield.setVisible(false); // Initially, the shield is hidden
        return shield;
    }

    /**
     * Updates the shield's position to align with the user plane.
     * The shield's position is offset relative to the user plane's position.
     */
    public void updateShieldPosition() {
        if (shield != null) {
            // Position the shield slightly offset from the user plane
            double shieldX = userPlane.getLayoutX() + 185; // Offset in X direction
            double shieldY = userPlane.getLayoutY() - 20 + userPlane.getTranslateY(); // Offset in Y direction
            shield.setLayoutX(shieldX);
            shield.setLayoutY(shieldY);
        }
    }

    /**
     * Adds the shield to the scene if it is not already present.
     * The shield will be visible if it is allowed.
     *
     * @param root The root group of the scene where the shield will be added.
     */
    public void addShieldToScene(Group root) {
        if (!root.getChildren().contains(shield)) {
            root.getChildren().add(shield);
        }
        shield.setVisible(shieldAllowed && shield.isVisible()); // Ensure visibility matches the flag
    }

    /**
     * Enables or disables the shield visibility.
     * If the shield is disabled, it will be hidden.
     *
     * @param allowed Flag indicating whether the shield is allowed (visible) or not.
     */
    public void setShieldAllowed(boolean allowed) {
        this.shieldAllowed = allowed;
        if (!allowed && shield != null) {
            shield.setVisible(false); // Hide the shield if globally disabled
        }
        if (allowed && shield != null) {
            shield.setVisible(true); // Show the shield if enabled
        }
    }

    /**
     * Checks if the shield is allowed (visible).
     *
     * @return True if the shield is allowed to be visible, false otherwise.
     */
    public boolean isShieldAllowed() {
        return shieldAllowed;
    }

    /**
     * Gets the image view of the shield.
     *
     * @return The image view representing the shield.
     */
    public ImageView getShield() {
        return shield;
    }
}
