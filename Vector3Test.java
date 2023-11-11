package ru.vsu.cs.filozop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3Test {

    @Test
    public void constructorTest() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);
        assertEquals(1.0, vector.getX());
        assertEquals(2.0, vector.getY());
        assertEquals(3.0, vector.getZ());
    }

    @Test
    public void addTest() {
        Vector3 v1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 v2 = new Vector3(4.0, 5.0, 6.0);
        Vector3 sum = v1.add(v2);
        assertEquals(5.0, sum.getX());
        assertEquals(7.0, sum.getY());
        assertEquals(9.0, sum.getZ());
    }

    @Test
    public void subtractTest() {
        Vector3 v1 = new Vector3(4.0, 5.0, 6.0);
        Vector3 v2 = new Vector3(1.0, 2.0, 3.0);
        Vector3 difference = v1.subtract(v2);
        assertEquals(3.0, difference.getX());
        assertEquals(3.0, difference.getY());
        assertEquals(3.0, difference.getZ());
    }

    @Test
    public void scalarMultiplyTest() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);
        Vector3 scaled = vector.scalarMultiply(3.0);
        assertEquals(3.0, scaled.getX());
        assertEquals(6.0, scaled.getY());
        assertEquals(9.0, scaled.getZ());
    }

    @Test
    public void dotProductTest() {
        Vector3 v1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 v2 = new Vector3(4.0, 5.0, 6.0);
        double dotProduct = v1.dotProduct(v2);
        assertEquals(32.0, dotProduct);
    }

    @Test
    public void magnitudeTest() {
        Vector3 vector = new Vector3(1.0, 2.0, 2.0);
        assertEquals(3.0, vector.magnitude());
    }

    @Test
    public void normalizeTest() {
        Vector3 vector = new Vector3(3.0, 0.0, 4.0);
        Vector3 normalized = vector.normalize();
        assertEquals(0.6, normalized.getX(), 1e-15);
        assertEquals(0.0, normalized.getY(), 1e-15);
        assertEquals(0.8, normalized.getZ(), 1e-15);
    }

    @Test
    public void crossProductTest() {
        Vector3 v1 = new Vector3(3.0, -3.0, 1.0);
        Vector3 v2 = new Vector3(4.0, 9.0, 2.0);
        Vector3 crossProduct = v1.crossProduct(v2);
        assertEquals(-15.0, crossProduct.getX());
        assertEquals(-2.0, crossProduct.getY());
        assertEquals(39.0, crossProduct.getZ());
    }
}

