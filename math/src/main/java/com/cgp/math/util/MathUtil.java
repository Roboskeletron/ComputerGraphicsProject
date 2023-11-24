package com.cgp.math.util;

public class MathUtil {
    public static final float ERROR = 1e-5f;

    public static int compareFloat(float a, float b){
        float delta = a - b;

        if (delta > ERROR){
            return  1;
        }

        if (delta < -ERROR){
            return -1;
        }

        return 0;
    }
}
