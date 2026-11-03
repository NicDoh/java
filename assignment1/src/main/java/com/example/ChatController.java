package com.example;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChatController {

    @FXML
    private ListView<String> userList;
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageInput;
    @FXML
    private ImageView backgroundImage;

    private final GameModel model = GameModel.getInstance();

    @FXML
    private void initialize() {
        // henter den valgte baggrund fra GameModel
        backgroundImage.setImage(new Image(getClass().getResource(model.getBackground()).toExternalForm()));
        // brugere til at simulere et chatrum
        ObservableList<String> users = FXCollections.observableArrayList("Lars", "Allan", "Baune", "Baunshøj");
        userList.setItems(users);
    }

    @FXML
    private void sendMessage() {
        String text = messageInput.getText().trim();
        if (!text.isEmpty()) {
            messageArea.appendText("You: " + text + "\n");
            messageInput.clear();
        }
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("mainmenu");
    }
}