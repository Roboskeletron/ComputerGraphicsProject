package com.cgp.graphics.primitives.rasterization;

import com.cgp.graphics.primitives.mesh.Vertex;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.vector.Vector3F;

public final class ScreenPoint extends Vertex {
    private final Vector3F point;
    private ScreenPoint(Vertex vertex, Vector3F point) {
        super(vertex.getX(), vertex.getY(), vertex.getZ());
        this.point = point;
    }

    public static ScreenPoint create(NormalizedScreenPoint normalizedScreenPoint, int width, int height){
        var point = Rasterization
                .toScreenCoordinates(normalizedScreenPoint.getNormalizedScreenPoint(), width, height);

        return new ScreenPoint(normalizedScreenPoint, point);
    }

    public Vector3F getPoint() {
        return point;
    }
}
