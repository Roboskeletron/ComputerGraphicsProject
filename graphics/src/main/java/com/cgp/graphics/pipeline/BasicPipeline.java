package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Transform;
import com.cgp.graphics.util.Rasterization;
import com.cgp.graphics.util.ZBuffer;
import com.cgp.math.AffineTransform.AffineTransform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicPipeline implements Pipeline {
    private Scene scene;
    private Map<GameObject, Matrix4> modelMatrices;
    private Map<GameObject, Matrix4> viewMatrices;
    private Matrix4 clipMatrix;
    private Set<Vector3F> vertices2D;

    public BasicPipeline(Scene scene) {
        setScene(scene);
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {
        var canvas = graphicsContext.getCanvas();
        ZBuffer.filterPoints(vertices2D).stream()
                .map(point -> Rasterization.toScreenCoordinates(point,
                                (float) canvas.getWidth(),
                                (float) canvas.getHeight()
                        )
                )
                .forEach(point -> graphicsContext.getPixelWriter()
                        .setColor(
                                Math.round(point.getX()),
                                Math.round(point.getY()),
                                Color.RED)
                );
    }

    @Override
    public void run() {
        modelMatrices = calculateModelMatrices();
        viewMatrices = calculateViewMatrices();
        clipMatrix = calculateClipMatrix();

        vertices2D = calculateVertices2D();
    }

    private Set<Vector3F> calculateVertices2D() {
        return scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .flatMap(gameObject -> gameObject.getMesh().getVertices4()
                        .values().stream().map(vertex -> Rasterization
                                .vertexToNormalizedScreen(vertex,
                                        clipMatrix,
                                        viewMatrices.get(gameObject),
                                        modelMatrices.get(gameObject)
                                )
                        )
                )
                .collect(Collectors.toSet());
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
                .collect(Collectors.toMap(
                                gameObject -> gameObject,
                                gameObject -> applyAffineTransform(gameObject.getTransform())
                                        .getTransformationMatrix()
                        )
                );
    }

    protected Map<GameObject, Matrix4> calculateViewMatrices() {
        return scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .collect(Collectors.toMap(
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
