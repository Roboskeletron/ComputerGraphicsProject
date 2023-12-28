package com.cgp.graphics.components.material;

import com.cgp.graphics.components.texture.Texture;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.graphics.primitives.mesh.Polygon;
import javafx.scene.paint.Color;

import java.util.Map;

public abstract class Material {
    protected Texture texture;
    protected Color baseColor = Color.BEIGE;
    protected boolean useTexture = true;

    protected Material(Texture texture){
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        if (baseColor == null){
            throw new NullPointerException("Base color cant be null");
        }

        this.baseColor = baseColor;
    }

    public boolean isUseTexture() {
        return useTexture && texture != null;
    }

    public void setUseTexture(boolean useTexture) {
        this.useTexture = useTexture;
    }

    public abstract void bakeMaterial(Map<Polygon, Polygon> polygonTexturePolygonMap);

    public abstract ColoredPoint getColoredPoint(BarycentricVector point);
}
