package com.example.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Spiller-entity med bevægelse, liv og bombe-placering
public class Player extends Entity {

    private int lives;
    private int maxBombs;
    private int activeBombs;
    private double speed;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private boolean placingBomb;
    // Forhindrer at spilleren mister flere liv fra samme eksplosion
    private boolean hitThisFrame;
    private int explosionRange = 2;
    public int getExplosionRange() { return explosionRange; }
    public void increaseExplosionRange() { explosionRange++; }

    public Player(double x, double y, double width, double height, Image image, int lives, int maxBombs, double speed) {
        super(x, y, width, height, image);
        this.lives = lives;
        this.maxBombs = maxBombs;
        this.activeBombs = 0;
        this.speed = speed;
    }

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) loseLife();
    }

    public void moveX(double dt) {
        if (movingLeft)  x -= speed * dt;
        if (movingRight) x += speed * dt;
    }

    public void moveY(double dt) {
        if (movingUp)   y -= speed * dt;
        if (movingDown) y += speed * dt;
    }

    @Override
    public void update(double dt) {
        hitThisFrame = false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public void loseLife() {
        if (!hitThisFrame) {
            lives--;
            hitThisFrame = true;
        }
    }

    public boolean canPlaceBomb() {
        return activeBombs < maxBombs;
    }

    public void incrementActiveBombs() { activeBombs++; }
    public void decrementActiveBombs() { activeBombs--; }

    public void setMovingUp(boolean b) { movingUp = b; }
    public void setMovingDown(boolean b) { movingDown = b; }
    public void setMovingLeft(boolean b) { movingLeft = b; }
    public void setMovingRight(boolean b) { movingRight = b; }
    public void setPlacingBomb(boolean b) { placingBomb = b; }
    public boolean isPlacingBomb() { return placingBomb; }

    public int getLives() { return lives; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public void setMaxBombs(int maxBombs) { this.maxBombs = maxBombs; }
    public void addLife() { lives++; }
    public int getMaxBombs() { return maxBombs; }
}