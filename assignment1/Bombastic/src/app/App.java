package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.MainMenuView;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainMenuView(), 800, 580);
        Router.init(scene);
        stage.setTitle("Bombastic");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
