package com.cgp.graphics.primitives;

import com.cgp.graphics.util.BarycentricCoordinates;
import com.cgp.math.vector.Vector3F;

public class BarycentricVector extends Vector3F {
    private final BarycentricCoordinates barycentricCoordinates;

    public BarycentricVector(BarycentricCoordinates barycentricCoordinates, Vector3F origin) {
        super(origin.getX(), origin.getY(), origin.getZ());
        this.barycentricCoordinates = barycentricCoordinates;
        barycentricCoordinates.setZPixel(this);
    }

    public BarycentricCoordinates getBarycentricCoordinates() {
        return barycentricCoordinates;
    }

    public Vector3F getLambdaVector(){
        return barycentricCoordinates.getBarycentricVector(this);
    }
}
