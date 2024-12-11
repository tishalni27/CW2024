package com.example.demo.Level3;


import com.example.demo.CommonElements.FighterPlane;
import com.example.demo.Level1.ActiveActorDestructible;
import com.example.demo.Level1.EnemyProjectile;

public class EnemyLevelThree extends FighterPlane {

    private static final String IMAGE_NAME = "enemyLevel3.png";
    private static final int IMAGE_HEIGHT = 70;
    private static final int HORIZONTAL_VELOCITY = -4;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 10;
    private static final double FIRE_RATE = .03;

    public EnemyLevelThree(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectile(projectileXPosition, projectileYPostion);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}

