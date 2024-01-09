package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Transform;
import com.cgp.math.AffineTransform.AffineTransform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;

import static javafx.scene.transform.Transform.rotate;

public class BasicTransform implements Transform
{
    protected Vector3F position;
    protected Vector3F rotation;
    protected Vector3F scale;

    public BasicTransform(){
        position = new Vector3F(0, 0, 0);
        rotation = new Vector3F(0, 0, 0);
        scale = new Vector3F(1, 1, 1);
    }
    @Override
    public Vector3F getPosition() {
        return position;
    }

    @Override
    public Vector3F getRotation() {
        return rotation;
    }

    @Override
    public Vector3F getScale() {
        return scale;
    }

    public void setPosition(Vector3F position) {
        this.position = position;
    }

    public void setRotation(Vector3F rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3F scale) {
        this.scale = scale;
    }

    @Override
    public Matrix4 toWorldCoordinates() {
        AffineTransform transform = new AffineTransform();

        transform.scale(scale.getX(), scale.getY(), scale.getZ());
        transform.rotate((float) Math.toRadians(rotation.getX()), (float) Math.toRadians(rotation.getY()), (float) Math.toRadians(rotation.getZ()));
        transform.translate(position.getX(), position.getY(), position.getZ());

        return transform.getTransformationMatrix();
    }
}
