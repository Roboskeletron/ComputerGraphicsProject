package ru.vsu.cs.filozop;

public class Vector4 {
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public Vector4 add(Vector4 other) {
        return new Vector4(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
    }

    public Vector4 subtract(Vector4 other) {
        return new Vector4(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
    }

    public Vector4 scalarMultiply(double scalar) {
        return new Vector4(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    public double dotProduct(Vector4 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    public Vector4 normalize() {
        double magnitude = this.magnitude();
        return new Vector4(this.x / magnitude, this.y / magnitude, this.z / magnitude, this.w / magnitude);
    }
}
