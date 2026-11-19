package com.example.entities;

import javafx.scene.image.Image;

public class SpeedPowerUp extends PowerUp {
    public SpeedPowerUp(double x, double y, double size, Image image) {
        super(x, y, size, size, image);
    }

    @Override
    protected void applyEffect(Player player) {
        player.setSpeed(player.getSpeed() * 1.2);
    }
}