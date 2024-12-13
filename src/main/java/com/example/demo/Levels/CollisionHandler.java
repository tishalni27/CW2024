package com.example.demo.Levels;

import com.example.demo.LevelCommonElements.Actor.ActiveActorDestructible;
import com.example.demo.User.UserPlane;

import java.util.List;

/**
 * Handles the collision detection and response between different actors which are projectiles and planes in the game.
 * <p>
 * This class provides methods for handling collisions between various types of game actors, such as friendly units,
 * enemy units, and projectiles. It also updates the state of the involved actors, including dealing damage and updating
 * the game score.
 * </p>
 */
public class CollisionHandler {
    private final LevelParent levelParent;

    /**
     * Constructor to initialize the CollisionHandler with the given level.
     *
     * @param levelParent the parent level that holds the game elements and logic
     */
    public CollisionHandler(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Handles collisions between two sets of actors by checking for intersection of their bounding boxes. If an intersection
     * is found, both actors take damage. Additionally, if the user plane is involved in a collision, the score is decreased.
     *
     * @param actors1 the first set of actors to check for collisions
     * @param actors2 the second set of actors to check for collisions
     */
    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            // Skip if the actor is the UserPlane and the shield is allowed
            if (actor instanceof UserPlane && ((UserPlane) actor).isShieldAllowed()) continue;

            for (ActiveActorDestructible otherActor : actors1) {
                // Skip if the other actor is the UserPlane and the shield is allowed
                if (otherActor instanceof UserPlane && ((UserPlane) otherActor).isShieldAllowed()) continue;

                // Check if the actors intersect
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();

                    // If either actor is a UserPlane, deduct score
                    if (actor instanceof UserPlane || otherActor instanceof UserPlane) {
                        levelParent.decreaseScore(10);
                    }
                }
            }
        }
    }

    /**
     * Handles collisions between user projectiles and enemy units by delegating the task to the generic collision handler.
     *
     * @param userProjectiles the list of projectiles fired by the user
     * @param enemyUnits the list of enemy units to check for collisions
     */
    public void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units by delegating the task to the generic collision handler.
     *
     * @param enemyProjectiles the list of projectiles fired by the enemy
     * @param friendlyUnits the list of friendly units to check for collisions
     */
    public void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles collisions between friendly units and enemy units which are planes by delegating the task to the generic collision handler.
     *
     * @param friendlyUnits the list of friendly units which is user plane
     * @param enemyUnits the list of enemy units, enemy planes to check for collisions
     */
    public void handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(friendlyUnits, enemyUnits);
    }
}
