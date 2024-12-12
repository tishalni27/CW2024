package com.example.demo.Level3;

import com.example.demo.User.UserPlane;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserShield {

    private final UserPlane userPlane; // Reference to the user plane for positioning
    private ImageView shield;
    private boolean shieldAllowed;

    public UserShield(UserPlane userPlane) {
        this.userPlane = userPlane;
        this.shieldAllowed = false;
        this.shield = createShield();
        updateShieldPosition();
    }

    private ImageView createShield() {
        ImageView shield = new ImageView(new Image(getClass().getResource("/com/example/demo/images/Usershield.png").toExternalForm()));
        shield.setFitHeight(120);
        shield.setFitWidth(120);
        shield.setVisible(false); // Initially, the shield is hidden
        return shield;
    }

    public void updateShieldPosition() {
        if (shield != null) {
            double shieldX = userPlane.getLayoutX() + 185; // Add offset for positioning
            double shieldY = userPlane.getLayoutY() + (-20) + userPlane.getTranslateY();
            shield.setLayoutX(shieldX);
            shield.setLayoutY(shieldY);
        }
    }

    public void addShieldToScene(Group root) {
        if (!root.getChildren().contains(shield)) {
            root.getChildren().add(shield);
        }
        shield.setVisible(shieldAllowed && shield.isVisible()); // Ensure visibility matches the flag
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

    public ImageView getShield() {
        return shield;
    }
}
