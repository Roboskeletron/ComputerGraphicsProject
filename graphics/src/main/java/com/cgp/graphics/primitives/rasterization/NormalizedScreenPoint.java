package com.cgp.graphics.primitives.rasterization;

import com.cgp.graphics.primitives.mesh.Vertex;
import com.cgp.graphics.primitives.rasterization.matrix.ScreenMatrix;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.vector.Vector3F;

public final class NormalizedScreenPoint extends Vertex {
    private final Vector3F normalizedScreenPoint;
    private NormalizedScreenPoint(Vertex vertex, Vector3F normalizedScreenPoint) {
        super(vertex.getX(), vertex.getY(), vertex.getZ());
        this.normalizedScreenPoint = normalizedScreenPoint;
    }

    public static NormalizedScreenPoint create(Vertex vertex, ScreenMatrix screenMatrix){
        var normalizedPoint = Rasterization.vertexToNormalizedScreen(
                vertex.getVertex4F(),
                screenMatrix.getClipMatrix().getClipMatrix(),
                screenMatrix.getViewMatrix().getViewMatrix(),
                screenMatrix.getModelMatrix().getModelMatrix()
        );

        return new NormalizedScreenPoint(vertex, normalizedPoint);
    }

    public boolean isOnScreen(){
        var x = Math.abs(normalizedScreenPoint.getX());
        var y = Math.abs(normalizedScreenPoint.getY());
        var z = Math.abs(normalizedScreenPoint.getZ());

        return x <= 1 && y <= 1 && z <= 1;
    }

    public Vector3F getNormalizedScreenPoint() {
        return normalizedScreenPoint;
    }
}
