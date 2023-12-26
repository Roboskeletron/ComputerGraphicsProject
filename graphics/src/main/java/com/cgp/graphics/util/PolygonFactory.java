package com.cgp.graphics.util;

import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.primitives.mesh.Vertex;
import com.cgp.graphics.primitives.rasterization.NormalizedScreenPoint;
import com.cgp.graphics.primitives.rasterization.ScreenPoint;
import com.cgp.graphics.primitives.rasterization.matrix.ScreenMatrix;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class PolygonFactory {
    public static Polygon createNormalizedPolygon(Polygon polygon, ScreenMatrix screenMatrix) {
        throwIfNotTriangulated(polygon);

        return new Polygon.Builder()
                .withVertices(Arrays.stream(polygon.getVertices()).parallel()
                        .map(vertex -> NormalizedScreenPoint.create(vertex, screenMatrix))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static Polygon createScaledToScreen(Polygon polygon, int width, int height) {
        throwIfNotTriangulated(polygon);

        return new Polygon.Builder()
                .withVertices(Arrays.stream(polygon.getVertices()).parallel()
                        .map(vertex -> ScreenPoint.create((NormalizedScreenPoint) vertex, width, height))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static Polygon fromBarycentricCoordinates(BarycentricCoordinates barycentricCoordinates){
        return new Polygon.Builder()
                .withVertex((Vertex) barycentricCoordinates.getA())
                .withVertex((Vertex) barycentricCoordinates.getB())
                .withVertex((Vertex) barycentricCoordinates.getC())
                .build();
    }

    public static void throwIfNotTriangulated(Polygon polygon) {
        if (polygon.getVertexCount() != 3) {
            throw new IllegalArgumentException("Polygon should be triangulated");
        }
    }
}
