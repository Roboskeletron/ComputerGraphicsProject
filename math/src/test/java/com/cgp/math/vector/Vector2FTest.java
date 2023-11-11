package com.cgp.math.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2FTest {
    @Test
    public void additionTest() {
        Vector2F v1 = new Vector2F(1, 2);
        Vector2F v2 = new Vector2F(3, 4);
        Vector2F result = v1.add(v2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void subtractionTest() {
        Vector2F v1 = new Vector2F(1, 2);
        Vector2F v2 = new Vector2F(3, 4);
        Vector2F result = v1.subtract(v2);
        assertEquals(-2, result.getX());
        assertEquals(-2, result.getY());
    }

    @Test
    public void scalarMultiplyTest() {
        Vector2F v1 = new Vector2F(1, 2);
        Vector2F result = v1.scalarMultiply(3);
        assertEquals(3, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void dotProductTest() {
        Vector2F v1 = new Vector2F(1, 2);
        Vector2F v2 = new Vector2F(3, 4);
        double result = v1.dotProduct(v2);
        assertEquals(11, result);
    }

    @Test
    public void magnitudeTest() {
        Vector2F v = new Vector2F(3, 4);
        assertEquals(5, v.magnitude(), 0.00001);
    }

    @Test
    public void normalizeTest() {
        Vector2F v = new Vector2F(1, 0);
        Vector2F result = v.normalize();
        assertEquals(1, result.getX(), 0.00001);
        assertEquals(0, result.getY(), 0.00001);
    }
}