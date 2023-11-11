package com.cgp.math.AffineTransform;

import com.cgp.math.matrix.Matrix4;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AffineTransformTest {

    @Test
    void scaleTest() {
        AffineTransform transform = new AffineTransform();
        transform.scale(2, 3, 4);

        Matrix4 resultMatrix = transform.getTransformationMatrix();

        assertEquals(2, resultMatrix.getAt(0, 0), 1e-5);
        assertEquals(3, resultMatrix.getAt(1, 1), 1e-5);
        assertEquals(4, resultMatrix.getAt(2, 2), 1e-5);
        assertEquals(1, resultMatrix.getAt(3, 3), 1e-5);
    }

    @Test
    void rotateTest() {
        AffineTransform transform = new AffineTransform();
        transform.rotate(0.5f, 0.3f, 0.2f); // Rotate by 0.5 radians around X, 0.3 radians around Y, and 0.2 radians around Z

        Matrix4 resultMatrix = transform.getTransformationMatrix();

        // Expected values for a specific rotation
        double expectedValue00 = Math.cos(0.3) * Math.cos(0.2);
        double expectedValue01 = -Math.cos(0.5) * Math.sin(0.2) + Math.sin(0.5) * Math.sin(0.3) * Math.cos(0.2);
        double expectedValue02 = Math.sin(0.5) * Math.sin(0.2) + Math.cos(0.5) * Math.sin(0.3) * Math.cos(0.2);

        double expectedValue10 = Math.cos(0.3) * Math.sin(0.2);
        double expectedValue11 = Math.cos(0.5) * Math.cos(0.2) + Math.sin(0.5) * Math.sin(0.3) * Math.sin(0.2);
        double expectedValue12 = -Math.sin(0.5) * Math.cos(0.2) + Math.cos(0.5) * Math.sin(0.3) * Math.sin(0.2);

        double expectedValue20 = -Math.sin(0.3);
        double expectedValue21 = Math.sin(0.5) * Math.cos(0.3);
        double expectedValue22 = Math.cos(0.5) * Math.cos(0.3);

        // Verify the result against the expected values
        assertEquals(expectedValue00, resultMatrix.getAt(0, 0), 1e-5);
        assertEquals(expectedValue01, resultMatrix.getAt(0, 1), 1e-5);
        assertEquals(expectedValue02, resultMatrix.getAt(0, 2), 1e-5);

        assertEquals(expectedValue10, resultMatrix.getAt(1, 0), 1e-5);
        assertEquals(expectedValue11, resultMatrix.getAt(1, 1), 1e-5);
        assertEquals(expectedValue12, resultMatrix.getAt(1, 2), 1e-5);

        assertEquals(expectedValue20, resultMatrix.getAt(2, 0), 1e-5);
        assertEquals(expectedValue21, resultMatrix.getAt(2, 1), 1e-5);
        assertEquals(expectedValue22, resultMatrix.getAt(2, 2), 1e-5);

        assertEquals(1, resultMatrix.getAt(3, 3), 1e-5);
    }


    @Test
    void translateTest() {
        AffineTransform transform = new AffineTransform();
        transform.translate(1, 2, 3);

        Matrix4 resultMatrix = transform.getTransformationMatrix();

        assertEquals(1, resultMatrix.getAt(0, 3), 1e-5);
        assertEquals(2, resultMatrix.getAt(1, 3), 1e-5);
        assertEquals(3, resultMatrix.getAt(2, 3), 1e-5);
        assertEquals(1, resultMatrix.getAt(3, 3), 1e-5);
    }
}