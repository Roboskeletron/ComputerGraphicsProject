package com.cgp.ui;

import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.material.BasicMaterial;
import com.cgp.graphics.components.texture.BasicTexture;
import com.cgp.graphics.components.transform.BasicTransform;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.primitives.mesh.Polygon;
import com.cgp.graphics.primitives.mesh.Vertex;
import com.cgp.math.vector.Vector3F;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CubeScene {
    public static Scene createScene(Camera camera){
        File file = new File("C:\\Users\\wwwrt\\Desktop\\texture.png");
        Image textureImage = new Image(file.toURI().toString());
        BasicTexture texture = new BasicTexture(textureImage);

        BasicMaterial material = new BasicMaterial(texture);

        ArrayList<GameObject> objects = new ArrayList<>();

        objects.add(camera);

        ArrayList<Polygon> polygons = new ArrayList<>();
        var a = new Vertex(0, 0, 0);
        var b = new Vertex(0, 1, 0);
        var c = new Vertex(1, 1, 0);
        var d = new Vertex(1, 0, 0);
        var e = new Vertex(0, 0, 1);
        var f = new Vertex(0, 1, 1);
        var g = new Vertex(1, 1, 1);
        var h = new Vertex(1, 0, 1);

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

        transform.setPosition(new Vector3F(2, 0, 0));
        transform.setRotation(new Vector3F((float) (Math.PI / 6), (float) (Math.PI / 3), 0));
        transform.setScale(new Vector3F(0.8f, 0.8f, 0.8f));


        var vt1 = new Vertex(0.25f, 1, 0);
        var vt2 = new Vertex(0.5f, 1, 0);
        var vt3 = new Vertex(0, 0.75f, 0);
        var vt4 = new Vertex(0.25f, 0.75f, 0);
        var vt5 = new Vertex(0.5f, 0.75f, 0);
        var vt6 = new Vertex(0.75f, 0.75f, 0);
        var vt7 = new Vertex(0, 0.5f, 0);
        var vt8 = new Vertex(0.25f, 0.5f, 0);
        var vt9 = new Vertex(0.5f, 0.5f, 0);
        var vt10 = new Vertex(0.75f, 0.5f, 0);
        var vt11 = new Vertex(0.25f, 0.25f, 0);
        var vt12 = new Vertex(0.5f, 0.25f, 0);
        var vt13 = new Vertex(0.25f, 0, 0);
        var vt14 = new Vertex(0.5f, 0, 0);

        ArrayList<Polygon> texturePolygons = new ArrayList<>();

        texturePolygons.add(new Polygon.Builder()
                        .withVertices(vt10, vt6, vt5, vt9)
                .build());
        texturePolygons.add(new Polygon.Builder()
                .withVertices(vt8, vt4, vt3, vt7)
                .build());
        texturePolygons.add(new Polygon.Builder()
                .withVertices(vt14, vt12, vt11, vt13)
                .build());
        texturePolygons.add(new Polygon.Builder()
                .withVertices(vt9, vt5, vt4, vt8)
                .build());
        texturePolygons.add(new Polygon.Builder()
                .withVertices(vt5, vt2, vt1, vt4)
                .build());
        texturePolygons.add(new Polygon.Builder()
                .withVertices(vt12, vt9, vt8, vt11)
                .build());


        var polygonIterator = polygons.iterator();
        var texturePolygonIterator = texturePolygons.iterator();

        Map<Polygon, Polygon> polygonTexturePolygonMap = IntStream.range(0, polygons.size()).boxed()
                        .collect(Collectors.toMap(
                                i -> polygonIterator.next(),
                                i -> texturePolygonIterator.next()
                        ));

        material.bakeMaterial(polygonTexturePolygonMap);

        objects.add(new Model3D(transform, mesh, material));

        return new Scene(objects);
    }
}
