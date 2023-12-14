package com.cgp.ui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Canvas mainCanvas;

    public Canvas getMainCanvas() {
        return mainCanvas;
    }
}