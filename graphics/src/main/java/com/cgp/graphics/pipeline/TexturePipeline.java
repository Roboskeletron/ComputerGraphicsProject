package com.cgp.graphics.pipeline;

import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import javafx.scene.canvas.GraphicsContext;

public class TexturePipeline extends BasicPipeline {
    public TexturePipeline(Scene scene) {
        super(scene);
    }

    @Override
    protected void cleanScreen(GraphicsContext graphicsContext) {
        var canvas = graphicsContext.getCanvas();
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    protected void drawPoint(ColoredPoint point, GraphicsContext graphicsContext) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        graphicsContext.getPixelWriter().setColor(x, y, point.getColor());
    }
}
