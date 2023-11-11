package ru.vsu.cs.filozop;

import java.util.Arrays;

public class Matrix4x4 {

    private double[][] matrix = new double[4][4];

    public Matrix4x4(double[][] matrix) {
        if (matrix == null || matrix.length != 4 || matrix[0].length != 4)
            throw new IllegalArgumentException("Матрица должна быть 4х4");
        this.matrix = new double[4][4];

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                this.matrix[i][j] = matrix[i][j];
    }

    public Matrix4x4() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                matrix[i][j] = 0;
    }

    public Matrix4x4(boolean isIdentity) {
        this();
        if (isIdentity) {
            for (int i = 0; i < 4; i++)
                matrix[i][i] = 1;
        }
    }

    public void setAt(int i, int j, double value) {
        matrix[i][j] = value;
    }

    public double getAt(int i, int j) {
        return matrix[i][j];
    }

    public Matrix4x4 add(Matrix4x4 other) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.setAt(i, j, this.getAt(i, j) + other.getAt(i, j));
            }
        }
        return result;
    }

    public Matrix4x4 multiply(Matrix4x4 other) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }

    public Vector4 multiplyVector4(Vector4 vec) {
        double x = matrix[0][0] * vec.getX() + matrix[0][1] * vec.getY() + matrix[0][2] * vec.getZ() + matrix[0][3] * vec.getW();
        double y = matrix[1][0] * vec.getX() + matrix[1][1] * vec.getY() + matrix[1][2] * vec.getZ() + matrix[1][3] * vec.getW();
        double z = matrix[2][0] * vec.getX() + matrix[2][1] * vec.getY() + matrix[2][2] * vec.getZ() + matrix[2][3] * vec.getW();
        double w = matrix[3][0] * vec.getX() + matrix[3][1] * vec.getY() + matrix[3][2] * vec.getZ() + matrix[3][3] * vec.getW();
        return new Vector4(x, y, z, w);
    }

    public Matrix4x4 transpose() {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[j][i] = this.matrix[i][j];
            }
        }

        return result;
    }

    public double determinant() {
        double sum = 0.0;
        if (matrix == null || matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Матрица должна быть 4х4.");
        }
        int sign = 1;
        for (int i = 0; i < 4; i++) {
            double[][] subArray = getSubMatrix(matrix, i);
            sum += sign * matrix[0][i] * determinant3x3(subArray);
            sign = -sign;
        }
        return sum;
    }

    private double[][] getSubMatrix(double[][] matrix, int excludeCol) {
        double[][] subArray = new double[3][3];
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

    private double determinant3x3(double[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }


    public double cofactor(int i, int j) {
        int size = 4;
        double[][] smaller = new double[size - 1][size - 1];
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if(a != i && b != j){
                    smaller[a < i ? a : a - 1][b < j ? b : b - 1] = this.matrix[a][b];
                }
            }
        }
        double determinant = new Matrix3x3(smaller).determinant();
        return ((i + j) % 2 == 0) ? determinant : -determinant;
    }

    public Matrix4x4 adjugate() {
        Matrix4x4 adj = new Matrix4x4();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                adj.setAt(j, i, this.cofactor(i, j));
        return adj;
    }

    public Matrix4x4 inverse() {
        double det = this.determinant();
        if (det == 0)
            throw new IllegalStateException("Матрица не имеет обратную.");
        Matrix4x4 adj = this.adjugate();
        Matrix4x4 inv = new Matrix4x4();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++){
                inv.setAt(i, j, adj.getAt(i, j) / det);}
        return inv;
    }


}
