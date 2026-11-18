package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Indestructible wall - blokerer bevægelse og eksplosioner
public class IndestructibleWall extends Entity {

    public IndestructibleWall(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void interact(Entity entity) {}

    @Override
    public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);
    }
}