package com.cgp.math.vector;

import com.cgp.math.util.MathUtil;

public class Vector2F {
    private float x;
    private float y;
    private float magnitude = -1;

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        magnitude = -1;
    }

    public void setY(float y) {
        this.y = y;
        magnitude = -1;
    }

    public Vector2F add(Vector2F other) {
        return new Vector2F(this.x + other.x, this.y + other.y);
    }

    public Vector2F subtract(Vector2F other) {
        return new Vector2F(this.x - other.x, this.y - other.y);
    }

    public Vector2F scalarMultiply(float scalar) {
        return new Vector2F(this.x * scalar, this.y * scalar);
    }

    public float dotProduct(Vector2F other) {
        return this.x * other.x + this.y * other.y;
    }

    public float magnitude() {
        if (magnitude >= 0) {
            return magnitude;
        }

        magnitude = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        return magnitude;
    }

    public Vector2F normalize() {
        float magnitude = this.magnitude();
        return new Vector2F(this.x / magnitude, this.y / magnitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2F vector){
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
