package com.cgp.ui;

import com.cgp.graphics.components.BasicTransform;
import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.components.Mesh;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.pipeline.BasicPipeline;
import com.cgp.graphics.pipeline.Pipeline;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector3F;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    private Pipeline pipelie;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        HelloController controller = fxmlLoader.getController();

        Canvas canvas = controller.getMainCanvas();
        canvas.setWidth(400);
        canvas.setHeight(400);

        ArrayList<GameObject> objects = new ArrayList<>();

        var camera = new Camera();
        objects.add(camera);
        camera.setAspectRatio(1);
        camera.setFOV((float) (Math.PI / 2));
        camera.setNPlane(0);
        camera.setNPlane(100);

        ArrayList<Polygon> polygons = new ArrayList<>();
        var a = new Vector3F(0, 0, 0);
        var b = new Vector3F(1, 0, 0);
        var c = new Vector3F(1, 1, 0);
        var d = new Vector3F(0, 1, 0);
        var e = new Vector3F(0, 0, 1);
        var f = new Vector3F(1, 0, 1);
        var g = new Vector3F(1, 1, 1);
        var h = new Vector3F(0, 1, 1);

        polygons.add(new Polygon.Builder()
                        .withVertices(a, b, c, d)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(e, f, g, h)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(a, b, e, f)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(c, d, g, h)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(a, d, e, h)
                .build());
        polygons.add(new Polygon.Builder()
                .withVertices(b, c, f, g)
                .build());

        Mesh mesh = new Mesh(polygons);
        BasicTransform transform = new BasicTransform();
        objects.add(new Model3D(transform, mesh, null));

        transform.setPosition(new Vector3F(1, 1, 1));

        com.cgp.graphics.components.Scene myScene = new com.cgp.graphics.components.Scene(objects);
        pipelie = new BasicPipeline(myScene);
        pipelie.drawScene(canvas.getGraphicsContext2D());
    }

    public static void main(String[] args) {
        launch();
    }
}