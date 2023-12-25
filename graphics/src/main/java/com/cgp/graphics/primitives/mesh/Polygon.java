package com.cgp.graphics.primitives.mesh;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Polygon {
    private final Vertex[] vertices;

    protected Polygon(Vertex[] vertices) {
        if (vertices.length < 3){
            throw new IllegalArgumentException("Polygon must consist of at least 3 vertices");
        }

        this.vertices = vertices;
    }

    public Vertex getVertex(int index){
        return vertices[index];
    }

    public int getVertexCount(){
        return vertices.length;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polygon polygon){

            return Arrays.equals(vertices, polygon.vertices);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        for (var vertex : vertices){
            hash = hash * 19 + vertex.hashCode();
        }

        return hash;
    }

    public static class Builder {
        private final LinkedList<Vertex> vertices = new LinkedList<>();

        public Builder(){

        }

        public Builder(Collection<Vertex> vertices){
            this.vertices.addAll(vertices);
        }

        public Builder(Vertex... vertices){
            this.vertices.addAll(Arrays.stream(vertices).toList());
        }

        public Builder withVertex(Vertex vertex){
            vertices.add(vertex);
            return this;
        }

        public Builder withVertices(Collection<Vertex> vertices){
            this.vertices.addAll(vertices);
            return this;
        }

        public Builder withVertices(Vertex... vertices){
            this.vertices.addAll(Arrays.stream(vertices).toList());
            return this;
        }

        public Polygon build(){
            return new Polygon(vertices.toArray(new Vertex[0]));
        }

        public int getVertexCount(){
            return vertices.size();
        }
    }
}
