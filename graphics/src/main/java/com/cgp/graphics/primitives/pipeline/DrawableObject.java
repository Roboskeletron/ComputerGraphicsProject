package com.cgp.graphics.primitives.pipeline;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.graphics.primitives.rasterization.NormalizedScreenPoint;
import com.cgp.graphics.primitives.rasterization.matrix.ClipMatrix;
import com.cgp.graphics.primitives.rasterization.matrix.ModelMatrix;
import com.cgp.graphics.primitives.rasterization.matrix.ScreenMatrix;
import com.cgp.graphics.primitives.rasterization.matrix.ViewMatrix;
import com.cgp.graphics.util.BarycentricCoordinates;
import com.cgp.graphics.util.BarycentricCoordinatesUtil;
import com.cgp.graphics.util.PolygonFactory;
import com.cgp.math.vector.Vector3F;

import java.util.Arrays;
import java.util.stream.Stream;

public class DrawableObject implements Drawable, Cloneable {
    private ScreenMatrix screenMatrix;
    private final GameObject gameObject;

    protected DrawableObject(ScreenMatrix screenMatrix, GameObject gameObject) {
        this.screenMatrix = screenMatrix;
        this.gameObject = gameObject;
    }

    public static DrawableObject create(GameObject gameObject, ClipMatrix clipMatrix){
        if (!gameObject.isDrawable()){
            throw new IllegalArgumentException("Object should be drawable");
        }

        var modelMatrix = ModelMatrix.create(gameObject);
        var viewMatrix = ViewMatrix.create(gameObject, clipMatrix.getCamera());
        var screenMatrix = ScreenMatrix.create(modelMatrix, viewMatrix, clipMatrix);

        return new DrawableObject(screenMatrix, gameObject);
    }

    private Stream<Polygon> calculateNormalizedScreenPoints(){
        return gameObject.getMesh().getTriangulatedPolygons().parallelStream()
                .map(polygon -> PolygonFactory.createNormalizedPolygon(polygon, screenMatrix))
                .filter(DrawableObject::polygonFilter);
    }

    private Stream<Polygon> calculateScreenPoints(int width, int height){
        return calculateNormalizedScreenPoints()
                .map(polygon -> PolygonFactory.createScaledToScreen(polygon, width, height));
    }

    private Stream<BarycentricCoordinates> calculateBarycentricCoordinates(int width, int height){
        return calculateScreenPoints(width, height)
                .map(BarycentricCoordinates::fromPolygon);
    }

    private Stream<BarycentricVector> calculateBarycentricVectors(int width, int height){
        return calculateBarycentricCoordinates(width, height)
                .flatMap(BarycentricCoordinatesUtil::calculateBarycentricScreenPoints)
                .filter(BarycentricCoordinates::checkLambdaVector);
    }

    @Override
    public Stream<ColoredPoint> calculatePoints(int width, int height){
        var material = gameObject.getMaterial();
        return calculateBarycentricVectors(width, height)
                .map(material::getColoredPoint);
    }

    @Override
    public DrawableObject clone() {
        try {
            return (DrawableObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private static boolean polygonFilter(Polygon polygon){
        return Arrays.stream(polygon.getVertices()).parallel()
                .map(vertex -> (NormalizedScreenPoint) vertex)
                .map(NormalizedScreenPoint::getNormalizedScreenPoint)
                .allMatch(DrawableObject::pointFilter);
    }

    private static boolean pointFilter(Vector3F point){
        var x = Math.abs(point.getX());
        var y = Math.abs(point.getY());
        var z = Math.abs(point.getZ());

        return x <=1 && y <= 1 && z <= 1;
    }
}
