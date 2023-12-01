package com.cgp.graphics.util;

import com.cgp.math.matrix.Matrix3;
import com.cgp.math.vector.Vector3F;

public class BarycentricCoordinates {
    private final Vector3F a;
    private final Vector3F b;
    private final Vector3F c;
    private final Matrix3 matrix;

    public BarycentricCoordinates(Vector3F a, Vector3F b, Vector3F c) {
        this.a = a;
        this.b = b;
        this.c = c;

        matrix = new Matrix3(new float[][]{
                {a.getX(), b.getX(), c.getX()},
                {a.getY(), b.getY(), c.getY()},
                {1, 1, 1}
        })
                .inverse();
    }

    public Vector3F getBarycentricVector(Vector3F point) {
        return matrix.multiplyVector3(
                new Vector3F(
                        point.getX(),
                        point.getY(),
                        1
                )
        );
    }

    public void setZPixel(Vector3F point) {
        var coordinates = getBarycentricVector(point);

        var z = coordinates.dotProduct(new Vector3F(
                        a.getZ(),
                        b.getZ(),
                        c.getZ()
                )
        );

        point.setZ(z);
    }
}
