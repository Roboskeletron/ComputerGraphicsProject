package com.cgp.math.AffineTransform;
import com.cgp.math.matrix.Matrix4;

public class AffineTransform {
    private Matrix4 transformationMatrix;
    private Matrix4 translateMatrix;
    private Matrix4 rotateMatrix;
    private Matrix4 scaleMatrix;

    public AffineTransform() {

    }

    public Matrix4 getTransformationMatrix() {
        if (transformationMatrix == null){
            transformationMatrix = translateMatrix.multiply(rotateMatrix).multiply(scaleMatrix);
        }

        return transformationMatrix;
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        scaleMatrix = new Matrix4(new float[][]{
                {scaleX, 0, 0, 0},
                {0, scaleY, 0, 0},
                {0, 0, scaleZ, 0},
                {0, 0, 0, 1}
        });
    }

    public void rotate(float radiansX, float radiansY, float radiansZ) {
        float cosX = (float) Math.cos(radiansX);
        float sinX = (float) Math.sin(radiansX);
        float cosY = (float) Math.cos(radiansY);
        float sinY = (float) Math.sin(radiansY);
        float cosZ = (float) Math.cos(radiansZ);
        float sinZ = (float) Math.sin(radiansZ);

        rotateMatrix = new Matrix4(new float[][]{
                {cosY * cosZ, -cosX * sinZ + sinX * sinY * cosZ, sinX * sinZ + cosX * sinY * cosZ, 0},
                {cosY * sinZ, cosX * cosZ + sinX * sinY * sinZ, -sinX * cosZ + cosX * sinY * sinZ, 0},
                {-sinY, sinX * cosY, cosX * cosY, 0},
                {0, 0, 0, 1}
        });

//        var rotateX = new Matrix4(new float[][]{
//                {1, 0, 0, 0},
//                {0, cosX, sinX, 0},
//                {0, -sinX, cosX, 0},
//                {0, 0, 0, 1}
//        });
//
//        var rotateY = new Matrix4(new float[][]{
//                {cosY, 0, sinY, 0},
//                {0, 1, 0, 0},
//                {-sinY, 0, cosY, 0},
//                {0, 0, 0, 1}
//        });
//
//        var rotateZ = new Matrix4(new float[][]{
//                {cosZ, sinY, 0, 0},
//                {-sinZ, cosZ, 0, 0},
//                {0, 0, 1, 0},
//                {0, 0, 0, 1}
//        });
//
//        rotateMatrix = rotateX.multiply(rotateY).multiply(rotateZ);
    }

    public void translate(float translateX, float translateY, float translateZ) {
        translateMatrix = new Matrix4(new float[][]{
                {1, 0, 0, translateX},
                {0, 1, 0, translateY},
                {0, 0, 1, translateZ},
                {0, 0, 0, 1}
        });
    }
}

