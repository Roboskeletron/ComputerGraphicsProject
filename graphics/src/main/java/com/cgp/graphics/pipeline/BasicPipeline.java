package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Transform;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.AffineTransform.AffineTransform;
import com.cgp.math.matrix.Matrix4;
import javafx.scene.canvas.GraphicsContext;

import java.util.Map;
import java.util.stream.Collectors;

public class BasicPipeline implements Pipeline {
    private Scene scene;
    private Map<GameObject, Matrix4> modelMatrices;
    private Map<GameObject, Matrix4> viewMatrices;
    private Matrix4 clipMatrix = null;

    public BasicPipeline(Scene scene) {
        setScene(scene);
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {
        modelMatrices = modelMatrices == null ? calculateModelMatrices() : modelMatrices;

        viewMatrices = modelMatrices == null ? calculateViewMatrices() : viewMatrices;

        clipMatrix = clipMatrix == null ? calculateClipMatrix() : clipMatrix;


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

    protected void bakeScene() {
        scene.bakeScene();
        modelMatrices = null;
        viewMatrices = null;
        clipMatrix = null;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        bakeScene();
    }
}
