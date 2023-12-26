package com.cgp.graphics.util;

import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.math.vector.Vector3F;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NormalTest {

    @ParameterizedTest
    @MethodSource
    void getPolygonNormal(Polygon polygon, Vector3F expectedNormal) {
        var actual = Normal.getPolygonNormal(polygon);

        assertEquals(expectedNormal, actual);
    }

    @Test
    void testNonTriangularPolygon() {
        Polygon nonTriangularPolygon = new Polygon.Builder()
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(1, 0, 0))
                .withVertex(new Vector3F(2, 0, 0))
                .withVertex(new Vector3F(3, 0, 0))
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> Normal.getPolygonNormal(nonTriangularPolygon),
                "Polygon must consist of at least 3 vertices");
    }

    @ParameterizedTest
    @MethodSource
    void getVertexNormal(Vector3F vertex,
                         Map<Vector3F, List<Polygon>> vertexPolygonMap,
                         Map<Polygon, Vector3F> polygonNormalMap,
                         Vector3F expectedNormal) {
        Vector3F calculatedNormal = Normal.getVertexNormal(vertex, vertexPolygonMap, polygonNormalMap);
        assertEquals(expectedNormal, calculatedNormal);
    }

    @ParameterizedTest
    @MethodSource
    void getPolygonNormalFromVertices(Polygon polygon, Map<Vector3F, Vector3F> vertexNormalMap, Vector3F expectedNormal) {
        Vector3F calculatedNormal = Normal.getPolygonNormal(polygon, vertexNormalMap);
        assertEquals(expectedNormal, calculatedNormal);
    }

    private static Stream<Arguments> getPolygonNormal() {
        return Stream.of(
                Arguments.of(
                        // Triangle in a slanted position
                        new Polygon.Builder()
                                .withVertex(new Vector3F(0, 0, 0))
                                .withVertex(new Vector3F(-1, -2, -3))
                                .withVertex(new Vector3F(-2, -1, -3))
                                .build(),
                        new Vector3F(0.5773503f, 0.5773503f, -0.5773503f)
                ),
                Arguments.of(
                        // Triangle in a different plane (XZ plane)
                        new Polygon.Builder()
                                .withVertex(new Vector3F(0, 0, 0))
                                .withVertex(new Vector3F(1, 0, 0))
                                .withVertex(new Vector3F(0, 0, 1))
                                .build(),
                        new Vector3F(0, -1, 0)
                ),
                Arguments.of(
                        // Regular triangle lying in the XY plane
                        new Polygon.Builder()
                                .withVertex(new Vector3F(0, 0, 0))
                                .withVertex(new Vector3F(1, 0, 0))
                                .withVertex(new Vector3F(0, 1, 0))
                                .build(),
                        new Vector3F(0, 0, 1)
                ),
                Arguments.of(
                        // Triangle lying in the YZ plane
                        new Polygon.Builder()
                                .withVertex(new Vector3F(0, 0, 0))
                                .withVertex(new Vector3F(0, 1, 0))
                                .withVertex(new Vector3F(0, 0, 1))
                                .build(),
                        new Vector3F(1, 0, 0) // Expected normal for YZ plane
                )
        );
    }

    private static Stream<Arguments> getVertexNormal() {
        Vector3F vertex1 = new Vector3F(0, 0, 0);
        Vector3F vertex2 = new Vector3F(1, 1, 1);
        Vector3F vertex3 = new Vector3F(2, 2, 2);

        Polygon polygon1 = new Polygon.Builder()
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(0, 0, 0))
                .build();

        Polygon polygon2 = new Polygon.Builder()
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(0, 0, 0))
                .build();

        Polygon polygon3 = new Polygon.Builder()
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(0, 0, 0))
                .withVertex(new Vector3F(0, 0, 0))
                .build();

        Map<Vector3F, List<Polygon>> vertexPolygonMap = new HashMap<>();
        vertexPolygonMap.put(vertex1, List.of(polygon1));
        vertexPolygonMap.put(vertex2, List.of(polygon1, polygon2));
        vertexPolygonMap.put(vertex3, List.of(polygon1, polygon2, polygon3));

        Map<Polygon, Vector3F> polygonNormalMap = new HashMap<>();
        polygonNormalMap.put(polygon1, new Vector3F(1, 1, 1));
        polygonNormalMap.put(polygon2, new Vector3F(0.5f, 0, -0.3f));
        polygonNormalMap.put(polygon3, new Vector3F(-1, 0.2f, 0.1f));

        return Stream.of(
                Arguments.of(vertex1, vertexPolygonMap, polygonNormalMap, new Vector3F(1, 1, 1)),
                Arguments.of(vertex2, vertexPolygonMap, polygonNormalMap, new Vector3F(0.75f, 0.5f, 0.35f)),
                Arguments.of(vertex3, vertexPolygonMap, polygonNormalMap, new Vector3F(0.166666666f, 0.4f, 0.266666666f))
        );
    }

    private static Stream<Arguments> getPolygonNormalFromVertices() {
        Vector3F vertex1 = new Vector3F(0, 1, 0);
        Vector3F vertex2 = new Vector3F(1, 1, 1);
        Vector3F vertex3 = new Vector3F(2, 2, 2);
        Vector3F vertex4 = new Vector3F(0, 0, 0);
        Vector3F vertex5 = new Vector3F(1, 1, 1);
        Vector3F vertex6 = new Vector3F(2, 2, 2);

        Polygon polygon1 = new Polygon.Builder(vertex1, vertex2, vertex3)
                .build();

        Polygon polygon2 = new Polygon.Builder(vertex2, vertex3, vertex5)
                .build();

        Polygon polygon3 = new Polygon.Builder(vertex1, vertex2, vertex3, vertex4, vertex5, vertex6)
                .build();

        Map<Vector3F, Vector3F> vertexNormalMap = new HashMap<>();
        vertexNormalMap.put(vertex1, new Vector3F(0, 0, 1));
        vertexNormalMap.put(vertex2, new Vector3F(1, 0, 0));
        vertexNormalMap.put(vertex3, new Vector3F(0, 1, 0));
        vertexNormalMap.put(vertex4, new Vector3F(0.5f, -1, 1));
        vertexNormalMap.put(vertex5, new Vector3F(1, -0.45f, 0.3f));
        vertexNormalMap.put(vertex6, new Vector3F(-1, -1, 0.3f));

        return Stream.of(
                Arguments.of(polygon1, vertexNormalMap, new Vector3F(1f / 3, 1f / 3, 1f / 3)),
                Arguments.of(polygon2, vertexNormalMap, new Vector3F(2f / 3, 0.55f / 3, 0.3f / 3)),
                Arguments.of(polygon3, vertexNormalMap, new Vector3F(1.5f / 6, -1.45f / 6, 2.6f / 6))
        );
    }
}