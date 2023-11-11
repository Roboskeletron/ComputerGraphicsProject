package com.cgp.math.vector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector4FTest {

    @Test
    public void constructorTest() {
        Vector4F vector = new Vector4F(1.0F, 2.0F, 3.0F, 4.0F);
        assertEquals(1.0, vector.getX());
        assertEquals(2.0, vector.getY());
        assertEquals(3.0, vector.getZ());
        assertEquals(4.0, vector.getW());
    }

    @Test
    public void addTest() {
        Vector4F v1 = new Vector4F(1.0F, 2.0F, 3.0F, 4.0F);
        Vector4F v2 = new Vector4F(4.0F, 3.0F, 2.0F, 1.0F);
        Vector4F sum = v1.add(v2);
        assertEquals(5.0F, sum.getX());
        assertEquals(5.0F, sum.getY());
        assertEquals(5.0F, sum.getZ());
        assertEquals(5.0F, sum.getW());
    }

    @Test
    public void subtractTest() {
        Vector4F v1 = new Vector4F(4.0F, 3.0F, 2.0F, 1.0F);
        Vector4F v2 = new Vector4F(1.0F, 2.0F, 3.0F, 4.0F);
        Vector4F difference = v1.subtract(v2);
        assertEquals(3.0F, difference.getX());
        assertEquals(1.0F, difference.getY());
        assertEquals(-1.0F, difference.getZ());
        assertEquals(-3.0F, difference.getW());
    }

    @Test
    public void scalarMultiplyTest() {
        Vector4F vector = new Vector4F(1.0F, 2.0F, 3.0F, 4.0F);
        Vector4F scaled = vector.scalarMultiply(3.0F);
        assertEquals(3.0F, scaled.getX());
        assertEquals(6.0F, scaled.getY());
        assertEquals(9.0F, scaled.getZ());
        assertEquals(12.0F, scaled.getW());
    }

    @Test
    public void dotProductTest() {
        Vector4F v1 = new Vector4F(1.0F, 2.0F, 3.0F, 4.0F);
        Vector4F v2 = new Vector4F(4.0F, 3.0F, 2.0F, 1.0F);
        float dotProduct = v1.dotProduct(v2);
        assertEquals(20.0, dotProduct);
    }

    @Test
    public void magnitudeTest() {
        Vector4F vector = new Vector4F(1.0F, 2.0F, 2.0F, 1.0F);
        float expectedMagnitude = (float) Math.sqrt(1.0F * 1.0F + 2.0F * 2.0F + 2.0F * 2.0F + 1.0F * 1.0F);
        assertEquals(expectedMagnitude, vector.magnitude(), 1e-15);
    }

    @Test
    public void normalizeTest() {
        Vector4F vector = new Vector4F(1.0F, 2.0F, 2.0F, 1.0F);
        float magnitude = vector.magnitude();
        Vector4F normalized = vector.normalize();
        assertEquals(1.0F / magnitude, normalized.getX(), 1e-15);
        assertEquals(2.0F / magnitude, normalized.getY(), 1e-15);
        assertEquals(2.0F / magnitude, normalized.getZ(), 1e-15);
        assertEquals(1.0F / magnitude, normalized.getW(), 1e-15);
    }
}