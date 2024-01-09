package com.cgp.graphics.primitives;

import com.cgp.math.matrix.Matrix4;
import com.cgp.math.vector.Vector3F;

public interface Transform {
    Vector3F getPosition();
    Vector3F getRotation();
    Vector3F getScale();
    Matrix4 toWorldCoordinates();
    void setRotation(Vector3F rotation);

    void setScale(Vector3F scale);

    void setPosition(Vector3F translation);
}
