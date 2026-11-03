package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class ChatController {

    @FXML
    private void goBack() throws IOException {
        App.setRoot("mainmenu");
    }
}