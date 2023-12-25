package com.cgp.graphics.entities;

import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.material.Material;
import com.cgp.graphics.components.transform.Transform;

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
