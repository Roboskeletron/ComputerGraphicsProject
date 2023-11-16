package com.cgp.graphics.util;

import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector3F;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Test
    void getVertexNormal() {
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
}