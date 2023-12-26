package com.cgp.graphics.primitives.rasterization;

import com.cgp.graphics.util.BarycentricCoordinates;
import com.cgp.math.vector.Vector3F;

public class BarycentricVector extends Vector3F {
    private final BarycentricCoordinates barycentricCoordinates;
    private Vector3F lambdaVector;

    public BarycentricVector(BarycentricCoordinates barycentricCoordinates, Vector3F origin) {
        super(origin.getX(), origin.getY(), origin.getZ());
        this.barycentricCoordinates = barycentricCoordinates;
        calculateZ();
    }

    private void calculateZ() {
        lambdaVector = barycentricCoordinates.getLambdaVector(this);

        var z = lambdaVector.dotProduct(new Vector3F(
                        ((ScreenPoint) barycentricCoordinates.getA()).getPoint().getZ(),
                        ((ScreenPoint) barycentricCoordinates.getB()).getPoint().getZ(),
                        ((ScreenPoint) barycentricCoordinates.getC()).getPoint().getZ()
                )
        );

        this.setZ(z);
    }

    public BarycentricCoordinates getBarycentricCoordinates() {
        return barycentricCoordinates;
    }

    public Vector3F getLambdaVector() {
        return lambdaVector;
    }
}
