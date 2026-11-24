package app;

import javafx.scene.Parent;
import javafx.scene.Scene;

// Central navigationsklasse — alle views bruger denne til at skifte skærm
public class Router {

    private static Scene scene;

    static void init(Scene s) {
        scene = s;
    }

    public static void navigate(Parent view) {
        scene.setRoot(view);
        view.requestFocus();
    }
}
