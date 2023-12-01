package com.cgp.graphics.util;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Transform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;
import com.cgp.math.vector.Vector4F;

public class Rasterization {
    public static Matrix4 lookAt(GameObject target, Camera camera) {
        Transform targetTransfrom = target.getTransform();
        Transform cameraTransform = camera.getTransform();

        var z = targetTransfrom.getPosition().subtract(cameraTransform.getPosition());
        var up = new Vector3F(0, 1, 0);
        var x = up.crossProduct(z);

        var y = z.crossProduct(x).normalize();
        x = x.normalize();
        z = z.normalize();

        return new Matrix4(new float[][]{
                {x.getX(), x.getY(), x.getZ(), -cameraTransform.getPosition().getX()},
                {y.getX(), y.getY(), y.getZ(), -cameraTransform.getPosition().getY()},
                {z.getX(), z.getY(), z.getZ(), -cameraTransform.getPosition().getZ()},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4 clip(Camera camera) {
        float atanFov = (float) Math.atan(camera.getFOV());
        float difference = camera.getFPlane() - camera.getNPlane();
        return new Matrix4(new float[][]{
                {atanFov, 0, 0, 0},
                {0, atanFov / camera.getAspectRatio(), 0, 0},
                {0, 0, (camera.getFPlane() + camera.getNPlane()) / difference, 2 * camera.getFPlane() * camera.getNPlane() / -difference},
                {0, 0, 1, 0}
        });
    }

    public static Vector3F vertexToNormalizedScreen(Vector4F vertex, Matrix4 P, Matrix4 V, Matrix4 M){
        var v = P.multiply(V).multiply(M).multiplyVector4(vertex);
        var w = v.getW();

        return new Vector3F(v.getX() / w, v.getY() / w, v.getZ() / w);
    }

    public static void toScreenCoordinates (Vector3F point, float width, float height){
        var x = (width - 1) / 2 * point.getX() + (width - 1) / 2;
        var y = (1 - height) / 2 * point.getY() + (height - 1) / 2;

        point.setX(x);
        point.setY(y);
    }
}
