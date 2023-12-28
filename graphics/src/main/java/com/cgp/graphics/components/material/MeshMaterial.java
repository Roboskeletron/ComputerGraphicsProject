package com.cgp.graphics.components.material;

import com.cgp.graphics.components.texture.Texture;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.math.util.MathUtil;
import com.cgp.math.vector.Vector3F;
import javafx.scene.paint.Color;

public class MeshMaterial extends BasicMaterial {
    private static Color meshColor = Color.GREEN;
    private static final float meshLineThickness = 0.01f;
    private boolean showMesh = true;

    protected MeshMaterial(Texture texture){
        super(texture);
    }

    @Override
    public ColoredPoint getColoredPoint(BarycentricVector point) {
        var coloredPoint = super.getColoredPoint(point);

        if (showMesh){
            return coloredPoint;
        }

        if (isOnMeshLine(point.getLambdaVector())){
            coloredPoint.setColor(meshColor);
        }

        return coloredPoint;
    }

    private boolean isOnMeshLine(Vector3F vector){
        var x = vector.getX();
        var y = vector.getY();
        var z = vector.getZ();

        return MathUtil.compareFloat(x, meshLineThickness) <= 0 ||
                MathUtil.compareFloat(y, meshLineThickness) <= 0 ||
                MathUtil.compareFloat(z, meshLineThickness) <= 0;
    }

    public static MeshMaterial fromBasicMaterial(BasicMaterial basicMaterial){
        var material = new MeshMaterial(basicMaterial.getTexture());
        material.polygonTexturePolygonMap = basicMaterial.polygonTexturePolygonMap;

        return material;
    }

    public static Color getMeshColor() {
        return meshColor;
    }

    public static void setMeshColor(Color meshColor) {
        if (meshColor == null){
            throw new NullPointerException("Mesh color cant be null");
        }

        MeshMaterial.meshColor = meshColor;
    }

    public boolean isShowMesh() {
        return showMesh;
    }

    public void setShowMesh(boolean showMesh) {
        this.showMesh = showMesh;
    }
}
