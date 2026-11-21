package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Gulvet i arenaen - blokerer ikke bevægelse og har ingen interaktioner
public class Floor extends StaticEntity {

    public Floor(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), getPositionX(), getPositionY(), getWidth(), getHeight());
    }
}