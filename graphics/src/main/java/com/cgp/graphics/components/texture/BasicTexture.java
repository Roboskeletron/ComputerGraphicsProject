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
        var x = Math.max(0, width);
        x = Math.min(getWidth() - 1, x);
        var y = Math.max(0, height);
        y = Math.min(getHeight() - 1, y);

        return image.getPixelReader().getColor(x, y);
    }
}
