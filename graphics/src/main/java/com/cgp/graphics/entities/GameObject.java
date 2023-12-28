package com.cgp.graphics.entities;

import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.components.material.Material;
import com.cgp.graphics.components.transform.Transform;

public class GameObject implements Cloneable {
    private Transform transform;
    private Mesh mesh;
    private Material material;

    protected GameObject(Transform transform, Mesh mesh, Material material) {
        this.transform = transform;
        this.mesh = mesh;
        this.material = material;
    }

    public boolean isDrawable(){
        return transform != null && mesh != null && material != null;
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

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public GameObject clone() {
        try {
            return (GameObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
