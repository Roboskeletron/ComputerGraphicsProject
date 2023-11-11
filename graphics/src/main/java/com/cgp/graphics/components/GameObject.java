package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Transform;

public class GameObject {
    private Transform transform;
    private Mesh mesh;

    public Transform getTransform() {
        return transform;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
