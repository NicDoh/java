package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SettingsController {

    @FXML
    private ImageView backgroundImage;

    private final GameModel model = GameModel.getInstance();

    @FXML
    private void initialize() {
        // Henter den valgte baggrund fra GameModel når settings loades
        backgroundImage.setImage(new Image(getClass().getResource(model.getBackground()).toExternalForm()));
    }

    @FXML
    private void selectBg1() {
        // Opdaterer baggrund i GameModel og viser den valgte baggrund
        model.setBackground("bg1.png");
        backgroundImage.setImage(new Image(getClass().getResource("bg1.png").toExternalForm()));
    }

    @FXML
    private void selectBg2() {
        model.setBackground("bg2.png");
        backgroundImage.setImage(new Image(getClass().getResource("bg2.png").toExternalForm()));
    }

    @FXML
    private void selectBg3() {
        model.setBackground("bg3.png");
        backgroundImage.setImage(new Image(getClass().getResource("bg3.png").toExternalForm()));
    }

    @FXML
    private void selectArena1() {
        // Gemmer valgt arena i GameModel
        model.setArena("Arena 1");
    }

    @FXML
    private void selectArena2() {
        model.setArena("Arena 2");
    }

    @FXML
    private void selectArena3() {
        model.setArena("Arena 3");
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("mainmenu");
    }
}