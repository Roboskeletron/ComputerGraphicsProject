package com.cgp.math.AffineTransform;
import com.cgp.math.matrix.Matrix4;

public class AffineTransform {
    private Matrix4 transformationMatrix;

    public AffineTransform() {
        this.transformationMatrix = new Matrix4(true); // Инициализация как единичная матрица
    }

    public Matrix4 getTransformationMatrix() {
        return transformationMatrix;
    }

    // Метод для масштабирования
    public void scale(float scaleX, float scaleY, float scaleZ) {
        Matrix4 scaleMatrix = new Matrix4(new float[][]{
                {scaleX, 0, 0, 0},
                {0, scaleY, 0, 0},
                {0, 0, scaleZ, 0},
                {0, 0, 0, 1}
        });
        transformationMatrix = transformationMatrix.multiply(scaleMatrix);
    }

    // Метод для вращения (в градусах)
    public void rotateX(float degrees) {
        float radians = (float) Math.toRadians(degrees);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        Matrix4 rotationMatrix = new Matrix4(new float[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        });

        transformationMatrix = transformationMatrix.multiply(rotationMatrix);
    }

    public void rotateY(float degrees) {
        float radians = (float) Math.toRadians(degrees);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        Matrix4 rotationMatrix = new Matrix4(new float[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        });

        transformationMatrix = transformationMatrix.multiply(rotationMatrix);
    }

    public void rotateZ(float degrees) {
        float radians = (float) Math.toRadians(degrees);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        Matrix4 rotationMatrix = new Matrix4(new float[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });

        transformationMatrix = transformationMatrix.multiply(rotationMatrix);
    }

    // Метод для переноса
    public void translate(float translateX, float translateY, float translateZ) {
        Matrix4 translationMatrix = new Matrix4(new float[][]{
                {1, 0, 0, translateX},
                {0, 1, 0, translateY},
                {0, 0, 1, translateZ},
                {0, 0, 0, 1}
        });

        transformationMatrix = transformationMatrix.multiply(translationMatrix);
    }
}
