package com.cgp.graphics.util;


import com.cgp.math.util.MathUtil;
import com.cgp.math.vector.Vector2F;
import com.cgp.math.vector.Vector3F;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ZBuffer {
    public static <Vector extends Vector3F> Collection<Vector> filterPoints(Stream<Vector> points) {
        ConcurrentHashMap<Vector2F, Vector> buffer = new ConcurrentHashMap<>();

        points.forEach(point -> ZBuffer.setValue(buffer, point));

        return buffer.values();
    }

    private static <Vector extends Vector3F> void setValue(Map<Vector2F, Vector> buffer, Vector point) {
        Vector2F key = new Vector2F(point.getX(), point.getY());

        if (!buffer.containsKey(key)) {
            buffer.put(key, point);
            return;
        }

        float depth = buffer.get(key).getZ();

        if (MathUtil.compareFloat(point.getZ(), depth) < 0) {
            buffer.put(key, point);
        }
    }
}
