package com.cgp.graphics.primitives.pipeline;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.graphics.primitives.rasterization.matrix.ClipMatrix;
import com.cgp.graphics.primitives.rasterization.matrix.ModelMatrix;
import com.cgp.graphics.primitives.rasterization.matrix.ScreenMatrix;
import com.cgp.graphics.primitives.rasterization.matrix.ViewMatrix;
import com.cgp.graphics.util.BarycentricCoordinates;
import com.cgp.graphics.util.BarycentricCoordinatesUtil;
import com.cgp.graphics.util.PolygonFactory;

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
                .map(polygon -> PolygonFactory.createNormalizedPolygon(polygon, screenMatrix));
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
}
