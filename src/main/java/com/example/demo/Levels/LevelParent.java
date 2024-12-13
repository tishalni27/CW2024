package com.example.demo.Levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.LevelCommonElements.visuals.ExplosionImage;
import com.example.demo.LevelCommonElements.Actor.FighterPlane;
import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.LevelChallenge.Scoreboard;
import com.example.demo.LevelView;
import com.example.demo.Screen.Pause.PauseScreen;
import com.example.demo.User.UserPlane;
import com.example.demo.controller.GameController;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 * Abstract class representing a level in the game.
 * It handles the gameplay logic, actor management (e.g. user plane, enemy units), collision detection, score updates,
 * level transitions, and other key functionalities like pausing and resuming the game.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 120;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	private boolean isPaused = false;
	private LevelView levelView;

	private GameController controller;
	protected Text killCountText;
	private int previousKillCount = 0;
	protected Text scoreText;
	private int score = 0;

	private Scoreboard scoreboard;

	/**
	 * Constructs a new level with specified parameters for background image, screen size, player health, and game controller.
	 *
	 * @param backgroundImageName The image file name for the background.
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 * @param playerInitialHealth The initial health of the player.
	 * @param controller The game controller to manage game flow.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, GameController controller) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.controller = controller;

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;

		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
		this.killCountText = new Text(20, 40, "Kill Count : 0 / 10");
		this.scoreText = new Text(20, 70, "Score: 0");
		this.scoreboard = new Scoreboard(300, 100);
		initializeScoreboard();
	}

	/**
	 * Initializes the scoreboard to display on the screen.
	 */
	private void initializeScoreboard() {
		this.scoreboard = new Scoreboard(20, 100); // Adjust position as needed
		scoreboard.displayScoreboard(root); // Add the scoreboard to the root
	}

	/**
	 * Abstract method to initialize friendly units for the level.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method to check if the game is over.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method to spawn enemy units for the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method to instantiate a specific level view (e.g., heart display, score display).
	 *
	 * @return The instantiated level view.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the game scene with background, actors, and other level-specific elements.
	 *
	 * @param stageWidth The width of the game window.
	 * @param stageHeight The height of the game window.
	 * @return The initialized scene.
	 */
	public Scene initializeScene(double stageWidth, double stageHeight) {
		root.getChildren().clear();
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		initializePauseScreen(stageWidth, stageHeight);
		background.requestFocus();
		return scene;
	}

	/**
	 * Starts the game by playing the timeline animation.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Stops the game by stopping the timeline animation.
	 */
	public void stopGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.stop(); // Stop game loop
		}
	}

	/**
	 * Pauses the game by pausing the timeline animation.
	 */
	public void pauseGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.pause(); // Pause the timeline
			isPaused = true; // Set to true to pause game
		}
	}

	/**
	 * Resumes the game by playing the timeline animation.
	 */
	public void resumeGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.PAUSED) {
			timeline.play(); // Resume the timeline
			isPaused = false;
			background.requestFocus();
		}
	}

	/**
	 * Returns whether the game is currently paused.
	 *
	 * @return True if the game is paused, false otherwise.
	 */
	public boolean isGamePaused() {
		return isPaused;
	}

	/**
	 * Initializes the pause screen, allowing the user to pause the game.
	 *
	 * @param stageWidth The width of the stage.
	 * @param stageHeight The height of the stage.
	 */
	private void initializePauseScreen(double stageWidth, double stageHeight) {
		PauseScreen pauseScreen = new PauseScreen(1240, 8, stageWidth, stageHeight, this);
		root.getChildren().add(pauseScreen.getContainer());
	}

	/**
	 * Advances to the next level by notifying observers.
	 *
	 * @param levelName The name of the next level to transition to.
	 */
	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates the scene by spawning enemy units, updating actors, checking collisions, etc.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
		updateUserShieldPosition();
	}

	/**
	 * Initializes the game loop timeline that updates the scene at regular intervals.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image and sets up key event handlers for user input.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP) user.moveUp();
			if (kc == KeyCode.DOWN) user.moveDown();
			if (kc == KeyCode.SPACE) fireProjectile();
		});
		background.setOnKeyReleased(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
		});
		root.getChildren().add(background);
	}

	/**
	 * Fires a projectile from the user's plane and adds it to the scene.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy fire by having each enemy shoot a projectile.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns an enemy projectile and adds it to the scene.
	 *
	 * @param projectile The enemy projectile to spawn.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors (e.g., user plane, enemy units, projectiles) by calling their respective update methods.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the scene.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from a list and handles explosions if necessary.
	 *
	 * @param actors The list of actors to check for destruction.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());

		for (ActiveActorDestructible destroyed : destroyedActors) {
			if (destroyed instanceof FighterPlane) {
				double explosionX = destroyed.getBoundsInParent().getMinX();
				double explosionY = destroyed.getBoundsInParent().getMinY();
				ExplosionImage explosion = new ExplosionImage(explosionX, explosionY);
				root.getChildren().add(explosion);

				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
				pause.setOnFinished(event -> root.getChildren().remove(explosion));
				pause.play();
			}
		}
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between friendly and enemy units.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Generic method to handle collisions between two lists of actors.
	 *
	 * @param actors1 The first list of actors.
	 * @param actors2 The second list of actors.
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			if (actor instanceof UserPlane && ((UserPlane) actor).isShieldAllowed()) continue;
			for (ActiveActorDestructible otherActor : actors1) {
				if (otherActor instanceof UserPlane && ((UserPlane) otherActor).isShieldAllowed()) continue;
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					if (actor instanceof UserPlane || otherActor instanceof UserPlane) {
						score -= 10;
						scoreText.setText("Score: " + score);
					}
				}
			}
		}
	}

	/**
	 * Checks if any enemy units have penetrated the user's defenses (i.e., crossed the screen).
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * Updates the level view, such as removing hearts based on the player's current health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the kill count and score when enemies are destroyed.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
			score += 10;
		}
		if (user.getNumberOfKills() != previousKillCount) {
			killCountText.setText("Kill Count :  " + user.getNumberOfKills() + "/10");
			scoreText.setText("Score: " + score);
			previousKillCount = user.getNumberOfKills();
		}
	}

	/**
	 * Checks if an enemy unit has crossed the screen width, thus penetrating the player's defenses.
	 *
	 * @param enemy The enemy actor to check.
	 * @return True if the enemy has penetrated the defenses, false otherwise.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Ends the game with a win, stopping the timeline and displaying the win image.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	/**
	 * Ends the game with a loss, stopping the timeline and displaying the game over image along with a retry button.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();

		Button retryButton = new Button("Retry");
		retryButton.setLayoutX(screenWidth / 2 - 50);
		retryButton.setLayoutY(screenHeight / 3 + 250);
		retryButton.setVisible(true);
		retryButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");

		if (controller != null) {
			retryButton.setOnAction(e -> controller.retryLevel());
		}

		root.getChildren().add(retryButton);
	}

	/**
	 * Handles the end of the challenge, stopping the game and displaying the scoreboard.
	 */
	protected void loseChallenge() {
		if (timeline != null) {
			timeline.stop();
		}

		scoreboard.addScore(score);
		scoreboard.displayScoreboard(root);

		if (root != null) {
			Button retryButton = new Button("Retry");
			retryButton.setLayoutX(screenWidth / 2 - 50);
			retryButton.setLayoutY(screenHeight / 3 + 200);
			retryButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
			if (controller != null) {
				retryButton.setOnAction(e -> controller.retryLevel());
			}

			root.getChildren().add(retryButton);
		}
	}

	/**
	 * Updates the position of the user's shield.
	 */
	protected void updateUserShieldPosition() {
		if (user != null) {
			user.updateShieldPosition();
		}
	}

	/**
	 * Gets the user plane.
	 *
	 * @return The user plane.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Gets the root group of the scene.
	 *
	 * @return The root group.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Gets the current number of enemies in the game.
	 *
	 * @return The number of enemy units.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds a new enemy unit to the game.
	 *
	 * @param enemy The enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Gets the maximum Y position for enemy units.
	 *
	 * @return The maximum Y position for enemies.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Gets the screen width of the game.
	 *
	 * @return The screen width.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Gets the screen height of the game.
	 *
	 * @return The screen height.
	 */
	public double getScreenHeight() {
		return this.screenHeight;
	}

	/**
	 * Checks if the user plane is destroyed.
	 *
	 * @return True if the user plane is destroyed, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the number of enemy units in the game.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
}
