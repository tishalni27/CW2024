package com.example.demo.Levels;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.LevelCommonElements.Actor.FighterPlane;
import com.example.demo.LevelCommonElements.visuals.ExplosionImage;
import com.example.demo.User.UserPlane;
import com.example.demo.User.UserProjectile;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the actors in the game, including friendly units (such as the user's plane), enemy units, and projectiles.
 * <p>
 * This class handles spawning, updating, and removing actors in the game. It also manages collisions between various
 * types of actors (e.g., user projectiles and enemy units), and generates appropriate effects such as explosions.
 * </p>
 */
public class ActorManager {

    private final Group root;
    private final UserPlane user;
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final ImageView background;

    /**
     * Constructs an {@link ActorManager} to manage the actors in the game.
     *
     * @param root The {@link Group} where the game actors will be added.
     * @param user The user's plane (friendly unit).
     * @param background The background image of the game.
     */
    public ActorManager(Group root, UserPlane user, ImageView background) {
        this.root = root;
        this.user = user;
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.background = background;

        friendlyUnits.add(user); // Add the user plane to friendly units
    }

    /**
     * Fires a projectile from the user plane and adds it to the game.
     */
    public void fireUserProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        }
    }

    /**
     * Generates and spawns projectiles from enemy units.
     */
    public void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Spawns a new enemy projectile and adds it to the game.
     *
     * @param projectile The projectile to be added to the game.
     */
    public void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Updates the positions and states of all actors (planes and projectiles).
     */
    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    /**
     * Removes all destroyed actors from the game scene.
     */
    public void removeDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from the provided list and triggers explosion effects if necessary.
     *
     * @param actors The list of actors to check for destruction.
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
                .collect(Collectors.toList());

        for (ActiveActorDestructible destroyed : destroyedActors) {
            if (!(destroyed instanceof UserProjectile)) {
                // If it's an enemy, create an explosion
                if (destroyed instanceof FighterPlane) {
                    double explosionX = destroyed.getBoundsInParent().getMinX();
                    double explosionY = destroyed.getBoundsInParent().getMinY();
                    // Create and show the explosion
                    ExplosionImage explosion = new ExplosionImage(explosionX, explosionY);
                    root.getChildren().add(explosion);

                    // Pause and remove the explosion after a short delay
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(event -> root.getChildren().remove(explosion));
                    pause.play();
                }
            }
            // Remove destroyed actors from root and list
            root.getChildren().remove(destroyed);
            actors.remove(destroyed);
        }
    }

    /**
     * Detects and handles collisions between two groups of actors.
     *
     * @param actors1 The first list of actors to check for collisions.
     * @param actors2 The second list of actors to check for collisions.
     */
    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    /**
     * Handles collisions between friendly units (e.g., user plane) and enemy units.
     */
    public void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    public void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    public void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Adds a new enemy unit to the game.
     *
     * @param enemy The enemy unit to be added to the game.
     */
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Checks if the user plane has been destroyed.
     *
     * @return {@code true} if the user plane is destroyed, {@code false} otherwise.
     */
    public boolean isUserDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Gets the list of enemy units currently in the game.
     *
     * @return The list of enemy units.
     */
    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Gets the list of friendly units, including the user plane.
     *
     * @return The list of friendly units.
     */
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    /**
     * Gets the list of user projectiles currently in the game.
     *
     * @return The list of user projectiles.
     */
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Gets the list of enemy projectiles currently in the game.
     *
     * @return The list of enemy projectiles.
     */
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }
}
