package com.cgp.graphics.util;

import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.math.vector.Vector3F;
import javafx.scene.paint.Color;

public class Light {
    public static void applyDirectLight(Vector3F ray, ColoredPoint vertex, Vector3F normal, float k) {
        if (k < 0 || k > 1) {
            throw new IllegalArgumentException("k must be between 0 and 1 (inclusive)");
        }

        var l = normal.scalarMultiply(-1).dotProduct(ray);

        l = l < 0 ? 0 : l;

        var color = vertex.getColor();
        var rgb = new Vector3F((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());

        rgb = rgb.scalarMultiply(1 - k)
                .add(
                        rgb.scalarMultiply(k * l)
                )
        ;

        vertex.setColor(new Color(
                rgb.getX(),
                rgb.getY(),
                rgb.getZ(),
                1)
        );
    }
}
