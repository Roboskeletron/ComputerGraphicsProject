package com.cgp.graphics.primitives.rasterization;

public final class ScreenMatrix {
    private final ModelMatrix modelMatrix;
    private final ViewMatrix viewMatrix;
    private final ClipMatrix clipMatrix;

    private ScreenMatrix(ModelMatrix modelMatrix, ViewMatrix viewMatrix, ClipMatrix clipMatrix) {
        this.modelMatrix = modelMatrix;
        this.viewMatrix = viewMatrix;
        this.clipMatrix = clipMatrix;
    }

    public static ScreenMatrix create(ModelMatrix modelMatrix, ViewMatrix viewMatrix, ClipMatrix clipMatrix){
        if (!modelMatrix.getGameObject().equals(viewMatrix.getGameObject())){
            throw new IllegalArgumentException("Game object mismatch");
        }

        return new ScreenMatrix(modelMatrix, viewMatrix, clipMatrix);
    }

    public ModelMatrix getModelMatrix() {
        return modelMatrix;
    }

    public ViewMatrix getViewMatrix() {
        return viewMatrix;
    }

    public ClipMatrix getClipMatrix() {
        return clipMatrix;
    }
}
