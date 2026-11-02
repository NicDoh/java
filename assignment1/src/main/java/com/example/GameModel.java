package com.example;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameModel {
    private static final GameModel instance = new GameModel();
    private final StringProperty background = new SimpleStringProperty("bg1.png");

    private GameModel() {}

    public static GameModel getInstance() {
        return instance;
    }

    public StringProperty backgroundProperty() {
        return background;
    }

    public String getBackground() {
        return background.get();
    }

    public void setBackground(String bg) {
        background.set(bg);
    }
}