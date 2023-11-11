package com.cgp.math.matrix;

import com.cgp.math.vector.Vector3F;

public class Matrix3 {

    private float[][] matrix = new float[3][3];
    private Float determinant = null;

    public Matrix3(float[][] matrix) {
        if (matrix == null || matrix.length != 3 || matrix[0].length != 3)
            throw new IllegalArgumentException("Matrix should be 3 by 3");

        this.matrix = new float[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, 3);
    }
    
    public Matrix3() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                matrix[i][j] = 0;
    }


    public Matrix3(boolean isIdentity) {
        this();
        if (isIdentity) {
            for (int i = 0; i < 3; i++)
                matrix[i][i] = 1;
        }
    }


    public void setAt(int i, int j, float value) {
        matrix[i][j] = value;
        determinant = null;
    }


    public float getAt(int i, int j) {
        return matrix[i][j];
    }


    public Matrix3 add(Matrix3 other) {
        Matrix3 result = new Matrix3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.setAt(i, j, this.getAt(i, j) + other.getAt(i, j));
            }
        }
        return result;
    }
    
    public Matrix3 multiply(Matrix3 other) {
        Matrix3 result = new Matrix3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }


    public Vector3F multiplyVector3(Vector3F vec) {
        float x = matrix[0][0] * vec.getX() + matrix[0][1] * vec.getY() + matrix[0][2] * vec.getZ();
        float y = matrix[1][0] * vec.getX() + matrix[1][1] * vec.getY() + matrix[1][2] * vec.getZ();
        float z = matrix[2][0] * vec.getX() + matrix[2][1] * vec.getY() + matrix[2][2] * vec.getZ();

        return new Vector3F(x, y, z);
    }
    
    public Matrix3 transpose() {
        Matrix3 result = new Matrix3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.matrix[j][i] = this.matrix[i][j];
            }
        }
        return result;
    }
    
    public float determinant() {
        if (determinant != null){
            return determinant;
        }

        determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

        return determinant;
    }


    public Matrix3 inverse() {
        float determinant = this.determinant();
        if (Math.abs(determinant) < 1e-9) {
            throw new ArithmeticException("Cant inverse matrix");
        }

        Matrix3 cofactor = new Matrix3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float[][] minor = new float[2][2];
                for (int ii = 0; ii < 3; ii++) {
                    for (int jj = 0; jj < 3; jj++) {
                        if (ii != i && jj != j) {
                            minor[ii < i ? ii : ii - 1][jj < j ? jj : jj - 1] = this.matrix[ii][jj];
                        }
                    }
                }
                cofactor.matrix[i][j] = (i + j) % 2 == 0 ? determinant(minor) : -determinant(minor);
            }
        }

        Matrix3 adjugate = cofactor.transpose();

        Matrix3 inverse = new Matrix3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse.matrix[i][j] = adjugate.matrix[i][j] / determinant;
            }
        }

        return inverse;
    }
    
    private float determinant(float[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }
}
