package com.cgp.ui;

import com.cgp.graphics.components.BasicTransform;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.pipeline.BasicPipeline;
import com.cgp.graphics.pipeline.Pipeline;
import com.cgp.math.vector.Vector3F;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class HelloController {
    @FXML
    private Canvas mainCanvas;
    @FXML
    private AnchorPane anchorPane;

    private final Camera camera = new Camera();
    private Pipeline pipeline;
    private AnimationTimer timer;
    private float velocity = 0.5f;
    private Scene scene;

    public HelloController() {
        camera.setAspectRatio(1);
        camera.setFOV((float) (Math.PI / 2));
        camera.setNPlane(0);
        camera.setNPlane(100);
    }

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) ->
                onSizeChanged(newValue.doubleValue(),
                        mainCanvas.getHeight()
                )
        );
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) ->
                onSizeChanged(mainCanvas.getWidth(),
                        newValue.doubleValue()
                )
        );


        var scene = CubeScene.createScene(camera);

        pipeline = new BasicPipeline(scene);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                pipeline.drawScene(mainCanvas.getGraphicsContext2D());
            }
        };

        onSizeChanged(anchorPane.getWidth(), anchorPane.getHeight());

        timer.start();
    }

    private void handleKeyEvent(KeyEvent keyEvent) {
        var translationVector = new Vector3F(0, 0 ,0);
        var direction = camera.getTransform().getRotation();

        switch (keyEvent.getCode()){
            case W -> translationVector.setX(1);
            case S -> translationVector.setX(-1);
            case D -> translationVector.setY(1);
            case A -> translationVector.setY(-1);
            case SPACE -> translationVector.setZ(1);
            case CONTROL -> translationVector.setZ(-1);
        }

        translationVector = translationVector.scalarMultiply(velocity);

        var position = camera.getTransform().getPosition();
        ((BasicTransform) camera.getTransform()).setPosition(position.add(translationVector));

        pipeline.run();
    }

    private Vector3F calculateTranslationVector(Vector3F vector, Vector3F direction){
        vector = vector.scalarMultiply(velocity);

        var u1 = vector.normalize();
        var u2 = direction.normalize();

        var f = Math.acos(u1.dotProduct(u2));

        var u21Cross = u2.crossProduct(u1);

         var u3 = u21Cross.scalarMultiply(1 / u21Cross.magnitude()).crossProduct(u2);

         return u2.scalarMultiply((float) Math.cos(f)).add(
                 u3.scalarMultiply((float) Math.sin(f))
         )
                 .scalarMultiply(vector.magnitude());
    }

    private void onSizeChanged(double width, double height) {

        float aspectRatio = (float) (height / width);

        camera.setAspectRatio(aspectRatio);
        mainCanvas.setHeight(height);
        mainCanvas.setWidth(width);

        pipeline.run();
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(this::handleKeyEvent);
    }
}