package com.cgp.math.AffineTransform;
import com.cgp.math.matrix.Matrix4;

public class AffineTransform {
    private Matrix4 transformationMatrix;

    public AffineTransform() {
        this.transformationMatrix = new Matrix4(true);
    }

    public Matrix4 getTransformationMatrix() {
        return transformationMatrix;
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        Matrix4 scaleMatrix = new Matrix4(new float[][]{
                {scaleX, 0, 0, 0},
                {0, scaleY, 0, 0},
                {0, 0, scaleZ, 0},
                {0, 0, 0, 1}
        });
        transformationMatrix = transformationMatrix.multiply(scaleMatrix);
    }

    public void rotate(float radiansX, float radiansY, float radiansZ) {
        float cosX = (float) Math.cos(radiansX);
        float sinX = (float) Math.sin(radiansX);
        float cosY = (float) Math.cos(radiansY);
        float sinY = (float) Math.sin(radiansY);
        float cosZ = (float) Math.cos(radiansZ);
        float sinZ = (float) Math.sin(radiansZ);

        Matrix4 rotationMatrix = new Matrix4(new float[][]{
                {cosY * cosZ, -cosX * sinZ + sinX * sinY * cosZ, sinX * sinZ + cosX * sinY * cosZ, 0},
                {cosY * sinZ, cosX * cosZ + sinX * sinY * sinZ, -sinX * cosZ + cosX * sinY * sinZ, 0},
                {-sinY, sinX * cosY, cosX * cosY, 0},
                {0, 0, 0, 1}
        });

        transformationMatrix = transformationMatrix.multiply(rotationMatrix);
    }

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

