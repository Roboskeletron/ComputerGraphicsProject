package ru.vsu.cs.filozop;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.Math;

public class Vector2Test {
    @Test
    public void additionTest() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        Vector2 result = v1.add(v2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void subtractionTest() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        Vector2 result = v1.subtract(v2);
        assertEquals(-2, result.getX());
        assertEquals(-2, result.getY());
    }

    @Test
    public void scalarMultiplyTest() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 result = v1.scalarMultiply(3);
        assertEquals(3, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    public void dotProductTest() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        double result = v1.dotProduct(v2);
        assertEquals(11, result);
    }

    @Test
    public void magnitudeTest() {
        Vector2 v = new Vector2(3, 4);
        assertEquals(5, v.magnitude(), 0.00001);
    }

    @Test
    public void normalizeTest() {
        Vector2 v = new Vector2(1, 0);
        Vector2 result = v.normalize();
        assertEquals(1, result.getX(), 0.00001);
        assertEquals(0, result.getY(), 0.00001);
    }
}