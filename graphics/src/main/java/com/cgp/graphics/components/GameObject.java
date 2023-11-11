package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Material;
import com.cgp.graphics.primitives.Transform;

public class GameObject {
    private Transform transform;
    private Mesh mesh;
    private Material material;

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
