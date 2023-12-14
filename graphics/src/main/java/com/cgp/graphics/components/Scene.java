package com.cgp.graphics.components;

import com.cgp.graphics.entities.Camera;

import java.util.Collection;

public class Scene {
    private final Collection<GameObject> objectCollection;
    private Camera currentCamera;

    protected Scene(Collection<GameObject> objectCollection) {
        this.objectCollection = objectCollection;
    }

    public Collection<GameObject> getObjectCollection() {
        return objectCollection;
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public void bakeScene(){
        objectCollection.stream().map(GameObject::getMesh).forEach(Mesh::bakeMesh);
    }
}
