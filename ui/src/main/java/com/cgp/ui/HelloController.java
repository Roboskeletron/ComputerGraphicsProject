package com.cgp.ui;

import com.cgp.graphics.components.BasicTransform;
import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.objreader.ObjReader;
import com.cgp.graphics.objwriter.ObjWriter;
import com.cgp.graphics.pipeline.BasicPipeline;
import com.cgp.graphics.pipeline.MeshPipeline;
import com.cgp.graphics.pipeline.Pipeline;
import com.cgp.math.vector.Vector3F;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    private Model3D mesh = null;

    public HelloController() {
        camera.setAspectRatio(1);
        camera.setFOV(1f);
        camera.setNPlane(0.01f);
        camera.setFPlane(100);
        ((BasicTransform) camera.getTransform()).setPosition(new Vector3F(-5, 0, 0));
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

        pipeline = new MeshPipeline(scene);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                pipeline.drawScene(mainCanvas.getGraphicsContext2D());
//                pipeline.run();
            }
        };

        onSizeChanged(anchorPane.getWidth(), anchorPane.getHeight());

        timer.start();
    }

    private void handleKeyEvent(KeyEvent keyEvent) {
        var translationVector = new Vector3F(0, 0 ,0);
        var rotationVector = new Vector3F(0, 0, 0);

        switch (keyEvent.getCode()){
            case W -> translationVector.setX(1);
            case S -> translationVector.setX(-1);
            case D -> translationVector.setZ(1);
            case A -> translationVector.setZ(-1);
            case SPACE -> translationVector.setY(1);
            case CONTROL -> translationVector.setY(-1);

            case UP -> rotationVector.setX(1);
            case DOWN -> rotationVector.setX(-1);
            case RIGHT -> rotationVector.setY(1);
            case LEFT -> rotationVector.setY(-1);
        }

        translationVector = translationVector.scalarMultiply(velocity);
        rotationVector = rotationVector.scalarMultiply(velocity);

        var position = camera.getTransform().getPosition().add(translationVector);
        var rotation = camera.getTransform().getRotation().add(rotationVector);

        var transform = (BasicTransform) camera.getTransform();
        transform.setPosition(position);
        transform.setRotation(rotation);

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

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) mainCanvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = (Model3D) ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        String file = "Model.obj";
//        ObjWriter.writeModelToObjFile(file, model);
    }

    @FXML
    private void onSaveNewModelMenuItemClick() {}

    @FXML
    private void onSelectXAxisMenuItemClick() {}

    @FXML
    private void onSelectYAxisMenuItemClick() {}

    @FXML
    private void onSelectZAxisMenuItemClick() {}
}