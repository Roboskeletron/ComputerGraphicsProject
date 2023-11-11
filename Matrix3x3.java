package ru.vsu.cs.filozop;

public class Matrix3x3 {

    private double[][] matrix = new double[3][3];

    public Matrix3x3(double[][] matrix) {
        if (matrix == null || matrix.length != 3 || matrix[0].length != 3)
            throw new IllegalArgumentException("Матрица должна быть размера 3x3");

        this.matrix = new double[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                this.matrix[i][j] = matrix[i][j];
    }

    // Нулевая матрица
    public Matrix3x3() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                matrix[i][j] = 0;
    }

    // Инициализируем матрицу boolean
    public Matrix3x3(boolean isIdentity) {
        this();
        if (isIdentity) {
            for (int i = 0; i < 3; i++)
                matrix[i][i] = 1;
        }
    }

    //Добавить
    public void setAt(int i, int j, double value) {
        matrix[i][j] = value;
    }

    //Получить
    public double getAt(int i, int j) {
        return matrix[i][j];
    }


    public Matrix3x3 add(Matrix3x3 other) {
        Matrix3x3 result = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.setAt(i, j, this.getAt(i, j) + other.getAt(i, j));
            }
        }
        return result;
    }

    // Умножение матриц
    public Matrix3x3 multiply(Matrix3x3 other) {
        Matrix3x3 result = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }

    //Умножение матрицы на вектор
    public Vector3 multiplyVector3(Vector3 vec) {
        double x = matrix[0][0] * vec.getX() + matrix[0][1] * vec.getY() + matrix[0][2] * vec.getZ();
        double y = matrix[1][0] * vec.getX() + matrix[1][1] * vec.getY() + matrix[1][2] * vec.getZ();
        double z = matrix[2][0] * vec.getX() + matrix[2][1] * vec.getY() + matrix[2][2] * vec.getZ();

        return new Vector3(x, y, z);
    }

    //Транспонирование
    public Matrix3x3 transpose() {
        Matrix3x3 result = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.matrix[j][i] = this.matrix[i][j];
            }
        }
        return result;
    }

    // Определитель
    public double determinant() {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }


    public Matrix3x3 inverse() {
        double determinant = this.determinant();
        if (Math.abs(determinant) < 1e-9) {
            throw new ArithmeticException("Матрица не имеет обратную матрицу                                                     ");
        }

        Matrix3x3 cofactor = new Matrix3x3();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double[][] minor = new double[2][2];
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

        Matrix3x3 adjugate = cofactor.transpose();

        Matrix3x3 inverse = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse.matrix[i][j] = adjugate.matrix[i][j] / determinant;
            }
        }

        return inverse;
    }

    // Вспомогательная функция для вычисления определителя матрицы 2x2
    private double determinant(double[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }
}
