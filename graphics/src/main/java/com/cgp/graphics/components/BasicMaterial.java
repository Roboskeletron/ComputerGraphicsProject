package com.cgp.graphics.components;

import com.cgp.graphics.primitives.BarycentricVector;
import com.cgp.graphics.primitives.ColoredPoint;
import com.cgp.graphics.primitives.Material;
import com.cgp.math.vector.Vector3F;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicMaterial extends Material {
    private ConcurrentHashMap<Vector3F, ColoredPoint> textureVertexMap;

    @Override
    public void bakeMaterial(Mesh mesh, Map<Vector3F, ColoredPoint> textureVertexMap) {
        var textureMapCheck = textureVertexMap.keySet().parallelStream()
                .allMatch(mesh.vertexPolygonMap::containsKey);

        if (!textureMapCheck) {
            throw new IllegalArgumentException("Texture vertex map contains non existing vertices");
        }

        this.textureVertexMap = new ConcurrentHashMap<>(textureVertexMap);
    }

    @Override
    public ColoredPoint getColoredPoint(BarycentricVector point) {
        var texturePoint = calculateNormalizedTexturePoint(point);

        scaleTexturePoint(texturePoint);

        var x = Math.round(texturePoint.getX());
        var y = Math.round(texturePoint.getY());

        var color = texture.getColor(x, y);

        return new ColoredPoint(texturePoint, color);
    }

    protected Vector3F calculateNormalizedTexturePoint(BarycentricVector vector){
        var barycentricCoordinates = vector.getBarycentricCoordinates();

        var a = textureVertexMap.get(barycentricCoordinates.getA());
        var b = textureVertexMap.get(barycentricCoordinates.getB());
        var c = textureVertexMap.get(barycentricCoordinates.getC());

        var lambdaVector = vector.getLambdaVector();

        return a.scalarMultiply(lambdaVector.getX())
                .add(b.scalarMultiply(lambdaVector.getY()))
                .add(c.scalarMultiply(lambdaVector.getZ()));
    }

    protected void scaleTexturePoint(Vector3F point){
        var x = texture.getWidth() * point.getX();
        var y = texture.getHeight() * point.getY();

        point.setX(x);
        point.setY(y);
    }
}
