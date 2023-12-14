package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.Scene;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.graphics.primitives.Transform;
import com.cgp.math.AffineTransform.AffineTransform;
import com.cgp.math.vector.Vector3F;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicPipeline  implements Pipeline {
    private Scene scene;

    public BasicPipeline(Scene scene) {
        setScene(scene);
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {

    }

    private AffineTransform applyAffineTransform(Transform transform){
        AffineTransform affineTransform = new AffineTransform();

        var position = transform.getPosition();
        var rotation = transform.getRotation();
        var scale = transform.getScale();

        affineTransform.translate(position.getX(), position.getY(), position.getZ());
        affineTransform.rotate(rotation.getX(), rotation.getY(), rotation.getZ());
        affineTransform.scale(scale.getX(), scale.getY(), scale.getZ());

        return affineTransform;
    }

    private void bakeScene(){
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
