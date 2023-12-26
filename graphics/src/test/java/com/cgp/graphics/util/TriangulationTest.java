package com.cgp.graphics.util;

import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.math.vector.Vector3F;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TriangulationTest {

    @ParameterizedTest
    @MethodSource
    void basic(Polygon origin, List<Polygon> expected) {
        var actual = Triangulation.basic(origin);


        assertIterableEquals(expected, actual);
    }

    private static Stream<Arguments> basic() {
        return Stream.of(
                Arguments.of(
                        new Polygon.Builder(
                                new Vector3F(0, 0, 0),
                                new Vector3F(1, 0, 0),
                                new Vector3F(2, 0, 0)
                        )
                                .build()
                        ,
                        List.of(
                                new Polygon.Builder(
                                        new Vector3F(0, 0, 0),
                                        new Vector3F(1, 0, 0),
                                        new Vector3F(2, 0, 0)
                                ).build()
                        )
                ),
                Arguments.of(
                        new Polygon.Builder(
                                new Vector3F(0, 0, 0),
                                new Vector3F(1, 0, 0),
                                new Vector3F(2, 0, 0),
                                new Vector3F(3, 0, 0)
                        )
                                .build()
                        ,
                        List.of(
                                new Polygon.Builder(
                                        new Vector3F(0, 0, 0),
                                        new Vector3F(1, 0, 0),
                                        new Vector3F(2, 0, 0)
                                )
                                        .build()
                                ,
                                new Polygon.Builder(
                                        new Vector3F(0, 0, 0),
                                        new Vector3F(2, 0, 0),
                                        new Vector3F(3, 0, 0)
                                )
                                        .build()
                        )
                ),
                Arguments.of(
                        new Polygon.Builder(
                                new Vector3F(0, 0, 0),
                                new Vector3F(1, 0, 0),
                                new Vector3F(2, 0, 0),
                                new Vector3F(3, 0, 0),
                                new Vector3F(4, 0, 0)
                        )
                                .build()
                        ,
                        List.of(
                                new Polygon.Builder(
                                        new Vector3F(0, 0, 0),
                                        new Vector3F(1, 0, 0),
                                        new Vector3F(2, 0, 0)
                                )
                                        .build()
                                ,
                                new Polygon.Builder(
                                        new Vector3F(0, 0, 0),
                                        new Vector3F(2, 0, 0),
                                        new Vector3F(3, 0, 0)
                                )
                                        .build()
                                ,
                                new Polygon.Builder(
                                        new Vector3F(0, 0, 0),
                                        new Vector3F(3, 0, 0),
                                        new Vector3F(4, 0, 0)
                                )
                                        .build()
                        )
                )
        );
    }
}