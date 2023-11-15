package com.cgp.graphics.util;

import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector3F;

import java.util.List;

public class Normal {
    public static Vector3F getPolygonNormal(Polygon polygon){
        var v0 = polygon.getVertex(0);
        var v1 = polygon.getVertex(1);
        var v2 = polygon.getVertex(polygon.getVertexCount() - 1);

        var vector1 = v1.subtract(v0);
        var vector2 = v2.subtract(v0);

        return vector1.crossProduct(vector2).normalize();
    }

}
