package com.cgp.graphics.components.texture;

import javafx.scene.paint.Color;

public interface Texture {
    int getWidth();
    int getHeight();
    Color getColor(int width, int height);
}