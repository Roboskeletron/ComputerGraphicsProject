package com.cgp.graphics.components.material;

import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.util.PolygonFactory;
import com.cgp.graphics.util.Triangulation;
import com.cgp.math.vector.Vector3F;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BasicMaterial extends Material {
    protected ConcurrentHashMap<Polygon, Polygon> polygonTexturePolygonMap;

    @Override
    public void bakeMaterial(Map<Polygon, Polygon> polygonTexturePolygonMap) {
        this.polygonTexturePolygonMap = calculateTriangulatedMap(polygonTexturePolygonMap);
    }

    @Override
    public ColoredPoint getColoredPoint(BarycentricVector point) {
        var texturePoint = calculateNormalizedTexturePoint(point);

        scaleTexturePoint(texturePoint);

        var x = Math.round(texturePoint.getX());
        var y = Math.round(texturePoint.getY());

        var color = texture.getColor(x, y);

        return new ColoredPoint(point, color);
    }

    protected Vector3F calculateNormalizedTexturePoint(BarycentricVector vector) {
        var polygon = PolygonFactory.fromBarycentricCoordinates(vector.getBarycentricCoordinates());

        var texturePolygon = polygonTexturePolygonMap.get(polygon);

        var lambdaVector = vector.getLambdaVector();
        var a = texturePolygon.getVertex(0);
        var b = texturePolygon.getVertex(1);
        var c = texturePolygon.getVertex(2);

        return a.scalarMultiply(lambdaVector.getX())
                .add(b.scalarMultiply(lambdaVector.getY()))
                .add(c.scalarMultiply(lambdaVector.getZ()));
    }

    protected void scaleTexturePoint(Vector3F point) {
        var x = texture.getWidth() * point.getX();
        var y = texture.getHeight() * (1 - point.getY());

        point.setX(x);
        point.setY(y);
    }

    protected ConcurrentHashMap<Polygon, Polygon> calculateTriangulatedMap(Map<Polygon, Polygon> polygonTexturePolygonMap) {
        ConcurrentHashMap<Polygon, Polygon> concurrentHashMap = new ConcurrentHashMap<>();

        polygonTexturePolygonMap.entrySet().parallelStream()
                .flatMap(entry -> triangulatePolygonEntry(
                                entry.getKey(),
                                entry.getValue()
                        ).entrySet()
                        .parallelStream()
                )
                .forEach(entry -> concurrentHashMap.put(entry.getKey(), entry.getValue()));


        return concurrentHashMap;
    }

    protected Map<Polygon, Polygon> triangulatePolygonEntry(Polygon polygon, Polygon texturePolygon) {
        var keys = Triangulation.basic(polygon);
        var values = Triangulation.basic(texturePolygon);

        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Unable to triangulate texture polygons");
        }

        var keysIterator = keys.iterator();
        var valuesIterator = values.iterator();

        return IntStream.range(0, keys.size())
                .boxed()
                .collect(Collectors.toConcurrentMap(
                                i -> keysIterator.next(),
                                i -> valuesIterator.next()
                        )
                );
    }
}
