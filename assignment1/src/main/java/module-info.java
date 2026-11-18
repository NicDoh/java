module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example to javafx.fxml;
    opens com.example.game to javafx.fxml;
    opens com.example.entities to javafx.fxml;
    opens com.example.arena to javafx.fxml;
    exports com.example;
    exports com.example.game;
    exports com.example.entities;
    exports com.example.arena;
}