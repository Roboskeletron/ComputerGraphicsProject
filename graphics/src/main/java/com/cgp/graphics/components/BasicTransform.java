package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Transform;
import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;

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

    @Override
    public Matrix4 toWorldCoordinates() {
        return null;
    }
}
