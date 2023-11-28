package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Material;
import com.cgp.graphics.primitives.Transform;

public class GameObject {
    private Transform transform;
    private Mesh mesh;
    private Material material;

    protected GameObject(Transform transform, Mesh mesh, Material material) {
        this.transform = transform;
        this.mesh = mesh;
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public Transform getTransform() {
        return transform;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
