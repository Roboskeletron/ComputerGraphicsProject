package ru.vsu.cs.filozop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector4Test {

    @Test
    public void constructorTest() {
        Vector4 vector = new Vector4(1.0, 2.0, 3.0, 4.0);
        assertEquals(1.0, vector.getX());
        assertEquals(2.0, vector.getY());
        assertEquals(3.0, vector.getZ());
        assertEquals(4.0, vector.getW());
    }

    @Test
    public void addTest() {
        Vector4 v1 = new Vector4(1.0, 2.0, 3.0, 4.0);
        Vector4 v2 = new Vector4(4.0, 3.0, 2.0, 1.0);
        Vector4 sum = v1.add(v2);
        assertEquals(5.0, sum.getX());
        assertEquals(5.0, sum.getY());
        assertEquals(5.0, sum.getZ());
        assertEquals(5.0, sum.getW());
    }

    @Test
    public void subtractTest() {
        Vector4 v1 = new Vector4(4.0, 3.0, 2.0, 1.0);
        Vector4 v2 = new Vector4(1.0, 2.0, 3.0, 4.0);
        Vector4 difference = v1.subtract(v2);
        assertEquals(3.0, difference.getX());
        assertEquals(1.0, difference.getY());
        assertEquals(-1.0, difference.getZ());
        assertEquals(-3.0, difference.getW());
    }

    @Test
    public void scalarMultiplyTest() {
        Vector4 vector = new Vector4(1.0, 2.0, 3.0, 4.0);
        Vector4 scaled = vector.scalarMultiply(3.0);
        assertEquals(3.0, scaled.getX());
        assertEquals(6.0, scaled.getY());
        assertEquals(9.0, scaled.getZ());
        assertEquals(12.0, scaled.getW());
    }

    @Test
    public void dotProductTest() {
        Vector4 v1 = new Vector4(1.0, 2.0, 3.0, 4.0);
        Vector4 v2 = new Vector4(4.0, 3.0, 2.0, 1.0);
        double dotProduct = v1.dotProduct(v2);
        assertEquals(20.0, dotProduct);
    }

    @Test
    public void magnitudeTest() {
        Vector4 vector = new Vector4(1.0, 2.0, 2.0, 1.0);
        double expectedMagnitude = Math.sqrt(1.0 * 1.0 + 2.0 * 2.0 + 2.0 * 2.0 + 1.0 * 1.0);
        assertEquals(expectedMagnitude, vector.magnitude(), 1e-15);
    }

    @Test
    public void normalizeTest() {
        Vector4 vector = new Vector4(1.0, 2.0, 2.0, 1.0);
        double magnitude = vector.magnitude();
        Vector4 normalized = vector.normalize();
        assertEquals(1.0 / magnitude, normalized.getX(), 1e-15);
        assertEquals(2.0 / magnitude, normalized.getY(), 1e-15);
        assertEquals(2.0 / magnitude, normalized.getZ(), 1e-15);
        assertEquals(1.0 / magnitude, normalized.getW(), 1e-15);
    }
}