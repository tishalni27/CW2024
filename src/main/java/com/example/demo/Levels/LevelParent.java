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
import com.example.demo.User.UserProjectile;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 120;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	//private final double enemyMinimumYPosition;

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
		//this.enemyMinimumYPosition = ;

		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
		this.killCountText = new Text(20, 40, "Kill Count : 0 / 10");
		this.scoreText = new Text(20, 70, "Score: 0");
		this.scoreboard = new Scoreboard(300, 100);
		initializeScoreboard();

	}

	private void initializeScoreboard() {
		this.scoreboard = new Scoreboard(20, 100); // Adjust position as needed
		System.out.println("Scoreboard initialized.");
		scoreboard.displayScoreboard(root); // Add the scoreboard to the root
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene(double stageWidth, double stageHeight) {
		root.getChildren().clear();
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		initializePauseScreen(stageWidth, stageHeight);
		background.requestFocus();
		// Initialize Scoreboard
		if (scoreboard == null) {
			scoreboard = new Scoreboard(100, 100);
			System.out.println("Scoreboard initialized during scene setup.");
		}
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
		System.out.println("Game started: " + this.getClass().getSimpleName());
	}

	//fixed timeline issue by adding stop game
	public void stopGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.stop();//stop game loop
		}
	}

	public void pauseGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.pause();//pause the timeline
			System.out.println("Game paused");//debugging statement
			isPaused = true; //set to true to pause game
		}
	}

	public void resumeGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.PAUSED) {
			System.out.println("Resuming game, Timeline status before play: " + timeline.getStatus());
			timeline.play(); // Resume the timeline
			System.out.println("Timeline status after play: " + timeline.getStatus());
			isPaused = false;
			background.requestFocus();
		}
	}

	public boolean isGamePaused() {
		// Return the current state of the game based on isPaused flag
		return isPaused;
	}

	/*public void retryGame() {
		stopGame();
		root.getChildren().clear();
		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();

		user.reset();
		levelView.reset(); // Reset LevelView elements

		initializeScene(screenWidth, screenHeight);
		startGame();

		System.out.println("Game restarted: " + this.getClass().getSimpleName());
	}*/

	private void initializePauseScreen(double stageWidth, double stageHeight) {
		PauseScreen pauseScreen = new PauseScreen(1240, 8, stageWidth, stageHeight, this);
		root.getChildren().add(pauseScreen.getContainer());
	}

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

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

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
		root.getChildren().add(background);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());

		//iterate over each destroyed actor
		for (ActiveActorDestructible destroyed : destroyedActors) {
			if (!(destroyed instanceof UserProjectile)) {
			//create explosion
				if (destroyed instanceof FighterPlane) {
					double explosionX = destroyed.getBoundsInParent().getMinX();
					double explosionY = destroyed.getBoundsInParent().getMinY();
					ExplosionImage explosion = new ExplosionImage(explosionX, explosionY);
					root.getChildren().add(explosion);

					// Pause transition for explosion effect
					PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
					pause.setOnFinished(event -> root.getChildren().remove(explosion));
					pause.play();
				}
			}
			root.getChildren().removeAll(destroyedActors);
			actors.removeAll(destroyedActors);
		}
	}
	/*private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());

		for (ActiveActorDestructible destroyed : destroyedActors) {
			double explosionX = destroyed.getBoundsInParent().getMinX();
			double explosionY = destroyed.getBoundsInParent().getMinY();
			// Check if the destroyed actor is an enemy
			if (destroyed instanceof FighterPlane) {
				ExplosionImage explosion = new ExplosionImage(explosionX, explosionY);
				root.getChildren().add(explosion);

				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
				pause.setOnFinished(event -> root.getChildren().remove(explosion));
				pause.play();
			}
		}

		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}*/

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			if (actor instanceof UserPlane && ((UserPlane) actor).isShieldAllowed()) {
				continue;  // Skip the collision check for the user plane if the shield is active
			}
			for (ActiveActorDestructible otherActor : actors1) {
				if (otherActor instanceof UserPlane && ((UserPlane) otherActor).isShieldAllowed()) {
					continue;  // Skip the collision check if the shield is active
				}
					if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
						actor.takeDamage();
						otherActor.takeDamage();
						if (actor instanceof UserPlane || otherActor instanceof UserPlane) {
							score -= 10;  // Subtract 10 points
							scoreText.setText("Score: " + score);  // Update the score display
						}
					}
			}
		}
	}
	/*private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			// Skip collision if the actor is the user and the shield is active
			if (actor instanceof UserPlane && ((UserPlane) actor).isShieldAllowed()) {
				continue;  // Skip the collision check for the user plane if the shield is active
			}

			for (ActiveActorDestructible otherActor : actors1) {
				// Skip collision if the actor is the user and the shield is active
				if (otherActor instanceof UserPlane && ((UserPlane) otherActor).isShieldAllowed()) {
					continue;  // Skip the collision check if the shield is active
				}

				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					if (actor instanceof UserPlane || otherActor instanceof UserPlane) {
						score -= 10;  // Subtract 10 points
						scoreText.setText("Score: " + score);  // Update the score display
					}
				}
			}
		}
	}*/

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

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

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();

	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();

		Button retryButton = new Button("Retry");
		retryButton.toFront();
		retryButton.setLayoutX(screenWidth / 2 - 50);
		retryButton.setLayoutY(screenHeight / 3 + 250);
		retryButton.setVisible(true);
		retryButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");

		// Add action to retry button
		if (controller != null) {
			retryButton.setOnAction(e -> controller.retryLevel());
		} else {
			System.out.println("Controller is not initialized!");
		}

		root.getChildren().add(retryButton);

	}

	protected void loseChallenge() {

		if (timeline != null) {
			timeline.stop();
		} else {
			System.out.println("Error: Timeline is null.");
		}

		scoreboard.addScore(score);  // Add the score to the scoreboard when the game is lost
		scoreboard.displayScoreboard(root);  // Display the scoreboard


		// Add Retry Button
		if (root != null) {
			Button retryButton = new Button("Retry");
			retryButton.setLayoutX(screenWidth / 2 - 50); // Center the button horizontally
			retryButton.setLayoutY(screenHeight / 3 + 200);     // Position it vertically in the middle
			retryButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;"); // Style the button
			if (controller != null) {
				retryButton.setOnAction(e -> controller.retryLevel());
			} else {
				System.out.println("Controller is not initialized!");
			}

			root.getChildren().add(retryButton);
		} else {
			System.out.println("Error: Root node is not initialized.");
		}

	}

	protected void updateUserShieldPosition() {
		if (user != null) {
			user.updateShieldPosition();
			//System.out.println("UserShield position updated in LevelParent.");
		} else {
			System.err.println("Error: User is null in LevelParent.");
		}
	}


	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}
	public double getScreenHeight() {
		return this.screenHeight;
	}


	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}


}
