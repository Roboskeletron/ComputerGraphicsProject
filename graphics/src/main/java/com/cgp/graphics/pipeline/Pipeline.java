package com.cgp.graphics.pipeline;

import com.cgp.graphics.components.Scene;
import javafx.scene.canvas.GraphicsContext;

public interface Pipeline {
    void drawScene(GraphicsContext graphicsContext);
    void run();
}
