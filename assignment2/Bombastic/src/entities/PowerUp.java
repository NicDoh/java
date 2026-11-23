package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class PowerUp extends Entity {

    private boolean collected = false;

    public PowerUp(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    public boolean isCollected() { return collected; }

    protected abstract void applyEffect(Player player);

    @Override
    public boolean isOccupying() { return false; }

    @Override
    public void interact(Entity entity) {
        if (!collected && entity instanceof Player p) {
            applyEffect(p);
            collected = true;
        }
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        if (!collected) gc.drawImage(getImage(), getPositionX(), getPositionY(), getWidth(), getHeight());
    }
}
