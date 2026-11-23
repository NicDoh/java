package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb extends Entity {
    private static final double FUSE_TIME = 3000;

    private final Player owner;
    private final int range;
    private double timer;
    private boolean exploded;
    private boolean ownerLeft;

    public Bomb(double x, double y, double size, Image image, Player owner, int range) {
        super(x, y, size, size, image);
        this.owner = owner;
        this.range = range;
        this.timer = FUSE_TIME;
    }

    @Override
    public boolean isOccupying() {
        return ownerLeft && !exploded;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) exploded = true;
    }

    @Override
    public void update(double dt) {
        if (exploded) return;
        if (!ownerLeft && !overlapsOwner()) ownerLeft = true;
        timer -= dt;
        if (timer <= 0) exploded = true;
    }

    private boolean overlapsOwner() {
        return owner.getPositionX() + owner.getWidth()  > getPositionX() &&
               getPositionX() + getWidth()  > owner.getPositionX() &&
               owner.getPositionY() + owner.getHeight() > getPositionY() &&
               getPositionY() + getHeight() > owner.getPositionY();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!exploded) gc.drawImage(getImage(), getPositionX(), getPositionY(), getWidth(), getHeight());
    }

    public boolean isExploded() { return exploded; }
    public int getRange() { return range; }
    public Player getOwner() { return owner; }
}
