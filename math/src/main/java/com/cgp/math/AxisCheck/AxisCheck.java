package com.cgp.math.AxisCheck;

import com.cgp.math.vector.Vector3F;

import java.util.List;

public class AxisCheck {

    public static boolean isVectorOnAxis(List<Vector3F> vectors) {
        for (Vector3F vector : vectors) {
            if (vector.getX() == 0 || vector.getY() == 0 || vector.getZ() == 0) {
                return true;
            }
        }
        return false;
    }
}

