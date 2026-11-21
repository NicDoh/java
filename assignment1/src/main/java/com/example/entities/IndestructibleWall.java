package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Indestructible wall - blokerer bevægelse og eksplosioner
public class IndestructibleWall extends StaticEntity {

    public IndestructibleWall(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), getPositionX(), getPositionY(), getWidth(), getHeight());
    }
}