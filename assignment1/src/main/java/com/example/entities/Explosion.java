package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Set;

public class Explosion extends Entity {
    private static final double DURATION = 500;

    private double timer;
    private boolean expired;
    // Deles mellem alle eksplosioner fra samme bombe
    private final Set<Entity> alreadyHit;

    public Explosion(double x, double y, double size, Image image, Set<Entity> alreadyHit) {
        super(x, y, size, size, image);
        this.timer = DURATION;
        this.alreadyHit = alreadyHit;
    }

    @Override
    public boolean isOccupying() { return false; }

    @Override
    public void interact(Entity entity) {
        if (!alreadyHit.contains(entity)) {
            alreadyHit.add(entity);
            entity.interact(this);
        }
    }

    @Override
    public void update(double dt) {
        timer -= dt;
        if (timer <= 0) expired = true;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!expired) gc.drawImage(image, x, y, width, height);
    }

    public boolean isExpired() { return expired; }
}