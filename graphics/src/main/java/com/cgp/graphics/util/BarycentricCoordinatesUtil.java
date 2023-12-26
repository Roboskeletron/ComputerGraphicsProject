package com.cgp.graphics.util;

import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.graphics.primitives.rasterization.ScreenPoint;
import com.cgp.math.vector.Vector3F;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class BarycentricCoordinatesUtil {
    public static Stream<BarycentricVector> calculateBarycentricScreenPoints(BarycentricCoordinates coordinates) {
        if (coordinates.getA() instanceof ScreenPoint a &&
                coordinates.getB() instanceof ScreenPoint b &&
                coordinates.getC() instanceof ScreenPoint c) {
            int minX = getMin(createStream(a, b, c).map(Vector3F::getX));
            int maxX = getMax(createStream(a, b, c).map(Vector3F::getX));
            int minY = getMin(createStream(a, b, c).map(Vector3F::getY));
            int maxY = getMax(createStream(a, b, c).map(Vector3F::getY));

            return getScreenPoints(minX, maxX, minY, maxY, coordinates);
        } else {
            throw new IllegalArgumentException("No screen points found");
        }
    }

    private static Stream<BarycentricVector> getScreenPoints(int minX, int maxX, int minY, int maxY, BarycentricCoordinates coordinates) {
        return IntStream.range(minY, maxY + 1).boxed().parallel()
                .flatMap(y -> IntStream.range(minX, maxX + 1).boxed().parallel()
                        .map(x -> new Vector3F(x, y, 1))
                        .map(vector -> new BarycentricVector(coordinates, vector)
                        )
                );
    }

    private static int getMin(Stream<Float> stream) {
        return stream
                .mapToInt(Math::round)
                .min()
                .orElseThrow();
    }

    private static int getMax(Stream<Float> stream) {
        return stream
                .mapToInt(Math::round)
                .max()
                .orElseThrow();
    }

    private static Stream<Vector3F> createStream(ScreenPoint... points) {
        return Arrays.stream(points).parallel()
                .map(ScreenPoint::getPoint);
    }
}
