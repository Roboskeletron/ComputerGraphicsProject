package com.cgp.graphics.entities;

import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.material.Material;
import com.cgp.graphics.components.transform.Transform;

public class Model3D extends GameObject {
    public Model3D(Transform transform, Mesh mesh, Material material) {
        super(transform, mesh, material);
    }
}
