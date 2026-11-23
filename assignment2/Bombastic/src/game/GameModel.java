package game;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// GameModel gemmer applikationens tilstand på tværs af alle views (MVC)
public class GameModel {
    // Singleton - sikrer at alle controllers deler samme instans
    private static final GameModel instance = new GameModel();
    private final StringProperty background = new SimpleStringProperty("bg1.png");
    private final StringProperty arena = new SimpleStringProperty("Arena 1");

    // Chat: brugerliste og beskedbuffer
    private final ObservableList<String> userList =
        FXCollections.observableArrayList("Lars", "Allan", "Baune", "Baunshøj");
    private final SimpleListProperty<String> users =
        new SimpleListProperty<>(userList);
    private final StringProperty messages = new SimpleStringProperty("");

    private GameModel() {}

    public static GameModel getInstance() { return instance; }

    public StringProperty backgroundProperty() { return background; }
    public String getBackground() { return background.get(); }
    public void setBackground(String bg) { background.set(bg); }

    public StringProperty arenaProperty() { return arena; }
    public String getArena() { return arena.get(); }
    public void setArena(String a) { arena.set(a); }

    public SimpleListProperty<String> usersProperty() { return users; }
    public ObservableList<String> getUsers() { return userList; }

    public StringProperty messagesProperty() { return messages; }
    public void appendMessage(String msg) { messages.set(messages.get() + msg + "\n"); }
}
