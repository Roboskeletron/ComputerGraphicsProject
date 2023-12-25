package com.cgp.graphics.util;

import com.cgp.math.vector.Vector3F;

public final class AffineTransform extends com.cgp.math.AffineTransform.AffineTransform {
    public void translate(Vector3F vector) {
        translate(vector.getX(), vector.getY(), vector.getZ());
    }

    public void rotate(Vector3F vector) {
        rotate(vector.getX(), vector.getY(), vector.getZ());
    }

    public void scale(Vector3F vector){
        scale(vector.getX(), vector.getY(), vector.getZ());
    }

    public static final class Builder {
        private final AffineTransform affineTransform;

        public Builder() {
            affineTransform = new AffineTransform();
        }

        public AffineTransform build(){
            return affineTransform;
        }

        public Builder withTranslation(Vector3F vector){
            affineTransform.translate(vector);
            return this;
        }

        public Builder withRotation(Vector3F vector){
            affineTransform.rotate(vector);
            return this;
        }

        public Builder withScale(Vector3F vector){
            affineTransform.scale(vector);
            return this;
        }

        public Builder withTranslation(float x, float y, float z){
            affineTransform.translate(x, y, z);
            return this;
        }

        public Builder withRotation(float x, float y, float z){
            affineTransform.rotate(x, y, z);
            return this;
        }

        public Builder withScale(float x, float y, float z){
            affineTransform.scale(x, y, z);
            return this;
        }
    }
}
