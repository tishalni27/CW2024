package com.example.demo.Levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.CommonElements.ExplosionImage;
import com.example.demo.CommonElements.FighterPlane;
import com.example.demo.Level1.ActiveActorDestructible;
import com.example.demo.LevelView;
import com.example.demo.Screen.PauseScreen;
import com.example.demo.User.UserPlane;
import com.example.demo.User.UserProjectile;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
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
	private boolean isPaused= false;
	private LevelView levelView;
	protected Text killCountText;
	private int previousKillCount = 0;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
		this.killCountText = new Text(20,40,"Kill Count : 0 / 10");
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene(double stageWidth,double stageHeight) {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		initializeUI(stageWidth, stageHeight);
		background.requestFocus();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
		System.out.println("Game started: "+ this.getClass().getSimpleName());
	}

	//fixed timeline issue by adding stop game
	public void stopGame(){
		if (timeline !=null && timeline.getStatus()==Animation.Status.RUNNING){
			timeline.stop();//stop game loop
		}
	}

	public void pauseGame(){
		if(timeline !=null && timeline.getStatus()==Animation.Status.RUNNING){
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
		}
	}

	public boolean isGamePaused() {
		// Return the current state of the game based on isPaused flag
		return isPaused;
	}

	public void retryGame() {
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
	}

	private void initializeUI(double stageWidth, double stageHeight){
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
				double explosionX = destroyed.getBoundsInParent().getMinX();
				double explosionY = destroyed.getBoundsInParent().getMinY();
				ExplosionImage explosion = new ExplosionImage(explosionX, explosionY);
				root.getChildren().add(explosion);

				// Pause transition for explosion effect
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
				pause.setOnFinished(event -> root.getChildren().remove(explosion));
				pause.play();
			}
			root.getChildren().removeAll(destroyedActors);
			actors.removeAll(destroyedActors);
		}
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

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
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
		}
		if (user.getNumberOfKills() !=previousKillCount){
			killCountText.setText("Kill Count :  " + user.getNumberOfKills()+ "/10");
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

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
