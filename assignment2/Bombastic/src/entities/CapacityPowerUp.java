package entities;

import javafx.scene.image.Image;

public class CapacityPowerUp extends PowerUp {
    public CapacityPowerUp(double x, double y, double size, Image image) {
        super(x, y, size, size, image);
    }

    @Override
    protected void applyEffect(Player player) {
        player.setMaxBombs(player.getMaxBombs() + 1);
    }
}
