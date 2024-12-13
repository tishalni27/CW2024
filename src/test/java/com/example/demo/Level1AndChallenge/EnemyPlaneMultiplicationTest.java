package com.example.demo.Level1AndChallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for multiplication-related functionality in the EnemyPlane class.
 */
public class EnemyPlaneMultiplicationTest {

    /**
     * A helper method to simulate multiplication for testing purposes.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the result of multiplying a and b
     */
    private int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Tests the multiplication logic with positive integers.
     */
    @Test
    public void testMultiplyPositiveNumbers() {
        int result = multiply(6, 7);
        Assertions.assertEquals(42, result, "6 * 7 should equal 42");
    }

    /**
     * Tests the multiplication logic with a negative and a positive integer.
     */
    @Test
    public void testMultiplyNegativeAndPositiveNumber() {
        int result = multiply(-6, 7);
        Assertions.assertEquals(-42, result, "-6 * 7 should equal -42");
    }

    /**
     * Tests the multiplication logic with zero.
     */
    @Test
    public void testMultiplyWithZero() {
        int result = multiply(6, 0);
        Assertions.assertEquals(0, result, "6 * 0 should equal 0");
    }

    /**
     * Tests the multiplication logic with two negative integers.
     */
    @Test
    public void testMultiplyNegativeNumbers() {
        int result = multiply(-6, -7);
        Assertions.assertEquals(42, result, "-6 * -7 should equal 42");
    }
}
