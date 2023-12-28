package com.cgp.graphics.pipeline.mode;

import com.cgp.graphics.components.material.BasicMaterial;
import com.cgp.graphics.components.material.MeshMaterial;
import com.cgp.graphics.entities.GameObject;

import java.util.stream.Stream;

public class MeshMode extends PipelineMode {
    public MeshMode() {
        super("Mesh mode");
    }

    @Override
    public void applyMode(Stream<GameObject> gameObjectStream) {
        gameObjectStream.parallel()
                .filter(GameObject::isDrawable)
                .forEach(this::applyMode);
    }

    private void applyMode(GameObject gameObject){
        var material = getMaterial(gameObject);

        material.setShowMesh(isEnabled());
    }

    private MeshMaterial getMaterial(GameObject gameObject){
        if (gameObject.getMaterial() instanceof MeshMaterial meshMaterial){
            return meshMaterial;
        }

        var meshMaterial = MeshMaterial.fromBasicMaterial((BasicMaterial) gameObject.getMaterial());
        gameObject.setMaterial(meshMaterial);

        return meshMaterial;
    }
}
