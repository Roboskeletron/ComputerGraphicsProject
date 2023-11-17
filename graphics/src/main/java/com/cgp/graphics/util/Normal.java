package com.cgp.graphics.util;

import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.util.MathUtil;
import com.cgp.math.vector.Vector3F;

import java.util.List;
import java.util.Map;

public class Normal {
    public static Vector3F getPolygonNormal(Polygon polygon){
        if (polygon.getVertexCount() != 3){
            throw new IllegalArgumentException("Cant calculate normal, polygon should be triangulated");
        }

        var v0 = polygon.getVertex(0);
        var v1 = polygon.getVertex(1);
        var v2 = polygon.getVertex(2);

        var vector1 = v1.subtract(v0);
        var vector2 = v2.subtract(v0);

        var normal = vector1.crossProduct(vector2).normalize();

        if (MathUtil.compareFloat(normal.magnitude(), 0) == 0){
            return normal;
        }

        v0 = polygon.getVertex(1);
        v1 = polygon.getVertex(2);
        v2 = polygon.getVertex(0);

        vector1 = v1.subtract(v0);
        vector2 = v2.subtract(v0);

        return vector1.crossProduct(vector2).normalize();
    }

    public static Vector3F getVertexNormal(Vector3F vertex, Map<Vector3F, List<Polygon>> vertexPolygonMap, Map<Polygon, Vector3F> polygonNormalMap){
        var polygons = vertexPolygonMap.get(vertex);

        Vector3F normal = new Vector3F(0, 0, 0);

        for (var polygon : polygons){
            var polygonNormal = polygonNormalMap.get(polygon);
            normal = normal.add(polygonNormal);
        }

        normal = normal.scalarMultiply(1 / (float) polygons.size());

        return normal;
    }

    public static Vector3F getPolygonNormal(Polygon polygon, Map<Vector3F, Vector3F> vertexNormalMap){
        Vector3F normal = new Vector3F(0, 0, 0);

        for (int  i = 0; i < polygon.getVertexCount(); i++){
            var vertex = polygon.getVertex(i);
            normal = normal.add(vertexNormalMap.get(vertex));
        }

        return normal.scalarMultiply(1 / (float) polygon.getVertexCount());
    }
}
