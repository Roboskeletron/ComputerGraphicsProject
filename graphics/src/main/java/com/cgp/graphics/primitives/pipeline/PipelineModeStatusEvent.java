package com.cgp.graphics.primitives.pipeline;

import com.cgp.graphics.events.Event;
import com.cgp.graphics.pipeline.mode.PipelineMode;

public class PipelineModeStatusEvent extends Event {
    private final boolean endSubscription;
    public PipelineModeStatusEvent(PipelineMode sender, boolean endSubscription) {
        super(sender);
        this.endSubscription = endSubscription;
    }

    public boolean isEndSubscription() {
        return endSubscription;
    }
}
