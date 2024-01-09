package com.cgp.ui;

import com.cgp.graphics.components.BasicTransform;
import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.objreader.ObjReader;
import com.cgp.graphics.objwriter.ObjWriter;
import com.cgp.graphics.objwriter.ObjWriterException;
import com.cgp.graphics.pipeline.BasicPipeline;
import com.cgp.graphics.pipeline.MeshPipeline;
import com.cgp.graphics.pipeline.Pipeline;
import com.cgp.math.vector.Vector3F;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Canvas mainCanvas;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField textFieldRotateX, textFieldRotateY, textFieldRotateZ;

    @FXML
    private TextField textFieldScaleX, textFieldScaleY, textFieldScaleZ;
    @FXML
    private TextField textFieldTranslateX, textFieldTranslateY, textFieldTranslateZ;

    private final Camera camera = new Camera();
    private Pipeline pipeline;
    private AnimationTimer timer;
    private final float velocity = 0.1f;
    private Scene scene;

    private Model3D mesh = null;

    private double mouseX, mouseY;
    private boolean isMiddleButtonPressed = false;

    public HelloController() {
        camera.setAspectRatio(1);
        camera.setFOV(1f);
        camera.setNPlane(0.01f);
        camera.setFPlane(100);
        ((BasicTransform) camera.getTransform()).setPosition(new Vector3F(-5, 0, 0));
    }

    private void initializeTextFields() {
        textFieldRotateX.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldRotateY.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldRotateZ.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldScaleX.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldScaleY.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldScaleZ.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldTranslateX.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldTranslateY.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());
        textFieldTranslateZ.textProperty().addListener((obs, oldVal, newVal) -> validateAndApplyTransformations());

        textFieldRotateX.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldRotateY.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldRotateZ.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldScaleX.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldScaleY.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldScaleZ.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldTranslateX.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldTranslateY.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });

        textFieldTranslateZ.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                anchorPane.requestFocus();
            }
        });
    }


    private void validateAndApplyTransformations() {
        Vector3F scale = mesh.getTransform().getScale();
        Vector3F translation = mesh.getTransform().getPosition();

        float rotationX = mesh.getTransform().getRotation().getX();
        float rotationY = mesh.getTransform().getRotation().getY();
        float rotationZ = mesh.getTransform().getRotation().getZ();

        try {
            String textFieldRotateXValue = textFieldRotateX.getText();
            String textFieldRotateYValue = textFieldRotateY.getText();
            String textFieldRotateZValue = textFieldRotateZ.getText();

            if (!textFieldRotateXValue.isEmpty()) {
                float rotateXValue = Float.parseFloat(textFieldRotateXValue);
                rotationX = (float) Math.toRadians(rotateXValue);
            }
            if (!textFieldRotateYValue.isEmpty()) {
                float rotateYValue = Float.parseFloat(textFieldRotateYValue);
                rotationY = (float) Math.toRadians(rotateYValue);
            }
            if (!textFieldRotateZValue.isEmpty()) {
                float rotateZValue = Float.parseFloat(textFieldRotateZValue);
                rotationZ = (float) Math.toRadians(rotateZValue);
            }

        } catch (NumberFormatException e) {
            System.err.println("Некорректное значение поворота: " + e.getMessage());
        }

        try {
            String textFieldScaleXValue = textFieldScaleX.getText();
            String textFieldScaleYValue = textFieldScaleY.getText();
            String textFieldScaleZValue = textFieldScaleZ.getText();

            if (!textFieldScaleXValue.isEmpty()) {
                float scaleX = Float.parseFloat(textFieldScaleXValue);
                scale.setX(scale.getX() * scaleX);
            }
            if (!textFieldScaleYValue.isEmpty()) {
                float scaleY = Float.parseFloat(textFieldScaleYValue);
                scale.setY(scale.getY() * scaleY);
            }
            if (!textFieldScaleZValue.isEmpty()) {
                float scaleZ = Float.parseFloat(textFieldScaleZValue);
                scale.setZ(scale.getZ() * scaleZ);
            }
        } catch (NumberFormatException e) {
            System.err.println("Некорректное значение масштабирования: " + e.getMessage());
        }

        try {
            String textFieldTranslateXValue = textFieldTranslateX.getText();
            String textFieldTranslateYValue = textFieldTranslateY.getText();
            String textFieldTranslateZValue = textFieldTranslateZ.getText();

            if (!textFieldTranslateXValue.isEmpty()) {
                float translateX = Float.parseFloat(textFieldTranslateXValue);
                if (translateX == 0) {
                    translateX = 0.0f;
                }
                translation.setX(translateX);
            }
            if (!textFieldTranslateYValue.isEmpty()) {
                float translateY = Float.parseFloat(textFieldTranslateYValue);
                if (translateY == 0) {
                    translateY = 0.0f;
                }
                translation.setY(translateY);
            }
            if (!textFieldTranslateZValue.isEmpty()) {
                float translateZ = Float.parseFloat(textFieldTranslateZValue);
                if (translateZ == 0) {
                    translateZ = 0.0f;
                }
                translation.setZ(translateZ);
            }
        } catch (NumberFormatException e) {
            System.err.println("Некорректное значение перемещения: " + e.getMessage());
        }

        applyTransformations(rotationX, rotationY, rotationZ, scale, translation);
    }


    private void applyTransformations(float rotationX, float rotationY, float rotationZ, Vector3F scale, Vector3F translation) {
        if (mesh != null) {
            Vector3F targetRotation = new Vector3F(rotationX, rotationY, rotationZ);
            mesh.getTransform().setRotation(targetRotation);
            mesh.getTransform().setScale(scale);
            mesh.getTransform().setPosition(translation);
            pipeline.run();
        }
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
        initializeTextFields();


        var scene = CubeScene.createScene(camera);

        ArrayList<GameObject> objects = scene.getObjects();
        mesh = (Model3D) objects.get(objects.size() - 1); // Последний объект - куб

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

    private void handleMouseWheelForward() {
        Vector3F translationVector = new Vector3F(velocity, 0, 0);
        moveCamera(translationVector);
    }

    private void handleMouseWheelBackward() {
        Vector3F translationVector = new Vector3F(-velocity, 0, 0);
        moveCamera(translationVector);
    }

    private void moveCamera(Vector3F translationVector) {
        Vector3F position = camera.getTransform().getPosition().add(translationVector);
        BasicTransform transform = (BasicTransform) camera.getTransform();
        transform.setPosition(position);

        pipeline.run();
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        if (event.isMiddleButtonDown()) {
            isMiddleButtonPressed = true;
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        }
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        if (!event.isMiddleButtonDown()) {
            isMiddleButtonPressed = false;
        }
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        if (isMiddleButtonPressed) {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            Vector3F translationVector = calculateTranslationVectorForMouseDrag(deltaX, deltaY);

            moveCamera(translationVector);
        } else {
            double deltaX = event.getX() - mouseX;
            double deltaY = event.getY() - mouseY;

            // Трансформируем перемещение мыши в вектор перемещения камеры
            Vector3F translationVector = new Vector3F((float) deltaX * -velocity, (float) deltaY * velocity, 0);

            // Переместим камеру
            moveCamera(translationVector);

            // Обновим текущее положение мыши
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }

    private Vector3F calculateTranslationVectorForMouseDrag(double deltaX, double deltaY) {
        float horizontalMovement = (float) -deltaX * velocity;
        float verticalMovement = (float) -deltaY * velocity;
        return new Vector3F(0, verticalMovement, horizontalMovement);
    }


    private void handleKeyEvent(KeyEvent keyEvent) {
        var translationVector = new Vector3F(0, 0, 0);

        switch (keyEvent.getCode()) {
            case W -> translationVector.setX(1);
            case S -> translationVector.setX(-1);
            case D -> translationVector.setZ(1);
            case A -> translationVector.setZ(-1);
            case SPACE -> translationVector.setY(1);
            case CONTROL -> translationVector.setY(-1);
        }

        translationVector = translationVector.scalarMultiply(velocity);
        var position = camera.getTransform().getPosition().add(translationVector);
        var transform = (BasicTransform) camera.getTransform();
        transform.setPosition(position);

        pipeline.run();
    }

    private Vector3F calculateTranslationVector(Vector3F vector, Vector3F direction) {
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
        this.scene.setOnMousePressed(this::onMousePressed);
        this.scene.setOnMouseReleased(this::onMouseReleased);
        this.scene.setOnMouseDragged(this::onMouseDragged);
        this.scene.setOnKeyPressed(this::handleKeyEvent);
        this.scene.setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();

            if (deltaY > 0) {
                handleMouseWheelForward();
            } else if (deltaY < 0) {
                handleMouseWheelBackward();
            }
        });
    }

    @FXML
    private void onOpenModelMenuItemClick() {
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Model");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                if (mesh != null) {
                    ObjWriter.writeModelToObjFile(file.getAbsolutePath(), mesh);
                }
            } catch (ObjWriterException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onSaveNewModelMenuItemClick() {
    }
}