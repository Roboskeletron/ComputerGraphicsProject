package com.cgp.graphics.components.material;

import com.cgp.graphics.components.texture.Texture;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.graphics.primitives.mesh.Polygon;

import java.util.Map;

public abstract class Material {
    protected Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public abstract void bakeMaterial(Map<Polygon, Polygon> polygonTexturePolygonMap);

    public abstract ColoredPoint getColoredPoint(BarycentricVector point);
}
