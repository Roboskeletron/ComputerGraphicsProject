package com.cgp.graphics.components;

import com.cgp.graphics.entities.Camera;

import java.util.Collection;

public class Scene {
    private final Collection<GameObject> objectCollection;
    private Camera currentCamera;

    public Scene(Collection<GameObject> objectCollection) {
        this.objectCollection = objectCollection;
    }

    public Collection<GameObject> getObjectCollection() {
        return objectCollection;
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public void bakeScene(){
        objectCollection.stream().filter(gameObject -> !(gameObject instanceof Camera)).map(GameObject::getMesh).forEach(Mesh::bakeMesh);
        var optionalCamera = objectCollection.stream().filter(gameObject -> gameObject instanceof Camera).findFirst();

        if (optionalCamera.isEmpty()){
            throw new NullPointerException("Scene has no camera");
        }

        currentCamera = (Camera) optionalCamera.get();
    }
}
