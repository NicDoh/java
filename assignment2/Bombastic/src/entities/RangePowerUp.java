package entities;

import javafx.scene.image.Image;

public class RangePowerUp extends PowerUp {
    public RangePowerUp(double x, double y, double size, Image image) {
        super(x, y, size, size, image);
    }

    @Override
    protected void applyEffect(Player player) {
        player.increaseExplosionRange();
    }
}
