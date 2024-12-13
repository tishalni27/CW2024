package com.example.demo.User;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest {

    private UserPlane userPlane;

    @BeforeEach
    void setUp() {
        // Initialize a UserPlane with some initial health, e.g., 100
        userPlane = new UserPlane(100);
    }

    @Test
    void testInitialPosition() {
        // Check that the initial Y position is correct
        assertEquals(300.0, userPlane.getTranslateY(), "Initial Y position should be 300.0");
    }

    @Test
    void testMoveUp() {
        // Move the plane up
        userPlane.moveUp();
        userPlane.updatePosition();

        // Check that the Y position has decreased as expected (because we move up with a negative velocity)
        assertTrue(userPlane.getTranslateY() < 300.0, "UserPlane should move upwards when moveUp() is called");
    }

    @Test
    void testMoveDown() {
        // Move the plane down
        userPlane.moveDown();
        userPlane.updatePosition();

        // Check that the Y position has increased as expected (because we move down with a positive velocity)
        assertTrue(userPlane.getTranslateY() > 300.0, "UserPlane should move downwards when moveDown() is called");
    }

    @Test
    void testStop() {
        // Stop the movement
        userPlane.stop();
        userPlane.updatePosition();

        // Check that the Y position hasn't changed after calling stop()
        assertEquals(300.0, userPlane.getTranslateY(), "UserPlane should not move after stop() is called");
    }

    @Test
    void testIncrementKillCount() {
        // Initial kill count should be 0
        assertEquals(0, userPlane.getNumberOfKills(), "Initial kill count should be 0");

        // Increment the kill count
        userPlane.incrementKillCount();

        // The kill count should now be 1
        assertEquals(1, userPlane.getNumberOfKills(), "Kill count should be incremented by 1");
    }

    @Test
    void testShieldToggle() {
        // Initially, the shield should be disabled
        assertFalse(userPlane.isShieldAllowed(), "Shield should initially be disabled");

        // Toggle the shield (this simulates pressing the Enter key)
        userPlane.handleKeyPress(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));

        // The shield should now be enabled
        assertTrue(userPlane.isShieldAllowed(), "Shield should be enabled after pressing Enter");

        // Toggle again (press Enter again)
        userPlane.handleKeyPress(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));

        // The shield should now be disabled again
        assertFalse(userPlane.isShieldAllowed(), "Shield should be disabled after pressing Enter again");
    }

    @Test
    void testUpdatePositionBounds() {
        // Move the plane upwards out of bounds
        userPlane.moveUp();
        for (int i = 0; i < 50; i++) {  // Keep moving up
            userPlane.updatePosition();
        }

        // Check that the Y position doesn't go beyond the upper bound (Y_UPPER_BOUND)
        assertTrue(userPlane.getTranslateY() >= 70, "UserPlane should not go above the upper bound");

        // Move the plane downwards out of bounds
        userPlane.moveDown();
        for (int i = 0; i < 50; i++) {  // Keep moving down
            userPlane.updatePosition();
        }

        // Check that the Y position doesn't go beyond the lower bound (Y_LOWER_BOUND)
        assertTrue(userPlane.getTranslateY() <= 750.0, "UserPlane should not go below the lower bound");
    }
}
