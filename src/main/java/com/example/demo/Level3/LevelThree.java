package com.example.demo.Level3;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.LevelView;
import com.example.demo.Levels.LevelParent;
import com.example.demo.User.UserPlane;
import com.example.demo.controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.example.demo.Level3.LevelThreeConfig.*;

/**
 * Represents the third level of the game. This class extends {@link LevelParent} and introduces
 * a unique mechanic of shield usage for the player's plane and spawning enemies.
 */
public class LevelThree extends LevelParent {

    private long lastShieldUsedTime = 0; // Time when the shield was last activated
    private Text shieldMessage;  // Text element to show the shield activation message

    /**
     * Constructs a new LevelThree.
     * Initializes the level with a background image, screen height and width, and the controller.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth The width of the game screen.
     * @param controller The game controller.
     */
    public LevelThree(double screenHeight, double screenWidth, GameController controller) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, controller);
    }

    /**
     * Checks if the game is over by determining whether the user has been destroyed
     * or has reached the kill target to win the level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            winGame();
        }
    }

    /**
     * Initializes the friendly units, adds the user plane to the scene,
     * and sets up the shield and kill count display.
     */
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

    /**
     * Handles key press events, specifically checking for the Enter key to activate the shield.
     *
     * @param event The key event.
     */
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

    /**
     * Deactivates the shield after 7 seconds and starts the cooldown period before the shield can be used again.
     *
     * @param userPlane The user's plane that owns the shield.
     */
    private void deactivateShieldAfterTimeout(UserPlane userPlane) {
        // Create a timeline to wait 7 seconds before deactivating the shield
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(7), event -> {
                    userPlane.setShieldAllowed(false); // Deactivate shield
                    System.out.println("Shield deactivated after 7 seconds");

                    // After deactivating the shield, wait for cooldown before showing the message again
                    waitForShieldCooldown();
                })
        );
        timeline.setCycleCount(1); // The timeline runs only once
        timeline.play(); // Start the timeline
    }

    /**
     * Handles the cooldown period and shows the "Use shield" message again after 5 seconds.
     */
    private void waitForShieldCooldown() {
        // Create a timeline to wait 5 seconds before showing the "Use shield" message again
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

    /**
     * Spawns enemy units at random positions on the screen based on a defined spawn probability.
     * The method ensures that a certain number of enemies are present on the screen at all times.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {

                // Custom bounds for spawning enemies
                double enemyY = ENEMY_SPAWN_MIN_Y + Math.random() * (ENEMY_SPAWN_MAX_Y - ENEMY_SPAWN_MIN_Y);
                ActiveActorDestructible newEnemy = new EnemyLevelThree(getScreenWidth(), enemyY);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates the level view for LevelThree.
     *
     * @return A new instance of {@link LevelView} for LevelThree.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks whether the user has reached the kill target to advance to the next level.
     *
     * @return true if the user has reached the kill target, otherwise false.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
