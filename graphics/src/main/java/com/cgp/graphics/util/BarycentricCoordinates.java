package com.cgp.graphics.util;

import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ScreenPoint;
import com.cgp.math.matrix.Matrix3;
import com.cgp.math.vector.Vector3F;

public class BarycentricCoordinates {
    private final Vector3F a;
    private final Vector3F b;
    private final Vector3F c;
    private final Matrix3 matrix;

    private BarycentricCoordinates(Vector3F a, Vector3F b, Vector3F c, Matrix3 matrix) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.matrix = matrix;
    }

    private static Matrix3 computeMatrix(Vector3F a, Vector3F b, Vector3F c) {
        Matrix3 matrix = null;
        try {
            matrix = new Matrix3(new float[][]{
                    {a.getX(), b.getX(), c.getX()},
                    {a.getY(), b.getY(), c.getY()},
                    {1, 1, 1}
            })
                    .inverse();
        } catch (ArithmeticException ignored) {
        }

        return matrix;
    }

    public static BarycentricCoordinates fromPolygon(Polygon polygon) {
        if (polygon.getVertexCount() != 3) {
            throw new IllegalArgumentException("Polygon must be triangulated");
        }

        if (polygon.getVertex(0) instanceof ScreenPoint a &&
                polygon.getVertex(1) instanceof ScreenPoint b &&
                polygon.getVertex(2) instanceof ScreenPoint c) {
            var matrix = computeMatrix(a.getPoint(), b.getPoint(), c.getPoint());

            return new BarycentricCoordinates(a, b, c, matrix);
        } else {
            throw new IllegalArgumentException("Vertices should be instances of ScreenPoint");
        }
    }

    public Vector3F getLambdaVector(Vector3F point) {
        if (matrix == null) {
            return new Vector3F(-1, -1, -1);
        }

        return matrix.multiplyVector3(
                new Vector3F(
                        point.getX(),
                        point.getY(),
                        1
                )
        );
    }

    public static boolean checkLambdaVector(BarycentricVector vector) {
        return vector.getLambdaVector().getX() >= 0 && vector.getLambdaVector().getY() >= 0 && vector.getLambdaVector().getZ() >= 0;
    }

    public Vector3F getA() {
        return a;
    }

    public Vector3F getB() {
        return b;
    }

    public Vector3F getC() {
        return c;
    }
}
