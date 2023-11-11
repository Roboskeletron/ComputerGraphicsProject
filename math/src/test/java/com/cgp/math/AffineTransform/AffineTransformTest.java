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
    void rotateXTest() {
        AffineTransform transform = new AffineTransform();
        transform.rotateX(90);

        Matrix4 resultMatrix = transform.getTransformationMatrix();

        assertEquals(0, resultMatrix.getAt(1, 1), 1e-5);
        assertEquals(1, resultMatrix.getAt(2, 1), 1e-5);
        assertEquals(-1, resultMatrix.getAt(1, 2), 1e-5);
        assertEquals(1, resultMatrix.getAt(3, 3), 1e-5);
    }

    @Test
    void rotateYTest() {
        AffineTransform transform = new AffineTransform();
        transform.rotateY(90);

        Matrix4 resultMatrix = transform.getTransformationMatrix();
        System.out.println(resultMatrix);

        assertEquals(0, resultMatrix.getAt(0, 0), 1e-5);
        assertEquals(-1, resultMatrix.getAt(2, 0), 1e-5);
        assertEquals(1, resultMatrix.getAt(0, 2), 1e-5);
        assertEquals(1, resultMatrix.getAt(3, 3), 1e-5);
    }

    @Test
    void rotateZTest() {
        AffineTransform transform = new AffineTransform();
        transform.rotateZ(90);

        Matrix4 resultMatrix = transform.getTransformationMatrix();

        assertEquals(0, resultMatrix.getAt(0, 0), 1e-5);
        assertEquals(1, resultMatrix.getAt(1, 0), 1e-5);
        assertEquals(-1, resultMatrix.getAt(0, 1), 1e-5);
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