package com.cgp.graphics.primitives;

import javafx.scene.paint.Color;

public interface Texture {
    int getWidth();
    int getHeight();
    Color getColor(int width, int height);
}