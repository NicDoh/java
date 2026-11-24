package game;

import app.Router;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import menu.MainMenuView;

// Spilvisning — spilimplementering kommer i assignment 2
public class GameController extends StackPane {

    public GameController() {
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> Router.navigate(new MainMenuView()));
        backBtn.setPrefWidth(160);

        VBox panel = new VBox(backBtn);
        panel.setAlignment(Pos.CENTER);

        getChildren().add(panel);
    }
}
