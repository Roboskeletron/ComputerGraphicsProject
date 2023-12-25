package com.cgp.graphics.primitives.rasterization;

import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.matrix.Matrix4;

public final class ViewMatrix {
    private final GameObject gameObject;
    private final Matrix4 viewMatrix;

    private ViewMatrix(GameObject gameObject, Matrix4 viewMatrix) {
        this.gameObject = gameObject;
        this.viewMatrix = viewMatrix;
    }

    public static ViewMatrix create(GameObject target, Camera camera){
        var viewMatrix = Rasterization.lookAt(target, camera);

        return new ViewMatrix(target, viewMatrix);
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Matrix4 getViewMatrix() {
        return viewMatrix;
    }
}
