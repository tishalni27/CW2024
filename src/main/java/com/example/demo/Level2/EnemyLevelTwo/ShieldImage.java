package com.example.demo.Level2.EnemyLevelTwo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	//private static final String IMAGE_NAME = "/images/shield.png";
	private static final int SHIELD_WIDTH = 80;
	private static final int SHIELD_HEIGHT= 131;
	public ShieldImage(double xPosition, double yPosition) {
		String IMAGE_NAME = getClass().getResource("/com/example/demo/images/shield.png").toExternalForm();
		this.setImage(new Image(IMAGE_NAME));

		this.setImage(new Image(IMAGE_NAME));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);

		//this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		this.setVisible(true);//visible by default
		this.setFitHeight(SHIELD_HEIGHT);
		this.setFitWidth(SHIELD_WIDTH);
	}

	public void showShield(double xPosition, double yPosition) {
		this.setVisible(true);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		System.out.println("Shield shown at: (" + xPosition + ", " + yPosition + ")");
	}
	
	public void hideShield() {
		this.setVisible(false);
	}

}
