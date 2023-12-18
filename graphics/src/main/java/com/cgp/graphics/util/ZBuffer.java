package com.cgp.graphics.util;


import com.cgp.math.util.MathUtil;
import com.cgp.math.vector.Vector2F;
import com.cgp.math.vector.Vector3F;

import java.util.*;

public class ZBuffer {
    public static Collection<Map.Entry<Vector3F, Vector3F>> filterPoints(Map<Vector3F, Vector3F> points){
        HashMap<Vector2F, Map.Entry<Vector3F, Vector3F>> buffer = new HashMap<>();

        points.entrySet().stream().forEachOrdered(point -> ZBuffer.setValue(buffer, point));

        return buffer.values();
    }

    private static void setValue(Map<Vector2F, Map.Entry<Vector3F, Vector3F>> buffer,
                                 Map.Entry<Vector3F, Vector3F> point){
        Vector2F key = new Vector2F(point.getValue().getX(), point.getValue().getY());

        if (!buffer.containsKey(key)){
            buffer.put(key,  point);
            return;
        }

        float depth = buffer.get(key).getValue().getZ();

        if (MathUtil.compareFloat(point.getValue().getZ(), depth) < 0){
            buffer.put(key, point);
        }
    }
}
