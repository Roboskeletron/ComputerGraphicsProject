package com.cgp.math.vector;

import com.cgp.math.util.MathUtil;

public class Vector3F {
    private float x;
    private float y;
    private float z;
    private float magnitude = -1;

    public Vector3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void setX(float x) {
        this.x = x;
        magnitude = -1;
    }

    public void setY(float y) {
        this.y = y;
        magnitude = -1;
    }

    public void setZ(float z) {
        this.z = z;
        magnitude = -1;
    }

    public Vector3F add(Vector3F other) {
        return new Vector3F(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3F subtract(Vector3F other) {
        return new Vector3F(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3F scalarMultiply(float scalar) {
        return new Vector3F(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public float dotProduct(Vector3F other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public float magnitude() {
        if (magnitude >= 0) {
            return magnitude;
        }

        magnitude = (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

        return magnitude;
    }

    public Vector3F normalize() {
        float magnitude = this.magnitude();

        if (magnitude == 0){
            return new Vector3F(0, 0, 0);
        }

        return new Vector3F(this.x / magnitude, this.y / magnitude, this.z / magnitude);
    }

    public Vector3F crossProduct(Vector3F other) {
        float newX = this.y * other.z - this.z * other.y;
        float newY = this.z * other.x - this.x * other.z;
        float newZ = this.x * other.y - this.y * other.x;
        return new Vector3F(newX, newY, newZ);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector3F vector){
            return MathUtil.compareFloat(x, vector.x) == 0 && MathUtil.compareFloat(y, vector.y) == 0 && MathUtil.compareFloat(z, vector.z) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = (int) (19 * hash + x);
        hash = (int) (19 * hash + y);
        hash = (int) (19 * hash + z);
        return hash;
    }
}
