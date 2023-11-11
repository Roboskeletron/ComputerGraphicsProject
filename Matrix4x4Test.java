package ru.vsu.cs.filozop;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix4x4Test {

    @Test
    public void testIdentityMatrixCreation() {
        Matrix4x4 identity = new Matrix4x4(true);
        assertEquals(1, identity.getAt(0, 0));
        assertEquals(1, identity.getAt(1, 1));
        assertEquals(1, identity.getAt(2, 2));
        assertEquals(1, identity.getAt(3, 3));
    }

    @Test
    public void testMatrixAddition() {
        Matrix4x4 matrix1 = new Matrix4x4(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4x4 matrix2 = new Matrix4x4(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4x4 expected = new Matrix4x4(new double[][]{
                {2, 4, 6, 8},
                {2, 4, 6, 8},
                {2, 4, 6, 8},
                {2, 4, 6, 8}
        });

        Matrix4x4 actual = matrix1.add(matrix2);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(expected.getAt(i, j), actual.getAt(i, j), "The sum of the two matrices is not correct");
    }

    @Test
    public void testMatrixMultiplication() {
        Matrix4x4 matrix1 = new Matrix4x4(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4x4 matrix2 = new Matrix4x4(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix4x4 expected = new Matrix4x4(new double[][]{
                {10, 20, 30, 40},
                {10, 20, 30, 40},
                {10, 20, 30, 40},
                {10, 20, 30, 40}
        });

        Matrix4x4 actual = matrix1.multiply(matrix2);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(expected.getAt(i, j), actual.getAt(i, j), "Matrix multiplication is not correct");
    }

    @Test
    public void testDeterminant() {
        Matrix4x4 matrix = new Matrix4x4(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        });

        double expected = 0.0; // The determinant of this particular matrix is 0
        double actual = matrix.determinant();

        assertEquals(expected, actual, "Determinant calculation is not correct");
    }

    @Test
    public void testInverse() {
        // Setting up a known 4x4 matrix for which we know the inverse
        double[][] values = {
                {1, 2, 3, 4},
                {5, 6, 7, 0},
                {8, 9, 10, 11},
                {12, 0, 15, 16}
        };

        Matrix4x4 matrix = new Matrix4x4(values);

        // Assuming known inverses exist
        // Insert the correct inverse matrix values
        double[][] inverseValues = {
                {-0.6865079365079365, -0.06481481481481481, 0.19576719576719576, 0.037037037037037035},
                {-0.001984126984126984, 0.004629629629629629, 0.10846560846560846,-0.07407407407407407},
                {0.49206349206349204, 0.18518518518518517, -0.2328042328042328, 0.037037037037037035},
                {0.05357142857142857, -0.125, 0.07142857142857142, 0}
        };

        Matrix4x4 expectedInverse = new Matrix4x4(inverseValues);
        Matrix4x4 actualInverse = matrix.inverse();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedInverse.getAt(i, j), actualInverse.getAt(i, j), 0.0001);
            }
        }
    }

    @Test
    public void testSingularMatrixInverse() {
        double[][] singularValues = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        Matrix4x4 singularMatrix = new Matrix4x4(singularValues);

        assertThrows(IllegalStateException.class, () -> singularMatrix.inverse());
    }


}
