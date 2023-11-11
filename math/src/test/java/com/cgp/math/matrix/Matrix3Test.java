package com.cgp.math.matrix;

import com.cgp.math.vector.Vector3F;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Matrix3Test {

    @Test
    public void constructorsTest() {
        Matrix3 m1 = new Matrix3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(0, m1.getAt(i, j));
            }
        }

        float[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix3 m2 = new Matrix3(data2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(data2[i][j], m2.getAt(i, j));
            }
        }

        Matrix3 m3 = new Matrix3(true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j)
                    assertEquals(1, m3.getAt(i, j));
                else
                    assertEquals(0, m3.getAt(i, j));
            }
        }
    }

    @Test
    public void addTest() {
        float[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix3 m1 = new Matrix3(data);
        Matrix3 m2 = new Matrix3(data);
        Matrix3 sum = m1.add(m2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(data[i][j] * 2, sum.getAt(i, j));
            }
        }
    }

    @Test
    public void multiplyTest() {
        float[][] data1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        float[][] data2 = {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}};
        Matrix3 m1 = new Matrix3(data1);
        Matrix3 m2 = new Matrix3(data2);
        Matrix3 product = m1.multiply(m2);
        float[][] expected = {{84, 90, 96}, {201, 216, 231}, {318, 342, 366}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expected[i][j], product.getAt(i, j));
            }
        }
    }

    @Test
    public void multiplyVector3Test() {
        float[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix3 matrix = new Matrix3(data);
        Vector3F vector = new Vector3F(2, 3, 4);
        Vector3F result = matrix.multiplyVector3(vector);
        assertEquals(20, result.getX());
        assertEquals(47, result.getY());
        assertEquals(74, result.getZ());
    }

    @Test
    public void transposeTest() {
        float[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix3 matrix = new Matrix3(data);
        Matrix3 transposed = matrix.transpose();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(data[j][i], transposed.getAt(i, j));
            }
        }
    }

    @Test
    public void determinantTest() {
        float[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix3 matrix = new Matrix3(data);
        assertEquals(0, matrix.determinant());
    }

    @Test
    public void testInverse() {
        float[][] testData = { { 1, 2, 3 }, { 0, 1, 4 }, { 5, 6, 0 } };
        Matrix3 matrix = new Matrix3(testData);

        float[][] expectedInverseData = { { -24, 18, 5 }, { 20, -15, -4 }, { -5, 4, 1 } };
        Matrix3 expectedInverse = new Matrix3(expectedInverseData);

        Matrix3 actualInverse = matrix.inverse();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expectedInverse.getAt(i, j), actualInverse.getAt(i, j), 1e-9, "Element at (" + i + ", " + j + ") not as expected");
            }
        }
    }

    @Test
    public void testInverseOfSingularMatrix() {
        float[][] testData = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };  // This is a singular matrix
        Matrix3 matrix = new Matrix3(testData);

        assertThrows(ArithmeticException.class, matrix::inverse);
    }
}


