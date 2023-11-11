package com.cgp.graphics.primitives;

import com.cgp.math.vector.Vector3F;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {
    @ParameterizedTest
    @MethodSource
    void polygonCreationFail(Collection<Vector3F> vertices) {
        final var message = "Polygon must consist of at least 3 vertices";
        assertThrows(IllegalArgumentException.class, () ->
                        new Polygon.Builder(vertices).build()
                ,
                message);

        assertThrows(IllegalArgumentException.class, () ->
                        new Polygon.Builder(vertices.toArray(new Vector3F[0])).build()
                ,
                message);

        assertThrows(IllegalArgumentException.class,
                () -> new Polygon.Builder()
                        .withVertices(vertices)
                        .build()
                ,
                message);

        assertThrows(IllegalArgumentException.class,
                () -> new Polygon.Builder()
                        .withVertices(vertices.toArray(new Vector3F[0]))
                        .build()
                ,
                message);

        assertThrows(IllegalArgumentException.class,
                () -> {
                    var builder = new Polygon.Builder();

                    for (var vertex : vertices) {
                        builder = builder.withVertex(vertex);
                    }

                    builder.build();
                }
                ,
                message);
    }

    @ParameterizedTest
    @MethodSource
    void polygonCreationSuccess(Collection<Vector3F> vertices){
        var polygon = new Polygon.Builder(vertices).build();

        assertTrue(checkPolygon(polygon, vertices));

        polygon = new Polygon.Builder(vertices.toArray(new Vector3F[0])).build();

        assertTrue(checkPolygon(polygon, vertices));

        polygon = new Polygon.Builder()
                .withVertices(vertices)
                .build();

        assertTrue(checkPolygon(polygon, vertices));

        polygon = new Polygon.Builder()
                .withVertices(vertices.toArray(new Vector3F[0]))
                .build();

        assertTrue(checkPolygon(polygon, vertices));

        var builder = new Polygon.Builder();

        for (var vertex : vertices) {
            builder = builder.withVertex(vertex);
        }

        polygon = builder.build();

        assertTrue(checkPolygon(polygon, vertices));
    }

    private boolean checkPolygon(Polygon polygon, Collection<Vector3F> vertices){
        if (polygon.getVertexCount() != vertices.size()){
            return false;
        }

        var iterator = vertices.iterator();

        for (int i = 0; i < polygon.getVertexCount(); i++){
            var expected = iterator.next();

            if (!expected.equals(polygon.getVertex(i))){
                return false;
            }
        }

        return true;
    }

    private static Stream<Collection<Vector3F>> polygonCreationSuccess(){
        return Stream.of(
                List.of(new Vector3F(0, 0, 0), new Vector3F(2, 0, 0), new Vector3F(1, 0, 0)),
                List.of(new Vector3F(0, 0, 0), new Vector3F(0, 0, 0), new Vector3F(0, 0, 0), new Vector3F(1, 0, 0)),
                Set.of(new Vector3F(0, 0, 0), new Vector3F(0, 0, 0), new Vector3F(0, 0, 0), new Vector3F(1, 0, 0))
        );
    }

    private static Stream<Collection<Vector3F>> polygonCreationFail(){
        return Stream.of(
                List.of(new Vector3F(0, 0, 0)),
                List.of(new Vector3F(0, 0, 0), new Vector3F(0, 0, 0)),
                Collections.emptyList()
        );
    }
}