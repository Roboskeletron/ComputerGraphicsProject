package com.cgp.graphics.pipeline;

import com.cgp.graphics.entities.GameObject;

import java.util.stream.Stream;

public abstract class PipelineMode {
    private final String name;
    private boolean isEnabled = false;

    protected PipelineMode(String name) {
        this.name = name;
    }

    public abstract void applyMode(Stream<GameObject> gameObjectStream);

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getName() {
        return name;
    }
}
