package settings;

import app.Router;
import game.GameModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import menu.MainMenuView;

public class SettingsView extends StackPane {

    public SettingsView() {
        GameModel model = GameModel.getInstance();

        // Baggrundsbillede bundet reaktivt til model
        ImageView bg = new ImageView();
        bg.imageProperty().bind(Bindings.createObjectBinding(
            () -> loadBg(model.getBackground()),
            model.backgroundProperty()
        ));
        bg.setFitWidth(800);
        bg.setFitHeight(580);
        bg.setPreserveRatio(false);

        // Dropdown-menu til valg af baggrund
        MenuButton bgMenu = new MenuButton("Background");
        MenuItem bg1Item = new MenuItem("Background 1");
        MenuItem bg2Item = new MenuItem("Background 2");
        MenuItem bg3Item = new MenuItem("Background 3");
        bg1Item.setOnAction(e -> { model.setBackground("bg1.png"); bgMenu.setText("Background 1"); });
        bg2Item.setOnAction(e -> { model.setBackground("bg2.png"); bgMenu.setText("Background 2"); });
        bg3Item.setOnAction(e -> { model.setBackground("bg3.png"); bgMenu.setText("Background 3"); });
        bgMenu.getItems().addAll(bg1Item, bg2Item, bg3Item);
        bgMenu.setPrefWidth(200);

        // Dropdown-menu til valg af arena
        MenuButton arenaMenu = new MenuButton("Arena");
        MenuItem a1Item = new MenuItem("Arena 1");
        MenuItem a2Item = new MenuItem("Arena 2");
        MenuItem a3Item = new MenuItem("Arena 3");
        a1Item.setOnAction(e -> { model.setArena("Arena 1"); arenaMenu.setText("Arena 1"); });
        a2Item.setOnAction(e -> { model.setArena("Arena 2"); arenaMenu.setText("Arena 2"); });
        a3Item.setOnAction(e -> { model.setArena("Arena 3"); arenaMenu.setText("Arena 3"); });
        arenaMenu.getItems().addAll(a1Item, a2Item, a3Item);
        arenaMenu.setPrefWidth(200);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> Router.navigate(new MainMenuView()));
        backBtn.setPrefWidth(200);

        VBox panel = new VBox(10, bgMenu, arenaMenu, backBtn);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(30));
        panel.setMaxWidth(240);

        getChildren().addAll(bg, panel);
    }

    private Image loadBg(String name) {
        return new Image(getClass().getResourceAsStream("/assets/backgrounds/" + name));
    }
}
