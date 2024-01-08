package com.cgp.graphics.components;

import com.cgp.graphics.primitives.Material;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.graphics.primitives.Transform;
import com.cgp.math.vector.Vector2F;
import com.cgp.math.vector.Vector3F;

import java.util.ArrayList;

public class GameObject {
    private Transform transform;
    private Mesh mesh;
    private Material material;

    private final ArrayList<Vector3F> vertices = new ArrayList<Vector3F>();
    private final ArrayList<Vector2F> textureVertices = new ArrayList<Vector2F>();
    private final ArrayList<Vector3F> normals = new ArrayList<Vector3F>();
    private final ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public ArrayList<Vector3F> getVertices() {
        return new ArrayList<>(vertices);
    }


    public GameObject() {
        this.transform = null;
        this.mesh = null;
        this.material = null;
    }

    protected GameObject(Transform transform, Mesh mesh, Material material) {
        this.transform = transform;
        this.mesh = mesh;
        this.material = material;
    }



    public Material getMaterial() {
        return material;
    }

    public Transform getTransform() {
        return transform;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void addVertex(Vector3F vertex) {
        this.vertices.add(vertex);
    }

    public ArrayList<Vector2F> getTextureVertices() {
        return new ArrayList<>(textureVertices);
    }

    public void addTextureVertex(Vector2F textureVertex) {
        this.textureVertices.add(textureVertex);
    }

    public ArrayList<Vector3F> getNormals() {
        return new ArrayList<>(normals);
    }

    public void addNormal(Vector3F normal) {
        this.normals.add(normal);
    }

    public ArrayList<Polygon> getPolygons() {
        return new ArrayList<>(polygons);
    }

    public void addPolygon(Polygon polygon) {
        this.polygons.add(polygon);
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }
}
