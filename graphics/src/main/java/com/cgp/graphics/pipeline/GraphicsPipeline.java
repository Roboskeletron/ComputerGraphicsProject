package com.cgp.graphics.pipeline;

import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.events.Subscriber;
import com.cgp.graphics.pipeline.mode.MeshMode;
import com.cgp.graphics.pipeline.mode.PipelineMode;
import com.cgp.graphics.pipeline.mode.TextureMode;
import com.cgp.graphics.primitives.pipeline.PipelineModeStatusEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Iterator;

public final class GraphicsPipeline implements Pipeline, Subscriber<PipelineModeStatusEvent> {
    private final Pipeline pipeline;
    private Color backgroundColor = Color.DARKGREY;
    private final HashMap<String, PipelineMode> pipelineModes = new HashMap<>();

    public GraphicsPipeline(Scene scene){
        pipeline = new BasicPipeline(scene);
        addPipelineMode(new TextureMode());
        addPipelineMode(new MeshMode());
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {
        graphicsContext.setFill(backgroundColor);
        pipeline.drawScene(graphicsContext);
    }

    @Override
    public void run() {
        pipeline.run();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        if (backgroundColor == null){
            throw new NullPointerException("Background color cant be null");
        }

        this.backgroundColor = backgroundColor;
    }

    public void addPipelineMode(PipelineMode pipelineMode){
        var result = pipelineModes.putIfAbsent(pipelineMode.getName(), pipelineMode);

        if (result == null){
            applyMode(pipelineMode);
            pipelineMode.getEventGenerator().subscribe(this);
        }
    }

    public void removePipelineMode(String name){
        var mode = pipelineModes.get(name);

        if (mode == null){
            throw new NullPointerException("Pipeline mode with name " + name + " not found");
        }

        mode.setEnabled(false);
        pipelineModes.remove(name);
    }

    public PipelineMode getPipelineMode(String name){
        return pipelineModes.get(name);
    }

    public Iterator<PipelineMode> getPipelineModes(){
        return pipelineModes.values().iterator();
    }

    private void applyMode(PipelineMode mode){
        var gameObjects = ((BasicPipeline) pipeline).getScene().getObjectCollection().parallelStream();
        mode.applyMode(gameObjects);
    }

    @Override
    public void process(PipelineModeStatusEvent event) {
        var mode = (PipelineMode) event.getSender();

        applyMode(mode);

        if (event.isEndSubscription()){
            mode.getEventGenerator().unsubscribe(this);
        }
    }
}
