package com.cgp.graphics.pipeline;

import javafx.scene.canvas.GraphicsContext;

public interface Pipeline {
    void drawScene(GraphicsContext graphicsContext);
    void run();
}
