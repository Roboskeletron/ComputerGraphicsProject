package com.cgp.graphics.primitives.rasterization;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.util.AffineTransform;
import com.cgp.math.matrix.Matrix4;

public final class ModelMatrix {
    private final GameObject gameObject;
    private final Matrix4 modelMatrix;

    private ModelMatrix(GameObject gameObject, Matrix4 modelMatrix) {
        this.gameObject = gameObject;
        this.modelMatrix = modelMatrix;
    }

    public static ModelMatrix create(GameObject gameObject){
        var transform = gameObject.getTransform();
        var affineTransform = new AffineTransform.Builder()
                .withTranslation(transform.getPosition())
                .withRotation(transform.getRotation())
                .withScale(transform.getScale())
                .build();

        return new ModelMatrix(gameObject, affineTransform.getTransformationMatrix());
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Matrix4 getModelMatrix() {
        return modelMatrix;
    }
}
