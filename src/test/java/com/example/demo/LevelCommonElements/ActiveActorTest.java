package com.example.demo.LevelCommonElements;

import com.example.demo.LevelCommonElements.Actor.ActiveActor;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ActiveActor class.
 */
class ActiveActorTest {

    // Concrete subclass of ActiveActor for testing
    static class TestActor extends ActiveActor {
        public TestActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            // Dummy implementation for testing
        }
    }

    private TestActor testActor;

    @BeforeEach
    void setUp() {
        testActor = new TestActor("test-image.png", 100, 50, 50);
    }

    @Test
    void testConstructor_initialization() {
        assertNotNull(testActor.getImage(), "Image should be initialized");
        assertEquals(50, testActor.getLayoutX(), "Initial X position should be set correctly");
        assertEquals(50, testActor.getLayoutY(), "Initial Y position should be set correctly");
        assertEquals(100, testActor.getFitHeight(), "Image height should be set correctly");
        assertTrue(testActor.isPreserveRatio(), "Image ratio should be preserved");
    }

    @Test
    void testMoveHorizontally() {
        testActor.moveHorizontally(20);
        assertEquals(20, testActor.getTranslateX(), "Actor should move 20 units horizontally");

        testActor.moveHorizontally(-10);
        assertEquals(10, testActor.getTranslateX(), "Actor should move back 10 units horizontally");
    }

    @Test
    void testMoveVertically() {
        testActor.moveVertically(30);
        assertEquals(30, testActor.getTranslateY(), "Actor should move 30 units vertically");

        testActor.moveVertically(-15);
        assertEquals(15, testActor.getTranslateY(), "Actor should move back 15 units vertically");
    }
}
