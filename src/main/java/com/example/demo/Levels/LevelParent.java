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
 * The abstract class representing a level in the game. It handles the main game mechanics such as managing the player, enemies,
 * projectiles, collisions, and the game loop.
 * <p>
 * The class extends {@link Observable} to allow observers to be notified of changes, such as when the level is completed.
 * </p>
 */
public abstract class LevelParent extends Observable {

	/**
	 * Constant representing the height adjustment of the screen.
	 */
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 120;

	/**
	 * Constant for the delay between each frame of the game loop in milliseconds.
	 */
	private static final int MILLISECOND_DELAY = 50;

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	protected UserPlane user;
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

	private CollisionHandler collisionHandler;

	/**
	 * Constructor for initializing the game level with essential components.
	 *
	 * @param backgroundImageName the background image for the level
	 * @param screenHeight        the height of the screen
	 * @param screenWidth         the width of the screen
	 * @param playerInitialHealth the initial health of the player
	 * @param controller          the game controller to handle game actions
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
		collisionHandler = new CollisionHandler(this);
		initializeScoreboard();
	}

	/**
	 * Initializes the scoreboard on the screen.
	 */
	private void initializeScoreboard() {
		this.scoreboard = new Scoreboard(20, 100); // Adjust position as needed
		scoreboard.displayScoreboard(root); // Add the scoreboard to the root
	}

	/**
	 * Abstract method for initializing the friendly units in the game.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method to check if the game is over.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method for spawning enemy units in the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method for instantiating the level view, which handles the display of level-specific visuals.
	 *
	 * @return the instantiated level view
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the game scene by setting up the background, friendly units, and the pause screen.
	 *
	 * @param stageWidth  the width of the game stage
	 * @param stageHeight the height of the game stage
	 * @return the initialized scene
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
	 * Starts the game by playing the timeline (game loop).
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Stops the game by halting the timeline (game loop).
	 */
	public void stopGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.stop(); // Stop game loop
		}
	}

	/**
	 * Pauses the game by pausing the timeline and setting the pause flag.
	 */
	public void pauseGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.pause(); // Pause the timeline
			isPaused = true; // Set to true to pause game
		}
	}

	/**
	 * Resumes the game by playing the timeline again and resetting the pause flag.
	 */
	public void resumeGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.PAUSED) {
			timeline.play(); // Resume the timeline
			isPaused = false;
			background.requestFocus();
		}
	}

	/**
	 * Checks if the game is currently paused.
	 *
	 * @return true if the game is paused, false otherwise
	 */
	public boolean isGamePaused() {
		return isPaused;
	}

	/**
	 * Initializes the pause screen with a pause button and event handlers.
	 *
	 * @param stageWidth  the width of the game stage
	 * @param stageHeight the height of the game stage
	 */
	private void initializePauseScreen(double stageWidth, double stageHeight) {
		PauseScreen pauseScreen = new PauseScreen(1240, 8, stageWidth, stageHeight, this);
		root.getChildren().add(pauseScreen.getContainer());
	}

	/**
	 * Notifies observers to transition to the next level.
	 *
	 * @param levelName the name of the next level
	 */
	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates the game scene by spawning enemies, updating actors, handling collisions, and managing game state.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		collisionHandler.handleUserProjectileCollisions(userProjectiles,enemyUnits);
		collisionHandler.handleEnemyProjectileCollisions(enemyProjectiles,friendlyUnits);
		collisionHandler.handlePlaneCollisions(friendlyUnits,enemyUnits);
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
		updateUserShieldPosition();
	}

	/**
	 * Initializes the game loop with a fixed delay between frames.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image for the level and sets up event handlers for player controls.
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
	 * Fires a projectile from the user's plane.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy fire by iterating through enemy units and having them fire projectiles.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns a projectile for an enemy unit and adds it to the scene.
	 *
	 * @param projectile the projectile to be spawned
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors (friendly units, enemy units, projectiles) by calling their individual update methods.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the game scene.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from the specified list and handles their visual effects (e.g., explosions).
	 *
	 * @param actors the list of actors to check for destruction
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
	 * Handles collisions between planes, both friendly and enemy.
	 */

	/**
	 * Handles generic collisions between two sets of actors.
	 *
	 * @param actors1 the first set of actors
	 * @param actors2 the second set of actors
	 */

	/**
	 * Handles the penetration of enemy units past the player's defenses.
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
	 * Penalty system to decrease score when collision occurs
	 */
	void decreaseScore(int amount) {
		score = Math.max(0, score - amount); // Ensures the score never goes below 0
		scoreText.setText("Score: " + score);
	}
	/**
	 * Updates the level view (e.g., removing hearts based on player health).
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the kill count and score based on the number of enemies destroyed.
	 */
	protected void updateKillCount() {
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
	 * Checks if an enemy unit has penetrated past the player's defenses.
	 *
	 * @param enemy the enemy unit to check
	 * @return true if the enemy has penetrated, false otherwise
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Ends the game with a win state, stopping the game loop and displaying a win message.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	/**
	 * Ends the game with a loss state, stopping the game loop and displaying a game over message.
	 * A retry button is also displayed to allow the player to retry the level.
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
	 * Handles the loss of a challenge, displaying the scoreboard and allowing the player to retry the level.
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
	 * Updates the user's shield position, if the user has one.
	 */
	protected void updateUserShieldPosition() {
		if (user != null) {
			user.updateShieldPosition();
		}
	}

	/**
	 * Retrieves the user's plane object.
	 *
	 * @return the user's plane
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Retrieves the root node of the game scene.
	 *
	 * @return the root node of the game scene
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Retrieves the current number of enemy units in the game.
	 *
	 * @return the number of enemy units
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the game and the root node.
	 *
	 * @param enemy the enemy unit to add
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Retrieves the maximum Y position for the enemy units.
	 *
	 * @return the maximum Y position for enemy units
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Retrieves the screen's width.
	 *
	 * @return the width of the screen
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Retrieves the screen's height.
	 *
	 * @return the height of the screen
	 */
	public double getScreenHeight() {
		return this.screenHeight;
	}

	/**
	 * Checks if the user's plane is destroyed.
	 *
	 * @return true if the user's plane is destroyed, false otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the current number of enemies based on the size of the enemy units list.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
}
