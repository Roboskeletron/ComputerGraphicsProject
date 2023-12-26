package com.cgp.graphics.pipeline;

import com.cgp.graphics.entities.GameObject;
import com.cgp.graphics.entities.Scene;
import com.cgp.graphics.entities.Camera;
import com.cgp.graphics.primitives.rasterization.BarycentricVector;
import com.cgp.math.vector.Vector3F;
import javafx.scene.canvas.GraphicsContext;

public class TexturePipeline extends BasicPipeline {
    public TexturePipeline(Scene scene) {
        super(scene);
    }
}
