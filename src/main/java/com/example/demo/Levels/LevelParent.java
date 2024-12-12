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

	private void initializeScoreboard() {
		this.scoreboard = new Scoreboard(20, 100); // Adjust position as needed
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
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void stopGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.stop(); // Stop game loop
		}
	}

	public void pauseGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
			timeline.pause(); // Pause the timeline
			isPaused = true; // Set to true to pause game
		}
	}

	public void resumeGame() {
		if (timeline != null && timeline.getStatus() == Animation.Status.PAUSED) {
			timeline.play(); // Resume the timeline
			isPaused = false;
			background.requestFocus();
		}
	}

	public boolean isGamePaused() {
		return isPaused;
	}

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

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

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
		retryButton.setLayoutX(screenWidth / 2 - 50);
		retryButton.setLayoutY(screenHeight / 3 + 250);
		retryButton.setVisible(true);
		retryButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");

		if (controller != null) {
			retryButton.setOnAction(e -> controller.retryLevel());
		}

		root.getChildren().add(retryButton);
	}

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

	protected void updateUserShieldPosition() {
		if (user != null) {
			user.updateShieldPosition();
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
