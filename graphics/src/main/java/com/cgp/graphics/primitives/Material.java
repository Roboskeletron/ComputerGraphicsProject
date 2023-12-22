package com.cgp.graphics.primitives;

import com.cgp.graphics.components.Mesh;
import com.cgp.math.vector.Vector3F;

import java.util.Map;

public abstract class Material {
    protected Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public abstract void bakeMaterial(Mesh mesh, Map<Vector3F, ColoredPoint> textureVertexMap);

    public abstract ColoredPoint getColoredPoint(BarycentricVector point);
}
