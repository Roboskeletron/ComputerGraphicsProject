package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Transform;
import com.cgp.graphics.util.Rasterization;
import com.cgp.math.AffineTransform.AffineTransform;
import com.cgp.math.matrix.Matrix4;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.stream.Collectors;

public class BasicPipeline  implements Pipeline {
    private Scene scene;
    private final HashSet<Matrix4> modelMatrices = new HashSet<>();
    private final HashSet<Matrix4> viewMatrices = new HashSet<>();
    private Matrix4 clipMatrix = null;

    public BasicPipeline(Scene scene) {
        setScene(scene);
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {
        if (modelMatrices.isEmpty()){
            calculateModelMatrices();
        }

        if (viewMatrices.isEmpty()){
            calculateViewMatrices();
        }

        if (clipMatrix == null){
            calculateClipMatrix();
        }
    }

    protected AffineTransform applyAffineTransform(Transform transform){
        AffineTransform affineTransform = new AffineTransform();

        var position = transform.getPosition();
        var rotation = transform.getRotation();
        var scale = transform.getScale();

        affineTransform.translate(position.getX(), position.getY(), position.getZ());
        affineTransform.rotate(rotation.getX(), rotation.getY(), rotation.getZ());
        affineTransform.scale(scale.getX(), scale.getY(), scale.getZ());

        return affineTransform;
    }

    protected void calculateModelMatrices(){
        modelMatrices.addAll(scene.getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .map(GameObject::getTransform)
                .map(this::applyAffineTransform)
                .map(AffineTransform::getTransformationMatrix)
                .collect(Collectors.toSet())
        );
    }

    protected void calculateViewMatrices(){
        viewMatrices.addAll(
                scene.getObjectCollection().stream()
                        .filter(gameObject -> !(gameObject instanceof Camera))
                        .map(object -> Rasterization.lookAt(object, scene.getCurrentCamera()))
                        .collect(Collectors.toSet())
        );
    }

    protected void calculateClipMatrix(){
        clipMatrix = Rasterization.clip(scene.getCurrentCamera());
    }

    protected void bakeScene(){
        scene.getObjectCollection().stream().map(GameObject::getMesh).forEach(Mesh::bakeMesh);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        bakeScene();
    }
}
