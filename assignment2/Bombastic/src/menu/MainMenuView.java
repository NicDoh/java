package menu;

import app.Router;
import chat.ChatView;
import game.GameController;
import game.GameModel;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import settings.SettingsView;

public class MainMenuView extends StackPane {

    public MainMenuView() {
        GameModel model = GameModel.getInstance();

        ImageView bg = new ImageView(loadBg(model.getBackground()));
        bg.setFitWidth(800);
        bg.setFitHeight(580);
        bg.setPreserveRatio(false);

        // Opdater baggrund, når den skiftes i settings
        model.backgroundProperty().addListener((obs, old, val) -> bg.setImage(loadBg(val)));

        Button startBtn    = new Button("Start Game");
        Button chatBtn     = new Button("Chat");
        Button settingsBtn = new Button("Settings");
        Button exitBtn     = new Button("Exit");

        startBtn.setOnAction(e -> Router.navigate(new GameController()));
        chatBtn.setOnAction(e -> Router.navigate(new ChatView()));
        settingsBtn.setOnAction(e -> Router.navigate(new SettingsView()));
        exitBtn.setOnAction(e -> Platform.exit());

        for (Button btn : new Button[]{startBtn, chatBtn, settingsBtn, exitBtn}) {
            btn.setPrefWidth(160);
        }

        VBox menu = new VBox(12, startBtn, chatBtn, settingsBtn, exitBtn);
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(bg, menu);
    }

    private Image loadBg(String name) {
        return new Image(getClass().getResourceAsStream("/assets/backgrounds/" + name));
    }
}
