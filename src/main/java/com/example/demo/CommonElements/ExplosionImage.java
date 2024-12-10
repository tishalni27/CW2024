package com.example.demo.CommonElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExplosionImage extends ImageView {

    private static final int EXPLOSION_SIZE=100;

    public ExplosionImage(double xPosition, double yPosition){
        super();
        String IMAGE_NAME = getClass().getResource("/com/example/demo/images/explosion.png").toExternalForm();
        this.setImage(new Image(IMAGE_NAME));

        // Set size
        this.setFitHeight(EXPLOSION_SIZE);
        this.setFitWidth(EXPLOSION_SIZE);

        // Set position
        this.setX(xPosition);
        this.setY(yPosition);
    }
}
