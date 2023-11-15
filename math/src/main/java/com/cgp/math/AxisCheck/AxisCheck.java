package com.cgp.math.AxisCheck;

import com.cgp.math.vector.Vector3F;

import java.util.List;

public class AxisCheck {

    public static Vector3F getDirectionIfVectorsOnSameLine(List<Vector3F> vectors) {
        Vector3F crossProduct = vectors.get(0).crossProduct(vectors.get(1));

        for (int i = 2; i < vectors.size(); i++) {
            crossProduct = crossProduct.crossProduct(vectors.get(i));
        }

        if (crossProduct.magnitude() == 0) {
            return vectors.get(1).subtract(vectors.get(0));
        }

        return null;
    }

}

