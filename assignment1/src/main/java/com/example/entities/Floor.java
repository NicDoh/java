package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Gulvet i arenaen - blokerer ikke bevægelse og har ingen interaktioner
public class Floor extends Entity {

    public Floor(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return false;
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