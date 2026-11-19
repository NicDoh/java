package com.example.entities;

import javafx.scene.image.Image;

public class LifePowerUp extends PowerUp {
    public LifePowerUp(double x, double y, double size, Image image) {
        super(x, y, size, size, image);
    }

    @Override
    protected void applyEffect(Player player) {
        player.addLife();
    }
}