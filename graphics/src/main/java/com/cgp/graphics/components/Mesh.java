package com.cgp.graphics.components;

import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.util.Normal;
import com.cgp.graphics.util.Triangulation;
import com.cgp.math.vector.Vector3F;
import com.cgp.math.vector.Vector4F;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Mesh {
    protected final List<Polygon> polygons;
    protected final ConcurrentHashMap<Vector3F, Vector3F> vertexNormalMap = new ConcurrentHashMap<>();
    protected final ConcurrentHashMap<Vector3F, List<Polygon>> vertexPolygonMap = new ConcurrentHashMap<>();
    protected final ConcurrentHashMap<Polygon, Vector3F> polygonNormalMap = new ConcurrentHashMap<>();
    protected ArrayList<Polygon> triangulatedPolygons = new ArrayList<>();

    public Mesh(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public void bakeMesh() {
        bakeVertices();
    }

    private void bakeVertices() {
        triangulatedPolygons.clear();
        vertexNormalMap.clear();
        polygonNormalMap.clear();
        vertexPolygonMap.clear();

        polygons.stream().map(Triangulation::basic).forEach(polygons ->
                polygons.stream().forEachOrdered(this::mapVerticesToPolygons)
        );

//        polygonNormalMap.putAll(triangulatedPolygons.stream().collect(Collectors.toConcurrentMap(
//                                polygon -> polygon,
//                                Normal::getPolygonNormal
//                        )
//                )
//        );
//
//        vertexNormalMap.putAll(vertexPolygonMap.keySet().stream().collect(Collectors.toConcurrentMap(
//                                vertex -> vertex,
//                                vertex -> Normal.getVertexNormal(vertex, vertexPolygonMap, polygonNormalMap)
//                        )
//                )
//        );
    }

    private void mapVerticesToPolygons(Polygon polygon) {
        triangulatedPolygons.add(polygon);

        triangulatedPolygons.stream().map(Polygon::getVertices)
                .forEachOrdered(vertices ->
                        {
                            for (var vertex : vertices) {
                                vertexPolygonMap.putIfAbsent(vertex, new ArrayList<>());

                                var polygons = vertexPolygonMap.get(vertex);

                                polygons.add(polygon);
                            }
                        }
                );
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public Map<Vector3F, Vector3F> getVertexNormalMap() {
        return vertexNormalMap;
    }

    public Map<Vector3F, List<Polygon>> getVertexPolygonMap() {
        return vertexPolygonMap;
    }

    public Map<Polygon, Vector3F> getPolygonNormalMap() {
        return polygonNormalMap;
    }

    public ArrayList<Polygon> getTriangulatedPolygons() {
        return triangulatedPolygons;
    }
}
