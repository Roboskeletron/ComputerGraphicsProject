package com.cgp.graphics.util;

import com.cgp.graphics.primitives.BarycentricVector;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.matrix.Matrix3;
import com.cgp.math.vector.Vector3F;

import java.util.Map;

public class BarycentricCoordinates {
    private final Vector3F a;
    private final Vector3F b;
    private final Vector3F c;
    private final Matrix3 matrix;

    public BarycentricCoordinates(Vector3F a, Vector3F b, Vector3F c) {
        this.a = a;
        this.b = b;
        this.c = c;

        Matrix3 matrix = null;
        try {
            matrix = new Matrix3(new float[][]{
                    {a.getX(), b.getX(), c.getX()},
                    {a.getY(), b.getY(), c.getY()},
                    {1, 1, 1}
            })
                    .inverse();
        }
        catch (ArithmeticException ignored){
        }
        finally {
            this.matrix = matrix;
        }
    }

    public static BarycentricCoordinates fromPolygon(Polygon polygon, Map<? extends Vector3F, ? extends Vector3F> vertexScreenPointMap){
        if (polygon.getVertexCount() != 3){
            throw new IllegalArgumentException("Polygon must be triangulated");
        }

        var a = vertexScreenPointMap.get(polygon.getVertex(0));
        var b = vertexScreenPointMap.get(polygon.getVertex(1));
        var c = vertexScreenPointMap.get(polygon.getVertex(2));

        return new BarycentricCoordinates(a, b, c);
    }

    public Vector3F getBarycentricVector(Vector3F point) {
        if (matrix == null){
            return new Vector3F(-1, -1,  -1);
        }

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

    public static boolean checkLambdaVector(BarycentricVector vector){
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
