package com.cgp.graphics.primitives.mesh;

import com.cgp.math.vector.Vector3F;
import com.cgp.math.vector.Vector4F;

public class Vertex extends Vector3F {
    private final Vector4F vertex4F;
    public Vertex(float x, float y, float z) {
        super(x, y, z);
        vertex4F = new Vector4F(x, y, z, 1);
    }

    public Vector4F getVertex4F() {
        return vertex4F;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex vertex){
            return vertex.hashCode() == this.hashCode();
        }

        return false;
    }
}
