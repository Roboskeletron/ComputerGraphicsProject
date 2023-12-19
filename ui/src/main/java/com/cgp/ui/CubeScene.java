package com.cgp.ui;

import com.cgp.graphics.components.BasicTransform;
import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector3F;

import java.util.ArrayList;

public class CubeScene {
    public static Scene createScene(Camera camera){
        ArrayList<GameObject> objects = new ArrayList<>();

        objects.add(camera);

        ArrayList<Polygon> polygons = new ArrayList<>();
        var a = new Vector3F(0, 0, 0);
        var b = new Vector3F(0, 1, 0);
        var c = new Vector3F(1, 1, 0);
        var d = new Vector3F(1, 0, 0);
        var e = new Vector3F(0, 0, 1);
        var f = new Vector3F(0, 1, 1);
        var g = new Vector3F(1, 1, 1);
        var h = new Vector3F(1, 0, 1);

        polygons.add(new Polygon.Builder()
                .withVertices(c, g, h, d)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(a, e, f, b)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(g, c, b, f)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(d, h, e, a)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(h, g, f, e)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(c, d, a, b)
                .build());

        Mesh mesh = new Mesh(polygons);
        BasicTransform transform = new BasicTransform();
        objects.add(new Model3D(transform, mesh, null));

        transform.setPosition(new Vector3F(1, 1, 1));
        transform.setRotation(new Vector3F(0, (float) (Math.PI / 3), 0));

        return new Scene(objects);
    }
}
