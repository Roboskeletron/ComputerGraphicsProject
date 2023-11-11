package com.cgp.math.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3FTest {

    @Test
    public void constructorTest() {
        Vector3F vector = new Vector3F(1.0F, 2.0F, 3.0F);
        assertEquals(1.0, vector.getX());
        assertEquals(2.0, vector.getY());
        assertEquals(3.0, vector.getZ());
    }

    @Test
    public void addTest() {
        Vector3F v1 = new Vector3F(1.0F, 2.0F, 3.0F);
        Vector3F v2 = new Vector3F(4.0F, 5.0F, 6.0F);
        Vector3F sum = v1.add(v2);
        assertEquals(5.0, sum.getX());
        assertEquals(7.0, sum.getY());
        assertEquals(9.0, sum.getZ());
    }

    @Test
    public void subtractTest() {
        Vector3F v1 = new Vector3F(4.0F, 5.0F, 6.0F);
        Vector3F v2 = new Vector3F(1.0F, 2.0F, 3.0F);
        Vector3F difference = v1.subtract(v2);
        assertEquals(3.0, difference.getX());
        assertEquals(3.0, difference.getY());
        assertEquals(3.0, difference.getZ());
    }

    @Test
    public void scalarMultiplyTest() {
        Vector3F vector = new Vector3F(1.0F, 2.0F, 3.0F);
        Vector3F scaled = vector.scalarMultiply(3.0F);
        assertEquals(3.0, scaled.getX());
        assertEquals(6.0, scaled.getY());
        assertEquals(9.0, scaled.getZ());
    }

    @Test
    public void dotProductTest() {
        Vector3F v1 = new Vector3F(1.0F, 2.0F, 3.0F);
        Vector3F v2 = new Vector3F(4.0F, 5.0F, 6.0F);
        double dotProduct = v1.dotProduct(v2);
        assertEquals(32.0, dotProduct);
    }

    @Test
    public void magnitudeTest() {
        Vector3F vector = new Vector3F(1.0F, 2.0F, 2.0F);
        assertEquals(3.0, vector.magnitude());
    }

    @Test
    public void normalizeTest() {
        Vector3F vector = new Vector3F(3.0F, 0.0F, 4.0F);
        Vector3F normalized = vector.normalize();
        assertEquals(0.6F, normalized.getX(), 1e-15);
        assertEquals(0.0F, normalized.getY(), 1e-15);
        assertEquals(0.8F, normalized.getZ(), 1e-15);
    }

    @Test
    public void crossProductTest() {
        Vector3F v1 = new Vector3F(3.0F, -3.0F, 1.0F);
        Vector3F v2 = new Vector3F(4.0F, 9.0F, 2.0F);
        Vector3F crossProduct = v1.crossProduct(v2);
        assertEquals(-15.0, crossProduct.getX());
        assertEquals(-2.0, crossProduct.getY());
        assertEquals(39.0, crossProduct.getZ());
    }
}

