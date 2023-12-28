package com.cgp.graphics.pipeline;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.primitives.pipeline.Drawable;
import com.cgp.graphics.primitives.pipeline.DrawableObject;
import com.cgp.graphics.primitives.rasterization.ColoredPoint;
import com.cgp.graphics.primitives.rasterization.matrix.ClipMatrix;
import com.cgp.graphics.util.ZBuffer;
import javafx.scene.canvas.GraphicsContext;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class BasicPipeline implements Pipeline {
    private Scene scene;
    protected ConcurrentLinkedQueue<Drawable> drawables;
    protected ClipMatrix clipMatrix;

    public BasicPipeline(Scene scene) {
        setScene(scene);
    }

    @Override
    public void drawScene(GraphicsContext graphicsContext) {
        var canvas = graphicsContext.getCanvas();
       cleanScreen(graphicsContext);

        rasterization(graphicsContext, (int) canvas.getWidth(), (int) canvas.getHeight());
    }

    @Override
    public void run() {
        clipMatrix = ClipMatrix.create(scene.getCurrentCamera());

        drawables = scene.getObjectCollection().parallelStream()
                .filter(GameObject::isDrawable)
                .map(gameObject -> DrawableObject.create(gameObject, clipMatrix))
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    protected void rasterization(GraphicsContext graphicsContext, int width, int height) {
        var points = drawables.parallelStream()
                .flatMap(drawable -> drawable.calculatePoints(width, height));

        ZBuffer.filterPoints(points)
                .forEach(point -> drawPoint(point, graphicsContext));
    }

    protected void drawPoint(ColoredPoint point, GraphicsContext graphicsContext) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        graphicsContext.getPixelWriter().setColor(x, y, point.getColor());
    }

    protected void cleanScreen(GraphicsContext graphicsContext){
        var canvas = graphicsContext.getCanvas();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.bakeScene();
        run();
    }
}
