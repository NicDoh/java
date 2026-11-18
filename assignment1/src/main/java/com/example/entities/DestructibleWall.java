package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Destructible wall - blokerer bevægelse, men kan ødelægges af eksplosioner
public class DestructibleWall extends Entity {

    private boolean destroyed = false;

    public DestructibleWall(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isOccupying() {
        return !destroyed;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) destroy();
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            gc.drawImage(image, x, y, width, height);
        }
    }
}