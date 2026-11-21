package com.example.entities;

import javafx.scene.image.Image;

// Abstrakt mellemklasse for entities der ikke bevæger sig og ikke har komplekse interaktioner
public abstract class StaticEntity extends Entity {

    public StaticEntity(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public void update(double dt) {}

    @Override
    public void interact(Entity entity) {}
}