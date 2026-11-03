package com.example;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

// GameModel gemmer applikationens tilstand på tværs af alle views (MVC)
public class GameModel {
    // Singleton - sikrer at alle controllers deler samme instans
    private static final GameModel instance = new GameModel();
    private final StringProperty background = new SimpleStringProperty("bg1.png");
    private final StringProperty arena = new SimpleStringProperty("Arena 1");

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

    public StringProperty arenaProperty() {
        return arena;
    }

    public String getArena() {
        return arena.get();
    }

    public void setArena(String arena) {
        this.arena.set(arena);
    }
}