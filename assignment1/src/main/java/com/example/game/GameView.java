package com.example.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

// Spillets visuelle lag - en Canvas placeret i en StackPane
public class GameView extends StackPane {

    private final Canvas canvas;
    private final GraphicsContext gc;

    public GameView(double width, double height) {
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
        canvas.setFocusTraversable(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public void clear() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}