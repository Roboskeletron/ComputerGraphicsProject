package com.cgp.graphics.util;


import com.cgp.math.util.MathUtil;
import com.cgp.math.vector.Vector2F;
import com.cgp.math.vector.Vector3F;

import java.util.*;

public class ZBuffer {
    public static Collection<Vector3F> filterPoints(Collection<Vector3F> points){
        HashMap<Vector2F, Vector3F> buffer = new HashMap<>();

        points.stream().forEachOrdered(point -> ZBuffer.setValue(buffer, point));

        return buffer.values();
    }

    private static void setValue(Map<Vector2F, Vector3F> buffer, Vector3F point){
        Vector2F key = new Vector2F(point.getX(), point.getY());

        if (!buffer.containsKey(key)){
            buffer.put(key,  point);
            return;
        }

        float depth = buffer.get(key).getZ();

        if (MathUtil.compareFloat(point.getZ(), depth) < 0){
            buffer.put(key, point);
        }
    }
}
