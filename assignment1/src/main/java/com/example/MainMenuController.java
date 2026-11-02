package com.example;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void startGame() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void openChat() throws IOException {
        App.setRoot("chat");
    }

    @FXML
    private void openSettings() throws IOException {
        App.setRoot("settings");
    }

    @FXML
    private void exitGame() {
        Platform.exit();
    }
}