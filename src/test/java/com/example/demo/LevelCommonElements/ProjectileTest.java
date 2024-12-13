package com.example.demo.LevelCommonElements;

import com.example.demo.LevelCommonElements.Actor.Projectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Projectile class.
 */
class ProjectileTest {

    // Concrete subclass of Projectile for testing
    static class TestProjectile extends Projectile {
        public TestProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            // Dummy implementation for testing
            moveHorizontally(5);
        }

        @Override
        public void updateActor() {

        }
    }

    private TestProjectile testProjectile;

    @BeforeEach
    void setUp() {
        testProjectile = new TestProjectile("test-projectile.png", 50, 100, 200);
    }

    @Test
    void testConstructor_initialization() {
        assertNotNull(testProjectile.getImage(), "Image should be initialized");
        assertEquals(100, testProjectile.getLayoutX(), "Initial X position should be set correctly");
        assertEquals(200, testProjectile.getLayoutY(), "Initial Y position should be set correctly");
        assertEquals(50, testProjectile.getFitHeight(), "Image height should be set correctly");
    }

    @Test
    void testTakeDamage() {
        testProjectile.takeDamage();
        assertFalse(testProjectile.isVisible(), "Projectile should be destroyed and not visible");
    }

    @Test
    void testUpdatePosition() {
        testProjectile.updatePosition();
        assertEquals(5, testProjectile.getTranslateX(), "Projectile should move 5 units horizontally after update");
    }
}
