package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.material.BasicMaterial;
import com.cgp.graphics.entities.GameObject;

import java.util.stream.Stream;

public class TextureMode extends PipelineMode {
    protected TextureMode() {
        super("Texture mode");
    }

    @Override
    public void applyMode(Stream<GameObject> gameObjectStream) {
        gameObjectStream.parallel()
                .filter(GameObject::isDrawable)
                .map(TextureMode::mapMaterial)
                .forEach(basicMaterial -> basicMaterial.setUseTexture(isEnabled()));
    }

    private static BasicMaterial mapMaterial(GameObject gameObject){
        return (BasicMaterial) gameObject.getMaterial();
    }
}
