package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Abstrakt base for alle game entities
public abstract class Entity {
    protected double x, y;
    protected double width, height;
    protected Image image;

    public Entity(double x, double y, double width, double height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public double getPositionX() { return x; }
    public double getPositionY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    // Bestemmer om denne entity blokerer bevægelse
    public abstract boolean isOccupying();
    // Håndterer interaktion mellem to entities
    public abstract void interact(Entity entity);
    // Opdaterer entity-tilstand baseret på tid siden sidste frame (ms)
    public abstract void update(double dt);
    // Tegner entity på canvas
    public abstract void render(GraphicsContext gc);
}