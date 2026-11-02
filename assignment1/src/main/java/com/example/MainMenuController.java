package com.example;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainMenuController {

    @FXML
    private ImageView backgroundImage;

    private final GameModel model = GameModel.getInstance();

    @FXML
    private void initialize() {
        backgroundImage.setImage(new Image(getClass().getResource(model.getBackground()).toExternalForm()));
    }

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