package com.cgp.graphics.pipeline;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector3F;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Map;

public class MeshPipeline  extends  BasicPipeline {
    public MeshPipeline(Scene scene) {
        super(scene);
    }

    @Override
    protected void rasterization(Map<Vector3F, Vector3F> points, GraphicsContext graphicsContext) {
        super.rasterization(points, graphicsContext);
        drawMesh(points, graphicsContext);
    }

    private void drawMesh(Map<Vector3F, Vector3F> points, GraphicsContext graphicsContext){
        super.getScene().getObjectCollection().stream()
                .filter(gameObject -> !(gameObject instanceof Camera))
                .map(GameObject::getMesh)
                .flatMap(mesh -> mesh.getTriangulatedPolygons().stream())
                .filter(polygon -> Arrays.stream(polygon.getVertices()).allMatch(points::containsKey))
                .forEach(polygon -> drawPolygon(polygon, points, graphicsContext));
    }

    private void drawPolygon(Polygon polygon, Map<Vector3F, Vector3F> points, GraphicsContext graphicsContext){
        graphicsContext.setStroke(Color.GREEN);
        var a = points.get(polygon.getVertex(0));
        var b = points.get(polygon.getVertex(1));

        graphicsContext.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());

        a = points.get(polygon.getVertex(1));
        b = points.get(polygon.getVertex(2));

        graphicsContext.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }
}
