package com.cgp.graphics.util;

import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.primitives.mesh.Vertex;
import com.cgp.math.vector.Vector3F;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NormalTest {

    @ParameterizedTest
    @MethodSource
    void getPolygonNormal(Polygon polygon, Vertex expectedNormal) {
        var actual = Normal.getPolygonNormal(polygon);

        assertEquals(expectedNormal, actual);
    }

    @Test
    void testNonTriangularPolygon() {
        Polygon nonTriangularPolygon = new Polygon.Builder()
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(1, 0, 0))
                .withVertex(new Vertex(2, 0, 0))
                .withVertex(new Vertex(3, 0, 0))
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> Normal.getPolygonNormal(nonTriangularPolygon),
                "Polygon must consist of at least 3 vertices");
    }

    @ParameterizedTest
    @MethodSource
    void getVertexNormal(Vertex vertex,
                         Map<Vertex, List<Polygon>> vertexPolygonMap,
                         Map<Polygon, Vector3F> polygonNormalMap,
                         Vertex expectedNormal) {
//        Vertex calculatedNormal = Normal.getVertexNormal(vertex, vertexPolygonMap, polygonNormalMap);
//        assertEquals(expectedNormal, calculatedNormal);
    }

    @ParameterizedTest
    @MethodSource
    void getPolygonNormalFromVertices(Polygon polygon, Map<Vertex, Vertex> vertexNormalMap, Vertex expectedNormal) {
//        Vertex calculatedNormal = Normal.getPolygonNormal(polygon, vertexNormalMap);
//        assertEquals(expectedNormal, calculatedNormal);
    }

    private static Stream<Arguments> getPolygonNormal() {
        return Stream.of(
                Arguments.of(
                        // Triangle in a slanted position
                        new Polygon.Builder()
                                .withVertex(new Vertex(0, 0, 0))
                                .withVertex(new Vertex(-1, -2, -3))
                                .withVertex(new Vertex(-2, -1, -3))
                                .build(),
                        new Vertex(0.5773503f, 0.5773503f, -0.5773503f)
                ),
                Arguments.of(
                        // Triangle in a different plane (XZ plane)
                        new Polygon.Builder()
                                .withVertex(new Vertex(0, 0, 0))
                                .withVertex(new Vertex(1, 0, 0))
                                .withVertex(new Vertex(0, 0, 1))
                                .build(),
                        new Vertex(0, -1, 0)
                ),
                Arguments.of(
                        // Regular triangle lying in the XY plane
                        new Polygon.Builder()
                                .withVertex(new Vertex(0, 0, 0))
                                .withVertex(new Vertex(1, 0, 0))
                                .withVertex(new Vertex(0, 1, 0))
                                .build(),
                        new Vertex(0, 0, 1)
                ),
                Arguments.of(
                        // Triangle lying in the YZ plane
                        new Polygon.Builder()
                                .withVertex(new Vertex(0, 0, 0))
                                .withVertex(new Vertex(0, 1, 0))
                                .withVertex(new Vertex(0, 0, 1))
                                .build(),
                        new Vertex(1, 0, 0) // Expected normal for YZ plane
                )
        );
    }

    private static Stream<Arguments> getVertexNormal() {
        Vertex vertex1 = new Vertex(0, 0, 0);
        Vertex vertex2 = new Vertex(1, 1, 1);
        Vertex vertex3 = new Vertex(2, 2, 2);

        Polygon polygon1 = new Polygon.Builder()
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(0, 0, 0))
                .build();

        Polygon polygon2 = new Polygon.Builder()
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(0, 0, 0))
                .build();

        Polygon polygon3 = new Polygon.Builder()
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(0, 0, 0))
                .withVertex(new Vertex(0, 0, 0))
                .build();

        Map<Vertex, List<Polygon>> vertexPolygonMap = new HashMap<>();
        vertexPolygonMap.put(vertex1, List.of(polygon1));
        vertexPolygonMap.put(vertex2, List.of(polygon1, polygon2));
        vertexPolygonMap.put(vertex3, List.of(polygon1, polygon2, polygon3));

        Map<Polygon, Vertex> polygonNormalMap = new HashMap<>();
        polygonNormalMap.put(polygon1, new Vertex(1, 1, 1));
        polygonNormalMap.put(polygon2, new Vertex(0.5f, 0, -0.3f));
        polygonNormalMap.put(polygon3, new Vertex(-1, 0.2f, 0.1f));

        return Stream.of(
                Arguments.of(vertex1, vertexPolygonMap, polygonNormalMap, new Vertex(1, 1, 1)),
                Arguments.of(vertex2, vertexPolygonMap, polygonNormalMap, new Vertex(0.75f, 0.5f, 0.35f)),
                Arguments.of(vertex3, vertexPolygonMap, polygonNormalMap, new Vertex(0.166666666f, 0.4f, 0.266666666f))
        );
    }

    private static Stream<Arguments> getPolygonNormalFromVertices() {
        Vertex vertex1 = new Vertex(0, 1, 0);
        Vertex vertex2 = new Vertex(1, 1, 1);
        Vertex vertex3 = new Vertex(2, 2, 2);
        Vertex vertex4 = new Vertex(0, 0, 0);
        Vertex vertex5 = new Vertex(1, 1, 1);
        Vertex vertex6 = new Vertex(2, 2, 2);

        Polygon polygon1 = new Polygon.Builder(vertex1, vertex2, vertex3)
                .build();

        Polygon polygon2 = new Polygon.Builder(vertex2, vertex3, vertex5)
                .build();

        Polygon polygon3 = new Polygon.Builder(vertex1, vertex2, vertex3, vertex4, vertex5, vertex6)
                .build();

        Map<Vertex, Vertex> vertexNormalMap = new HashMap<>();
        vertexNormalMap.put(vertex1, new Vertex(0, 0, 1));
        vertexNormalMap.put(vertex2, new Vertex(1, 0, 0));
        vertexNormalMap.put(vertex3, new Vertex(0, 1, 0));
        vertexNormalMap.put(vertex4, new Vertex(0.5f, -1, 1));
        vertexNormalMap.put(vertex5, new Vertex(1, -0.45f, 0.3f));
        vertexNormalMap.put(vertex6, new Vertex(-1, -1, 0.3f));

        return Stream.of(
                Arguments.of(polygon1, vertexNormalMap, new Vertex(1f / 3, 1f / 3, 1f / 3)),
                Arguments.of(polygon2, vertexNormalMap, new Vertex(2f / 3, 0.55f / 3, 0.3f / 3)),
                Arguments.of(polygon3, vertexNormalMap, new Vertex(1.5f / 6, -1.45f / 6, 2.6f / 6))
        );
    }
}