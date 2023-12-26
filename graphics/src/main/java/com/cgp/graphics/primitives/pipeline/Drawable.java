package com.cgp.graphics.primitives.pipeline;

import com.cgp.graphics.primitives.rasterization.ColoredPoint;

import java.util.stream.Stream;

public interface Drawable {
    Stream<ColoredPoint> calculatePoints(int width, int height);
}
