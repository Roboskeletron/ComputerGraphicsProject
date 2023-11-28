package com.cgp.graphics.util;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Transform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;

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
}
