package com.cgp.graphics.util;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Transform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;
import com.cgp.math.vector.Vector4F;

public class Rasterization {
    public static Matrix4 lookAt(GameObject target, Camera camera) {
        Transform targetTransform = target.getTransform();
        Transform cameraTransform = camera.getTransform();

//        var z = cameraTransform.getRotation().subtract(cameraTransform.getPosition());
        var z = new Vector3F(1, 0, 0);
        var up = new Vector3F(0, 1, 0);
        var x = up.crossProduct(z);

        var y = z.crossProduct(x).normalize();
        x = x.normalize();
        z = z.normalize();

        return new Matrix4(new float[][]{
                {x.getX(), x.getY(), x.getZ(), -x.dotProduct(cameraTransform.getPosition())},
                {y.getX(), y.getY(), y.getZ(), -y.dotProduct(cameraTransform.getPosition())},
                {z.getX(), z.getY(), z.getZ(), -z.dotProduct(cameraTransform.getPosition())},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4 clip(Camera camera) {
        float tanFov = 1f / (float) Math.tan(camera.getFOV() * 0.5);
        float difference = camera.getFPlane() - camera.getNPlane();
        return new Matrix4(new float[][]{
                {tanFov, 0, 0, 0},
                {0, tanFov / camera.getAspectRatio(), 0, 0},
                {0, 0, (camera.getFPlane() + camera.getNPlane()) / difference, 2 * camera.getFPlane() * camera.getNPlane() / -difference},
                {0, 0, 1f, 0}
        });
    }

    public static Vector3F vertexToNormalizedScreen(Vector4F vertex, Matrix4 P, Matrix4 V, Matrix4 M) {
        var v = P.multiply(V).multiply(M).multiplyVector4(vertex);
        var w = v.getW();

        return new Vector3F(v.getX() / w, v.getY() / w, v.getZ() / w);
    }

    public static Vector3F toScreenCoordinates(Vector3F point, float width, float height) {
        var x = (width - 1) / 2 * point.getX() + (width - 1) / 2;
        var y = (1 - height) / 2 * point.getY() + (height - 1) / 2;

        return new Vector3F(x, y, point.getZ());
    }
}
