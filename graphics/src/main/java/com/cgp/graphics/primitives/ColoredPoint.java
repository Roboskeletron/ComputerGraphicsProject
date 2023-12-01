package com.cgp.graphics.primitives;

import com.cgp.math.vector.Vector3F;
import javafx.scene.paint.Color;

public class ColoredPoint extends Vector3F {
    private static Color defaultColor = Color.BLACK;
    private Color color;
    public ColoredPoint(float x, float y, float z, Color color) {
        super(x, y, z);
        this.color = color;
    }

    public ColoredPoint(Vector3F vector, Color color){
        this(vector.getX(), vector.getY(), vector.getZ(), color);
    }

    public ColoredPoint(float x, float y, float z){
        this(x, y, z, defaultColor);
    }

    public ColoredPoint(Vector3F vector){
        this(vector, defaultColor);
    }

    public static Color getDefaultColor() {
        return defaultColor;
    }

    public static void setDefaultColor(Color defaultColor) {
        if (defaultColor == null){
            throw new NullPointerException();
        }
        ColoredPoint.defaultColor = defaultColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == null){
            throw new NullPointerException();
        }
        this.color = color;
    }
}
