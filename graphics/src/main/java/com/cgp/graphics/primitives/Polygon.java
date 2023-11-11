package com.cgp.graphics.primitives;

import com.cgp.math.vector.Vector3F;

import java.util.*;

public class Polygon {
    private final Vector3F[] vertices;

    protected Polygon(Vector3F[] vertices) {
        if (vertices.length < 3){
            throw new IllegalArgumentException("Polygon must consist of at least 3 vertices");
        }

        this.vertices = vertices;
    }

    public Vector3F getVertex(int index){
        return vertices[index];
    }

    public int getVertexCount(){
        return vertices.length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polygon polygon){

            return Arrays.equals(vertices, polygon.vertices);
        }
        return false;
    }

    public static class Builder {
        private final LinkedList<Vector3F> vertices = new LinkedList<>();

        public Builder(){

        }

        public Builder(Collection<Vector3F> vertices){
            this.vertices.addAll(vertices);
        }

        public Builder(Vector3F... vertices){
            this.vertices.addAll(Arrays.stream(vertices).toList());
        }

        public Builder withVertex(Vector3F vertex){
            vertices.add(vertex);
            return this;
        }

        public Builder withVertices(Collection<Vector3F> vertices){
            this.vertices.addAll(vertices);
            return this;
        }

        public Builder withVertices(Vector3F... vertices){
            this.vertices.addAll(Arrays.stream(vertices).toList());
            return this;
        }

        public Polygon build(){
            return new Polygon(vertices.toArray(new Vector3F[0]));
        }

        public int getVertexCount(){
            return vertices.size();
        }
    }
}
