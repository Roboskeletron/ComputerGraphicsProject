package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Polygon;
import com.cgp.graphics.util.Normal;
import com.cgp.graphics.util.Triangulation;
import com.cgp.math.vector.Vector3F;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Mesh {
    protected final List<Polygon> polygons;
    protected final HashMap<Vector3F, Vector3F> vertexNormalMap = new HashMap<>();
    protected final HashMap<Vector3F, ArrayList<Polygon>> vertexPolygonMap = new HashMap<>();
    protected final HashMap<Polygon, Vector3F> polygonNormalMap = new HashMap<>();
    protected ArrayList<Polygon> triangulatedPolygons = new ArrayList<>();

    public Mesh(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public void bakeMesh() {
        triangulatedPolygons.clear();

        polygons.stream().map(Triangulation::basic).forEach(polygons ->
                polygons.stream().forEachOrdered(this::mapVerticesToPolygons)
        );

        polygonNormalMap.putAll(triangulatedPolygons.stream().collect(Collectors.toMap(
                                polygon -> polygon,
                                Normal::getPolygonNormal
                        )
                )
        );


    }

    private void mapVerticesToPolygons(Polygon polygon){
        triangulatedPolygons.add(polygon);

        triangulatedPolygons.stream().map(Polygon::getVertices)
                .forEachOrdered(vertices ->
                        {
                            for (var vertex : vertices) {
                                var polygons = vertexPolygonMap.containsKey(vertex) ?
                                        vertexPolygonMap.get(vertex) : new ArrayList<Polygon>();

                                polygons.add(polygon);
                                vertexPolygonMap.put(vertex, polygons);
                            }
                        }
                );
    }
}
