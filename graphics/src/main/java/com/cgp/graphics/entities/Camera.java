package com.cgp.graphics.entities;

import com.cgp.graphics.components.transform.BasicTransform;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.matrix.Matrix4;

public class Camera extends GameObject {
    private float FOV;
    private float AspectRatio;
    private float FPlane;
    private float NPlane;
    private Matrix4 clipMatrix = null;

    public Camera() {
        //TODO: provide implementation of transform
        super(new BasicTransform(), null, null);
    }

    public float getFOV() {
        return FOV;
    }

    public void setFOV(float FOV) {
        this.FOV = FOV;
        clipMatrix = null;
    }

    public float getAspectRatio() {
        return AspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        AspectRatio = aspectRatio;
        clipMatrix = null;
    }

    public float getFPlane() {
        return FPlane;
    }

    public void setFPlane(float FPlane) {
        this.FPlane = FPlane;
        clipMatrix = null;
    }

    public float getNPlane() {
        return NPlane;
    }

    public void setNPlane(float NPlane) {
        this.NPlane = NPlane;
        clipMatrix = null;
    }

    public Matrix4 getClipMatrix() {
        if (clipMatrix == null) {
            clipMatrix = Rasterization.clip(this);
        }

        return clipMatrix;
    }
}
