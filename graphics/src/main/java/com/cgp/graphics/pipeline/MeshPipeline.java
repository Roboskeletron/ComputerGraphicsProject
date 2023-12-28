package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.material.BasicMaterial;
import com.cgp.graphics.components.material.MeshMaterial;
import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.primitives.pipeline.DrawableObject;
import com.cgp.graphics.primitives.rasterization.matrix.ClipMatrix;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class MeshPipeline  extends  TexturePipeline {
    public MeshPipeline(Scene scene) {
        super(scene);
    }

    @Override
    public void run() {
        clipMatrix = ClipMatrix.create(getScene().getCurrentCamera());

        drawables = getScene().getObjectCollection().parallelStream()
                .filter(GameObject::isDrawable)
                .map(GameObject::clone)
                .peek(gameObject -> gameObject
                        .setMaterial(MeshMaterial.fromBasicMaterial((BasicMaterial) gameObject.getMaterial())
                        )
                )
                .map(gameObject -> DrawableObject.create(gameObject, clipMatrix))
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }
}
