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

public class ActorManager {

    private final Group root;
    private final UserPlane user;
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final ImageView background;

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

    // Spawning a user projectile
    public void fireUserProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        }
    }

    // Spawning enemy projectiles
    public void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    public void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    // Updates the actors (planes and projectiles)
    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    // Removes all destroyed actors from the scene
    public void removeDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

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

    // Collision detection between two groups of actors
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

    // Handle collisions specifically for friendly units and enemies
    public void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    // Handle collisions between user projectiles and enemies
    public void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    // Handle collisions between enemy projectiles and friendly units
    public void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    // Add enemy units to the game
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    // Check if the user plane is destroyed
    public boolean isUserDestroyed() {
        return user.isDestroyed();
    }

    // Get the list of enemy units
    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    // Get the list of friendly units (includes the user plane)
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    // Get the list of user projectiles
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    // Get the list of enemy projectiles
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }
}
