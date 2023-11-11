package com.cgp.math.matrix;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix4Test {

    @Test
    public void testIdentityMatrixCreation() {
        Matrix4 identity = new Matrix4(true);
        assertEquals(1, identity.getAt(0, 0));
        assertEquals(1, identity.getAt(1, 1));
        assertEquals(1, identity.getAt(2, 2));
        assertEquals(1, identity.getAt(3, 3));
    }

    @Test
    public void testMatrixAddition() {
        Matrix4 matrix1 = new Matrix4(new float[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4 matrix2 = new Matrix4(new float[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4 expected = new Matrix4(new float[][]{
                {2, 4, 6, 8},
                {2, 4, 6, 8},
                {2, 4, 6, 8},
                {2, 4, 6, 8}
        });

        Matrix4 actual = matrix1.add(matrix2);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(expected.getAt(i, j), actual.getAt(i, j), "The sum of the two matrices is not correct");
    }

    @Test
    public void testMatrixMultiplication() {
        Matrix4 matrix1 = new Matrix4(new float[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4 matrix2 = new Matrix4(new float[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4 expected = new Matrix4(new float[][]{
                {10, 20, 30, 40},
                {10, 20, 30, 40},
                {10, 20, 30, 40},
                {10, 20, 30, 40}
        });

        Matrix4 actual = matrix1.multiply(matrix2);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(expected.getAt(i, j), actual.getAt(i, j), "Matrix multiplication is not correct");
    }

    @Test
    public void testDeterminant() {
        Matrix4 matrix = new Matrix4(new float[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        });

        float expected = 0.0F; // The determinant of this particular matrix is 0
        float actual = matrix.determinant();

        assertEquals(expected, actual, "Determinant calculation is not correct");
    }

    @Test
    public void testInverse() {
        // Setting up a known 4x4 matrix for which we know the inverse
        float[][] values = {
                {1, 2, 3, 4},
                {5, 6, 7, 0},
                {8, 9, 10, 11},
                {12, 0, 15, 16}
        };

        Matrix4 matrix = new Matrix4(values);

        // Assuming known inverses exist
        // Insert the correct inverse matrix values
        float[][] inverseValues = {
                {-0.6865079365079365F, -0.06481481481481481F, 0.19576719576719576F, 0.037037037037037035F},
                {-0.001984126984126984F, 0.004629629629629629F, 0.10846560846560846F,-0.07407407407407407F},
                {0.49206349206349204F, 0.18518518518518517F, -0.2328042328042328F, 0.037037037037037035F},
                {0.05357142857142857F, -0.125F, 0.07142857142857142F, 0}
        };

        Matrix4 expectedInverse = new Matrix4(inverseValues);
        Matrix4 actualInverse = matrix.inverse();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedInverse.getAt(i, j), actualInverse.getAt(i, j), 0.0001);
            }
        }
    }

    @Test
    public void testSingularMatrixInverse() {
        float[][] singularValues = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        Matrix4 singularMatrix = new Matrix4(singularValues);

        assertThrows(IllegalStateException.class, singularMatrix::inverse);
    }


}
