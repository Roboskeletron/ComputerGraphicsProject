package com.cgp.graphics.pipeline.mode;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.events.EventGenerator;
import com.cgp.graphics.primitives.pipeline.PipelineModeStatusEvent;

import java.util.stream.Stream;

public abstract class PipelineMode {
    private final String name;
    private boolean isEnabled = false;
    private final EventGenerator<PipelineModeStatusEvent> eventEventGenerator = new EventGenerator<>();

    protected PipelineMode(String name) {
        this.name = name;
    }

    public abstract void applyMode(Stream<GameObject> gameObjectStream);

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        eventEventGenerator.publish(new PipelineModeStatusEvent(this, false));
    }

    public void removeMode(){
        isEnabled = false;
        eventEventGenerator.publish(new PipelineModeStatusEvent(this, true));
    }

    public String getName() {
        return name;
    }

    public EventGenerator<PipelineModeStatusEvent> getEventGenerator() {
        return eventEventGenerator;
    }
}
