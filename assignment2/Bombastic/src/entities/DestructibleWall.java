package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Destructible wall - blokerer bevægelse, men kan ødelægges af eksplosioner
public class DestructibleWall extends StaticEntity {

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
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            gc.drawImage(getImage(), getPositionX(), getPositionY(), getWidth(), getHeight());
        }
    }
}
