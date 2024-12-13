package com.example.demo.LevelCommonElements.visuals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an explosion visual effect in the game.
 * <p>
 * This class extends {@link ImageView} and is used to display an explosion image at a specified
 * location on the screen. The image is scaled to a fixed size and placed at the provided coordinates.
 * </p>
 */
public class ExplosionImage extends ImageView {

    // Constant defining the size of the explosion image
    private static final int EXPLOSION_SIZE = 100;

    /**
     * Constructs an {@link ExplosionImage} and positions it at the specified coordinates.
     *
     * @param xPosition The X position of the explosion image on the screen.
     * @param yPosition The Y position of the explosion image on the screen.
     */
    public ExplosionImage(double xPosition, double yPosition) {
        super();

        // Load the explosion image from resources
        String IMAGE_NAME = getClass().getResource("/com/example/demo/images/explosion.png").toExternalForm();
        this.setImage(new Image(IMAGE_NAME));

        // Set the size of the explosion image
        this.setFitHeight(EXPLOSION_SIZE);
        this.setFitWidth(EXPLOSION_SIZE);

        // Set the position of the explosion image
        this.setX(xPosition);
        this.setY(yPosition);
    }
}
