package com.cgp.graphics.components.material;

import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.math.util.MathUtil;
import com.cgp.math.vector.Vector3F;
import javafx.scene.paint.Color;

public class MeshMaterial extends BasicMaterial {
    public static Color meshColor = Color.GREEN;
    private static float threshold = 0.01f;

    protected MeshMaterial(){

    }

    @Override
    public ColoredPoint getColoredPoint(BarycentricVector point) {
        var coloredPoint = super.getColoredPoint(point);

        if (isOnMeshLine(point.getLambdaVector())){
            coloredPoint.setColor(meshColor);
        }

        return coloredPoint;
    }

    private boolean isOnMeshLine(Vector3F vector){
        var x = vector.getX();
        var y = vector.getY();
        var z = vector.getZ();

        return MathUtil.compareFloat(x, threshold) <= 0 ||
                MathUtil.compareFloat(y, threshold) <= 0 ||
                MathUtil.compareFloat(z, threshold) <= 0;
    }

    public static MeshMaterial fromBasicMaterial(BasicMaterial basicMaterial){
        var material = new MeshMaterial();
        material.setTexture(basicMaterial.getTexture());
        material.polygonTexturePolygonMap = basicMaterial.polygonTexturePolygonMap;

        return material;
    }
}
