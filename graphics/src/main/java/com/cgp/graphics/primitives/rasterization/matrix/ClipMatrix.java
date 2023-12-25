package com.cgp.graphics.primitives.rasterization.matrix;

import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.matrix.Matrix4;

public final class ClipMatrix {
    private final Camera camera;
    private final Matrix4 clipMatrix;

    private ClipMatrix(Camera camera, Matrix4 clipMatrix) {
        this.camera = camera;
        this.clipMatrix = clipMatrix;
    }

    public static ClipMatrix create(Camera camera){
        var clipMatrix = Rasterization.clip(camera);

        return new ClipMatrix(camera, clipMatrix);
    }

    public Camera getCamera() {
        return camera;
    }

    public Matrix4 getClipMatrix() {
        return clipMatrix;
    }
}
