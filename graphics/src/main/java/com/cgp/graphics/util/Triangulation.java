package com.cgp.graphics.util;

import com.cgp.graphics.primitives.Polygon;

import java.util.ArrayList;

public class Triangulation {
    public static ArrayList<Polygon> basic(Polygon origin){
        ArrayList<Polygon> polygons = new ArrayList<>();

        if (origin.getVertexCount() == 3){
            polygons.add(origin);
            return polygons;
        }

        for (int i = 2; i < origin.getVertexCount(); i += 2){
            Polygon polygon = new Polygon.Builder()
                    .withVertex(origin.getVertex(0))
                    .withVertex(origin.getVertex(i -1))
                    .withVertex(origin.getVertex(i))
                    .build();

            polygons.add(polygon);
        }

        return polygons;
    }
}
