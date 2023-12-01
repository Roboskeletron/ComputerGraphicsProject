package com.cgp.graphics.primitives;

import com.cgp.graphics.util.BarycentricCoordinates;
import com.cgp.math.vector.Vector3F;
import javafx.scene.paint.Color;

public interface Texture {
    Color getColor(BarycentricCoordinates barycentricCoordinates, Vector3F point);
}