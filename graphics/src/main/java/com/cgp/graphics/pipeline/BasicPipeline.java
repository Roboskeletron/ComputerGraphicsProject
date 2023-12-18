package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.graphics.primitives.Transform;
import com.cgp.graphics.util.BarycentricCoordinates;
import com.cgp.graphics.util.Rasterization;
import com.cgp.graphics.util.ZBuffer;
import com.cgp.math.AffineTransform.AffineTransform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicPipeline implements Pipeline {
    private Scene scene;
    private Map<GameObject, Matrix4> modelMatrices;
    private Map<GameObject, Matrix4> viewMatrices;
    private Matrix4 clipMatrix;
    private ConcurrentHashMap<Vector3F, Vector3F> vertices2D3DMap;

    public BasicPipeline(Scene scene) {
        setScene(scene);
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {
        var canvas = graphicsContext.getCanvas();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        var points = calculateScreenPoints(canvas);

        rasterization(points, graphicsContext);
    }

    @Override
    public void run() {
        modelMatrices = calculateModelMatrices();
        viewMatrices = calculateViewMatrices();
        clipMatrix = calculateClipMatrix();

        vertices2D3DMap = (ConcurrentHashMap<Vector3F, Vector3F>) calculateVertices2D();
    }

    protected void rasterization(Map<Vector3F, Vector3F> points, GraphicsContext graphicsContext) {
        var barycentricCoordinates = calculateBarycentricCoordinates(points);

        var width = graphicsContext.getCanvas().getWidth();
        var height = graphicsContext.getCanvas().getHeight();

        IntStream.range(0, (int) (width + 1))
                .forEach(x -> IntStream.range(0, (int) (height + 1))
                        .forEach(y -> {
                                    var lambdaVector = getLambdaVector(x, y, barycentricCoordinates);

                                    if (lambdaVector.isPresent()){
                                        drawPixel(x, y, graphicsContext.getPixelWriter());
                                    }
                                }
                        )
                );
    }

    protected Optional<Vector3F> getLambdaVector(int x, int y, Set<BarycentricCoordinates> barycentricCoordinates) {
        return barycentricCoordinates.stream()
                .map(coordinates -> coordinates.getBarycentricVector(
                        new Vector3F(x, y, 1))
                )
                .filter(vector -> vector.getX() >= 0 && vector.getY() >= 0 && vector.getZ() >= 0)
                .findFirst();
    }

    protected void drawPixel(int x, int y, PixelWriter pixelWriter){
        pixelWriter.setColor(x, y, Color.RED);
     }

    protected Set<BarycentricCoordinates> calculateBarycentricCoordinates(Map<Vector3F, Vector3F> points) {
        return scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .flatMap(gameObject -> gameObject.getMesh()
                        .getTriangulatedPolygons()
                        .stream()
                        .filter(polygon -> Arrays.stream(polygon.getVertices())
                                .allMatch(points::containsKey))
                )
                .map(polygon -> BarycentricCoordinates.fromPolygon(polygon, points))
                .collect(Collectors.toSet());
    }

    protected ConcurrentMap<Vector3F, Vector3F> calculateScreenPoints(Canvas canvas) {
        return ZBuffer.filterPoints(vertices2D3DMap).stream()
                .collect(Collectors.toConcurrentMap(
                        Map.Entry::getKey,
                        entry -> Rasterization.toScreenCoordinates(entry.getValue(),
                                (float) canvas.getWidth(),
                                (float) canvas.getHeight()
                        )
                ));
    }

    protected Map<Vector3F, Vector3F> calculateVertices2D() {
        return scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .flatMap(gameObject -> gameObject
                        .getMesh()
                        .getVertices4()
                        .entrySet()
                        .stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(
                                        entry.getKey(),
                                        Rasterization.vertexToNormalizedScreen(entry.getValue(),
                                                clipMatrix,
                                                viewMatrices.get(gameObject),
                                                modelMatrices.get(gameObject)
                                        )
                                )
                        )
                )
                .collect(Collectors.toConcurrentMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));
    }

    protected AffineTransform applyAffineTransform(Transform transform) {
        AffineTransform affineTransform = new AffineTransform();

        var position = transform.getPosition();
        var rotation = transform.getRotation();
        var scale = transform.getScale();

        affineTransform.translate(position.getX(), position.getY(), position.getZ());
        affineTransform.rotate(rotation.getX(), rotation.getY(), rotation.getZ());
        affineTransform.scale(scale.getX(), scale.getY(), scale.getZ());

        return affineTransform;
    }

    protected Map<GameObject, Matrix4> calculateModelMatrices() {
        return scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .collect(Collectors.toConcurrentMap(
                                gameObject -> gameObject,
                                gameObject -> applyAffineTransform(gameObject.getTransform())
                                        .getTransformationMatrix()
                        )
                );
    }

    protected Map<GameObject, Matrix4> calculateViewMatrices() {
        return scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .collect(Collectors.toConcurrentMap(
                                gameObject -> gameObject,
                                gameObject -> Rasterization.lookAt(gameObject, scene.getCurrentCamera())
                        )
                );
    }

    protected Matrix4 calculateClipMatrix() {
        return Rasterization.clip(scene.getCurrentCamera());
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.bakeScene();
        run();
    }
}
