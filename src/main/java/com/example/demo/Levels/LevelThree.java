package com.example.demo.Levels;

import com.example.demo.Level1.ActiveActorDestructible;
import com.example.demo.Level3.EnemyLevelThree;
import com.example.demo.LevelView;
import com.example.demo.User.UserPlane;
import com.example.demo.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";
    private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    private long lastShieldUsedTime = 0; // Time when the shield was last activated
    private Text shieldMessage;  // Text element to show the shield activation message
    private static final double ENEMY_SPAWN_MIN_Y = 100.0;  // Minimum Y position for enemy spawn
    private static final double ENEMY_SPAWN_MAX_Y = 700.0;  // Maximum Y position for enemy spawn

    public LevelThree(double screenHeight, double screenWidth, Controller controller) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            winGame();
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Enable the shield for the UserPlane
        if (getUser() instanceof UserPlane) {
            UserPlane userPlane = (UserPlane) getUser();
            userPlane.setShieldAllowed(false); // Initially, the shield is disabled
            userPlane.addShieldToScene(getRoot()); // Add the shield to the scene
        }

        // Add the user plane to the root
        getRoot().getChildren().add(getUser());

        // Add kill count text

        killCountText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        killCountText.setLayoutX(650); // Adjusted X position
        killCountText.setLayoutY(30);  // Adjusted Y position
        killCountText.toFront();      // Ensure it's rendered above other elements
        getRoot().getChildren().add(killCountText);

        // Create the message that will prompt the user to use the shield
        shieldMessage = new Text("Press Enter to Use shield");
        shieldMessage.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: yellow;");
        shieldMessage.setLayoutX(600); // Adjust X position
        shieldMessage.setLayoutY(97); // Adjust Y position
        getRoot().getChildren().add(shieldMessage);

        // Initially show the "Use shield" message
        shieldMessage.setVisible(true);

        // Set up key event listener to activate shield with Enter key
        getRoot().setOnKeyPressed(event -> handleKeyPress(event));
    }

    private void handleKeyPress(KeyEvent event) {
        // Check if Enter key is pressed to activate shield
        if (event.getCode() == KeyCode.ENTER) {
            if (getUser() instanceof UserPlane) {
                UserPlane userPlane = (UserPlane) getUser();

                // Only activate the shield if it hasn't been used in the last 10 seconds
                long currentTime = System.currentTimeMillis();
                if (!userPlane.isShieldAllowed() && (currentTime - lastShieldUsedTime) >= 10000) {
                    userPlane.setShieldAllowed(true); // Activate the shield
                    System.out.println("Shield activated");

                    // Hide the "Use shield" message as the shield is now activated
                    shieldMessage.setVisible(false);

                    // Update last shield used time
                    lastShieldUsedTime = currentTime;

                    // Automatically deactivate shield after 5 seconds
                    deactivateShieldAfterTimeout(userPlane);
                } else {
                    System.out.println("Shield cannot be used yet. Wait for 10 seconds.");
                }
            }
        }
    }

    // Method to deactivate the shield after 5 seconds
    private void deactivateShieldAfterTimeout(UserPlane userPlane) {
        // Create a timeline to wait 5 seconds before deactivating the shield
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(7), event -> {
                    userPlane.setShieldAllowed(false); // Deactivate shield
                    System.out.println("Shield deactivated after 5 seconds");

                    // After deactivating the shield, wait for 10 seconds before showing the message again
                    waitForShieldCooldown();
                })
        );
        timeline.setCycleCount(1); // The timeline runs only once
        timeline.play(); // Start the timeline
    }

    // Method to handle the cooldown period and show the "Use shield" message again
    private void waitForShieldCooldown() {
        // Create a timeline to wait 10 seconds before showing the "Use shield" message again
        Timeline cooldownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    // After the cooldown period, show the "Use shield" message again
                    shieldMessage.setVisible(true);
                    System.out.println("You can use the shield again!");
                })
        );
        cooldownTimeline.setCycleCount(1); // The timeline runs only once
        cooldownTimeline.play(); // Start the cooldown timeline
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {

                //custom bounds for bigger enemies
                double enemyY = ENEMY_SPAWN_MIN_Y + Math.random() * (ENEMY_SPAWN_MAX_Y - ENEMY_SPAWN_MIN_Y);
                ActiveActorDestructible newEnemy = new EnemyLevelThree(getScreenWidth(), enemyY);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}