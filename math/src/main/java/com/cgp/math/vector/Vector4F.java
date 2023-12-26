package com.cgp.math.vector;

import com.cgp.math.util.MathUtil;

public class Vector4F {
    private float x;
    private float y;
    private float z;
    private float w;
    private float magnitude = -1;

    public Vector4F(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setW(float w) {
        this.w = w;
    }

    public Vector4F add(Vector4F other) {
        return new Vector4F(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
    }

    public Vector4F subtract(Vector4F other) {
        return new Vector4F(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
    }

    public Vector4F scalarMultiply(float scalar) {
        return new Vector4F(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    public float dotProduct(Vector4F other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public float magnitude() {
        if (magnitude >= 0) {
            return magnitude;
        }

        magnitude = (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);

        return magnitude;
    }

    public Vector4F normalize() {
        float magnitude = this.magnitude();
        return new Vector4F(this.x / magnitude, this.y / magnitude, this.z / magnitude, this.w / magnitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector4F vector){
            return MathUtil.compareFloat(x, vector.x) == 0 && MathUtil.compareFloat(y, vector.y) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = (int) (19 * hash + x);
        hash = (int) (19 * hash + y);
        return hash;
    }
}
