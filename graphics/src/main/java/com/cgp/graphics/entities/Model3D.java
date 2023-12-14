package com.cgp.graphics.entities;

import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.primitives.Material;
import com.cgp.graphics.primitives.Transform;

public class Model3D extends GameObject {
    public Model3D(Transform transform, Mesh mesh, Material material) {
        super(transform, mesh, material);
    }
}
