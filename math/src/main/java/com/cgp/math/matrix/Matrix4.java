package com.cgp.math.matrix;

import com.cgp.math.vector.Vector4F;

public class Matrix4 {

    private float[][] matrix = new float[4][4];
    private Float determinant = null;

    public Matrix4(float[][] matrix) {
        if (matrix == null || matrix.length != 4 || matrix[0].length != 4)
            throw new IllegalArgumentException("Matrix should be 4 by 4");
        this.matrix = new float[4][4];

        for (int i = 0; i < 4; i++)
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, 4);
    }

    public Matrix4() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                matrix[i][j] = 0;
    }

    public Matrix4(boolean isIdentity) {
        this();
        if (isIdentity) {
            for (int i = 0; i < 4; i++)
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

    public Matrix4 add(Matrix4 other) {
        Matrix4 result = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.setAt(i, j, this.getAt(i, j) + other.getAt(i, j));
            }
        }
        return result;
    }

    public Matrix4 multiply(Matrix4 other) {
        Matrix4 result = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }

    public Vector4F multiplyVector4(Vector4F vec) {
        float x = matrix[0][0] * vec.getX() + matrix[0][1] * vec.getY() + matrix[0][2] * vec.getZ() + matrix[0][3] * vec.getW();
        float y = matrix[1][0] * vec.getX() + matrix[1][1] * vec.getY() + matrix[1][2] * vec.getZ() + matrix[1][3] * vec.getW();
        float z = matrix[2][0] * vec.getX() + matrix[2][1] * vec.getY() + matrix[2][2] * vec.getZ() + matrix[2][3] * vec.getW();
        float w = matrix[3][0] * vec.getX() + matrix[3][1] * vec.getY() + matrix[3][2] * vec.getZ() + matrix[3][3] * vec.getW();
        return new Vector4F(x, y, z, w);
    }

    public Matrix4 transpose() {
        Matrix4 result = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[j][i] = this.matrix[i][j];
            }
        }

        return result;
    }

    public float determinant() {
        if (determinant != null){
            return determinant;
        }

        float sum = 0.0F;
        if (matrix == null || matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Matrix should be 4 by 4");
        }
        int sign = 1;
        for (int i = 0; i < 4; i++) {
            float[][] subArray = getSubMatrix(matrix, i);
            sum += sign * matrix[0][i] * determinant3(subArray);
            sign = -sign;
        }

        determinant = sum;

        return determinant;
    }

    private float[][] getSubMatrix(float[][] matrix, int excludeCol) {
        float[][] subArray = new float[3][3];
        int k = 0;
        int l = 0;
        for (int i = 1; i < 4; i++) {
            l = 0;
            for (int j = 0; j < 4; j++) {
                if (j == excludeCol) {
                    continue;
                }
                subArray[k][l] = matrix[i][j];
                l++;
            }
            k++;
        }
        return subArray;
    }

    private float determinant3(float[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }


    public float cofactor(int i, int j) {
        int size = 4;
        float[][] smaller = new float[size - 1][size - 1];
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if(a != i && b != j){
                    smaller[a < i ? a : a - 1][b < j ? b : b - 1] = this.matrix[a][b];
                }
            }
        }
        float determinant = new Matrix3(smaller).determinant();
        return ((i + j) % 2 == 0) ? determinant : -determinant;
    }

    public Matrix4 adjugate() {
        Matrix4 adj = new Matrix4();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                adj.setAt(j, i, this.cofactor(i, j));
        return adj;
    }

    public Matrix4 inverse() {
        float det = this.determinant();
        if (det == 0)
            throw new IllegalStateException("Cant inverse matrix");
        Matrix4 adj = this.adjugate();
        Matrix4 inv = new Matrix4();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++){
                inv.setAt(i, j, adj.getAt(i, j) / det);}
        return inv;
    }
}
