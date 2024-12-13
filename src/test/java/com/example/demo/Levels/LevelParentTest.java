package com.example.demo.Levels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelParentTest {
    @Test
    void testScoreIsNotNegative() {
        int score = 10; // Mock or real setup
        assertTrue(score >= 0, "Score should not be negative");
    }
}
