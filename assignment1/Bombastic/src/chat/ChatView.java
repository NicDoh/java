package chat;

import app.Router;
import game.GameModel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import menu.MainMenuView;

public class ChatView extends StackPane {

    public ChatView() {
        GameModel model = GameModel.getInstance();

        ImageView bg = new ImageView(loadBg(model.getBackground()));
        bg.setFitWidth(800);
        bg.setFitHeight(580);
        bg.setPreserveRatio(false);

        // Brugerliste bundet til model via SimpleListProperty
        ListView<String> userList = new ListView<>();
        userList.itemsProperty().bind(model.usersProperty());
        userList.setPrefWidth(140);

        // Beskedfelt bundet reaktivt til model
        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setPrefHeight(460);
        model.messagesProperty().addListener((obs, old, val) -> {
            messageArea.setText(val);
            messageArea.setScrollTop(Double.MAX_VALUE);
        });

        TextField input = new TextField();
        input.setPromptText("Type a message...");

        Button sendBtn = new Button("Send");
        sendBtn.setOnAction(e -> {
            String text = input.getText().trim();
            if (!text.isEmpty()) {
                // Opdater model — messageArea opdateres via listener
                model.appendMessage("You: " + text);
                input.clear();
            }
        });
        input.setOnAction(e -> sendBtn.fire());

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> Router.navigate(new MainMenuView()));

        HBox inputRow = new HBox(8, input, sendBtn, backBtn);
        inputRow.setPadding(new Insets(8));
        HBox.setHgrow(input, Priority.ALWAYS);

        BorderPane chatPanel = new BorderPane();
        chatPanel.setLeft(userList);
        chatPanel.setCenter(messageArea);
        chatPanel.setBottom(inputRow);
        chatPanel.setPadding(new Insets(20));
        chatPanel.setMaxSize(760, 540);

        getChildren().addAll(bg, chatPanel);
    }

    private Image loadBg(String name) {
        return new Image(getClass().getResourceAsStream("/assets/backgrounds/" + name));
    }
}
