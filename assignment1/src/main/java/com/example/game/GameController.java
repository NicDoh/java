package com.example.game;

import com.example.App;
import com.example.arena.Arena;
import com.example.entities.Player;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    private StackPane root;

    private GameView gameView;
    private Arena arena;
    private AnimationTimer gameLoop;
    private Player player1;
    private Player player2;

    @FXML
    private void initialize() {
        String arenaName = GameModel.getInstance().getArena();
        String mapResource = switch (arenaName) {
            case "Arena 2" -> "/com/example/assets/arenas/arena2.txt";
            case "Arena 3" -> "/com/example/assets/arenas/arena3.txt";
            default        -> "/com/example/assets/arenas/arena1.txt";
        };

        arena = new Arena(mapResource);

        double canvasW = arena.getCols() * Arena.TILE_SIZE;
        double canvasH = arena.getRows() * Arena.TILE_SIZE;

        gameView = new GameView(canvasW, canvasH);
        root.getChildren().add(gameView);

        initPlayers();
        initKeyBindings();
        startGameLoop();
        gameView.getCanvas().requestFocus();
    }

    private void initPlayers() {
        double playerSize = Arena.TILE_SIZE * 0.8;
        double offset = (Arena.TILE_SIZE - playerSize) / 2;

        Image img1 = new Image(getClass().getResourceAsStream("/com/example/assets/players/blue_down_0.png"));
        Image img2 = new Image(getClass().getResourceAsStream("/com/example/assets/players/red_down_0.png"));

        player1 = new Player(Arena.TILE_SIZE + offset, Arena.TILE_SIZE + offset, playerSize, playerSize, img1, 3, 1, 0.1);
        player2 = new Player(13 * Arena.TILE_SIZE + offset, 9 * Arena.TILE_SIZE + offset, playerSize, playerSize, img2, 3, 1, 0.1);
    }

    private void initKeyBindings() {
        gameView.getCanvas().setOnKeyPressed(evt -> {
            switch (evt.getCode()) {
                case W -> player1.setMovingUp(true);
                case S -> player1.setMovingDown(true);
                case A -> player1.setMovingLeft(true);
                case D -> player1.setMovingRight(true);
                case SPACE -> player1.setPlacingBomb(true);
                case I -> player2.setMovingUp(true);
                case K -> player2.setMovingDown(true);
                case J -> player2.setMovingLeft(true);
                case L -> player2.setMovingRight(true);
                case ENTER -> player2.setPlacingBomb(true);
            }
        });

        gameView.getCanvas().setOnKeyReleased(evt -> {
            switch (evt.getCode()) {
                case W -> player1.setMovingUp(false);
                case S -> player1.setMovingDown(false);
                case A -> player1.setMovingLeft(false);
                case D -> player1.setMovingRight(false);
                case SPACE -> player1.setPlacingBomb(false);
                case I -> player2.setMovingUp(false);
                case K -> player2.setMovingDown(false);
                case J -> player2.setMovingLeft(false);
                case L -> player2.setMovingRight(false);
                case ENTER -> player2.setPlacingBomb(false);
            }
        });
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            long lastTime = -1;
            @Override
            public void handle(long currentTime) {
                if (lastTime == -1) { lastTime = currentTime; return; }
                double dt = (currentTime - lastTime) / 1_000_000.0;
                lastTime = currentTime;

                arena.update(dt);
                player1.update(dt);
                player2.update(dt);

                gameView.clear();
                arena.render(gameView.getGraphicsContext());
                player1.render(gameView.getGraphicsContext());
                player2.render(gameView.getGraphicsContext());
            }
        };
        gameLoop.start();
    }

    public void goBack() throws IOException {
        gameLoop.stop();
        App.setRoot("mainmenu");
    }
}