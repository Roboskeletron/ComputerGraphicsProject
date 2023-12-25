package com.cgp.graphics.components.texture;

import com.cgp.graphics.components.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BasicTexture  implements Texture {
    private final Image image;

    public BasicTexture(Image image) {
        this.image = image;
    }

    @Override
    public int getWidth() {
        return (int) image.getWidth();
    }

    @Override
    public int getHeight() {
        return (int) image.getHeight();
    }

    @Override
    public Color getColor(int width, int height) {
        return image.getPixelReader().getColor(width, height);
    }
}
