package com.cgp.graphics.components;

import java.util.Collection;

public class Scene {
    private final Collection<GameObject> objectCollection;

    public Scene(Collection<GameObject> objectCollection) {
        this.objectCollection = objectCollection;
    }

    public Collection<GameObject> getObjectCollection() {
        return objectCollection;
    }
}
