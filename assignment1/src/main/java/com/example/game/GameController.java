package com.example.game;

import com.example.App;
import com.example.arena.Arena;
import com.example.entities.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML private StackPane root;

    private GameView gameView;
    private Arena arena;
    private AnimationTimer gameLoop;
    private Player player1;
    private Player player2;
    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Explosion> explosions = new ArrayList<>();
    private final List<PowerUp> powerUps = new ArrayList<>();
    private Image bombImg;
    private Image explosionImg;
    private Image capacityImg;
    private Image rangeImg;
    private Image speedImg;
    private Image lifeImg;

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

        // Indlæs billeder til bombe, eksplosion og powerups
        bombImg      = new Image(getClass().getResourceAsStream("/com/example/assets/bombs/bomb_0.png"));
        explosionImg = new Image(getClass().getResourceAsStream("/com/example/assets/explosions/explosion_3.png"));
        capacityImg  = new Image(getClass().getResourceAsStream("/com/example/assets/powerups/capacity.png"));
        rangeImg     = new Image(getClass().getResourceAsStream("/com/example/assets/powerups/range.png"));
        speedImg     = new Image(getClass().getResourceAsStream("/com/example/assets/powerups/speed.png"));
        lifeImg      = new Image(getClass().getResourceAsStream("/com/example/assets/powerups/life.png"));

        initPlayers();
        initKeyBindings();
        startGameLoop();
        gameView.getCanvas().requestFocus();
    }

    private void initPlayers() {
        // Spillere er lidt mindre end en tile så de kan navigere i korridorer
        double playerSize = Arena.TILE_SIZE * 0.8;
        double offset = (Arena.TILE_SIZE - playerSize) / 2;

        Image img1 = new Image(getClass().getResourceAsStream("/com/example/assets/players/blue_down_0.png"));
        Image img2 = new Image(getClass().getResourceAsStream("/com/example/assets/players/red_down_0.png"));

        // P1 starter øverst til venstre, P2 nederst til højre
        player1 = new Player(Arena.TILE_SIZE + offset, Arena.TILE_SIZE + offset, playerSize, playerSize, img1, 3, 1, 0.1);
        player2 = new Player(13 * Arena.TILE_SIZE + offset, 9 * Arena.TILE_SIZE + offset, playerSize, playerSize, img2, 3, 1, 0.1);
    }

    private void initKeyBindings() {
        // P1: WASD + Mellemrum | P2: IJKL + Enter
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

    private void tryPlaceBomb(Player player) {
        if (!player.isPlacingBomb() || !player.canPlaceBomb()) return;

        // Placér bombe på den tile spillerens centrum befinder sig på
        int col = (int)((player.getPositionX() + player.getWidth() / 2) / Arena.TILE_SIZE);
        int row = (int)((player.getPositionY() + player.getHeight() / 2) / Arena.TILE_SIZE);
        double bx = col * Arena.TILE_SIZE;
        double by = row * Arena.TILE_SIZE;

        // Kun én bombe per tile ad gangen
        for (Bomb b : bombs) {
            if (b.getPositionX() == bx && b.getPositionY() == by) return;
        }

        bombs.add(new Bomb(bx, by, Arena.TILE_SIZE, bombImg, player, player.getExplosionRange()));
        player.incrementActiveBombs();
        player.setPlacingBomb(false);
    }

    private void createExplosions(Bomb bomb) {
        int col = (int)(bomb.getPositionX() / Arena.TILE_SIZE);
        int row = (int)(bomb.getPositionY() / Arena.TILE_SIZE);

        // Alle eksplosioner fra samme bombe deler én alreadyHit-liste
        Set<Entity> alreadyHit = new HashSet<>();

        // Eksplosion på bombens egen tile
        explosions.add(new Explosion(bomb.getPositionX(), bomb.getPositionY(), Arena.TILE_SIZE, explosionImg, alreadyHit));

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // Spred eksplosionen i fire retninger
        for (int dir = 0; dir < 4; dir++) {
            for (int i = 1; i <= bomb.getRange(); i++) {
                int nc = col + dx[dir] * i;
                int nr = row + dy[dir] * i;
                if (nc < 0 || nc >= arena.getCols() || nr < 0 || nr >= arena.getRows()) break;

                Entity tile = arena.getEntity(nc, nr);
                if (tile instanceof IndestructibleWall) break;

                Explosion exp = new Explosion(nc * Arena.TILE_SIZE, nr * Arena.TILE_SIZE, Arena.TILE_SIZE, explosionImg, alreadyHit);
                explosions.add(exp);

                if (tile instanceof DestructibleWall) {
                    tile.interact(exp);
                    // 40% chance for at en powerup spawner bag den ødelagte kasse
                    if (Math.random() < 0.4) {
                        int type = (int)(Math.random() * 4);
                        PowerUp pu = switch (type) {
                            case 0 -> new CapacityPowerUp(nc * Arena.TILE_SIZE, nr * Arena.TILE_SIZE, Arena.TILE_SIZE, capacityImg);
                            case 1 -> new RangePowerUp(nc * Arena.TILE_SIZE, nr * Arena.TILE_SIZE, Arena.TILE_SIZE, rangeImg);
                            case 2 -> new SpeedPowerUp(nc * Arena.TILE_SIZE, nr * Arena.TILE_SIZE, Arena.TILE_SIZE, speedImg);
                            default -> new LifePowerUp(nc * Arena.TILE_SIZE, nr * Arena.TILE_SIZE, Arena.TILE_SIZE, lifeImg);
                        };
                        powerUps.add(pu);
                    }
                    break;
                }
            }
        }
    }

    private boolean collidesWithArena(Player player) {
        // Tjek kollision med vægge og kasser i arenaen
        Entity[][] grid = arena.getGrid();
        for (int row = 0; row < arena.getRows(); row++)
            for (int col = 0; col < arena.getCols(); col++) {
                Entity tile = grid[row][col];
                if (tile.isOccupying() && CollisionDetection.aabb(player, tile)) return true;
            }
        // Tjek kollision med aktive bomber
        for (Bomb bomb : bombs)
            if (bomb.isOccupying() && CollisionDetection.aabb(player, bomb)) return true;
        return false;
    }

    private void checkWinCondition() {
        boolean p1Dead = player1.isDead();
        boolean p2Dead = player2.isDead();

        if (p1Dead || p2Dead) {
            gameLoop.stop();
            String message = (p1Dead && p2Dead) ? "Draw!"
                           : p1Dead             ? "Player 2 wins!"
                                                : "Player 1 wins!";
            showEndScreen(message);
        }
    }

    private void showEndScreen(String message) {
        javafx.scene.canvas.GraphicsContext gc = gameView.getGraphicsContext();

        // Mørkt overlay henover spillet
        gc.setFill(new javafx.scene.paint.Color(0, 0, 0, 0.6));
        gc.fillRect(0, 0, gameView.getCanvas().getWidth(), gameView.getCanvas().getHeight());

        // Vinder-tekst centreret på skærmen
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(48));
        gc.fillText(message, gameView.getCanvas().getWidth() / 2 - 120, gameView.getCanvas().getHeight() / 2);

        // Knapper til at spille igen eller gå til hovedmenu
        javafx.scene.control.Button playAgain = new javafx.scene.control.Button("Play again");
        javafx.scene.control.Button backBtn   = new javafx.scene.control.Button("Main menu");

        playAgain.setOnAction(e -> {
            root.getChildren().clear();
            bombs.clear();
            explosions.clear();
            powerUps.clear();
            initialize();
        });
        backBtn.setOnAction(e -> {
            try { App.setRoot("mainmenu"); } catch (IOException ex) { ex.printStackTrace(); }
        });

        // Placer knapper under teksten
        javafx.scene.layout.HBox buttons = new javafx.scene.layout.HBox(20, playAgain, backBtn);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);
        buttons.setTranslateY(gameView.getCanvas().getHeight() / 2 + 20);
        root.getChildren().add(buttons);
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            long lastTime = -1;
            @Override
            public void handle(long currentTime) {
                if (lastTime == -1) { lastTime = currentTime; return; }
                double dt = (currentTime - lastTime) / 1_000_000.0;
                lastTime = currentTime;

                // Opdater bomber og find dem der skal eksplodere
                List<Bomb> toExplode = new ArrayList<>();
                for (Bomb bomb : bombs) {
                    bomb.update(dt);
                    if (bomb.isExploded()) toExplode.add(bomb);
                }
                for (Bomb bomb : toExplode) {
                    createExplosions(bomb);
                    bomb.getOwner().decrementActiveBombs();
                }
                bombs.removeAll(toExplode);

                // Opdater eksplosioner
                for (Explosion exp : explosions) exp.update(dt);

                // Detects kollisioner concurrent via CollisionDetection
                List<Entity> targets = new ArrayList<>();
                targets.add(player1);
                targets.add(player2);
                targets.addAll(bombs);

                try {
                    for (CollisionDetection.CollisionPair pair :
                            CollisionDetection.findCollisionsConcurrently(explosions, targets))
                        pair.a().interact(pair.b());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                explosions.removeIf(Explosion::isExpired);

                // Opdater arena (erstatter ødelagte kasser med gulv)
                arena.update(dt);

                // Flyt P1 på X-aksen, fortryd hvis kollision
                double p1x = player1.getPositionX();
                player1.moveX(dt);
                if (collidesWithArena(player1) || CollisionDetection.aabb(player1, player2)) player1.setX(p1x);

                // Flyt P1 på Y-aksen, fortryd hvis kollision
                double p1y = player1.getPositionY();
                player1.moveY(dt);
                if (collidesWithArena(player1) || CollisionDetection.aabb(player1, player2)) player1.setY(p1y);

                player1.update(dt);

                // Flyt P2 på X-aksen, fortryd hvis kollision
                double p2x = player2.getPositionX();
                player2.moveX(dt);
                if (collidesWithArena(player2) || CollisionDetection.aabb(player2, player1)) player2.setX(p2x);

                // Flyt P2 på Y-aksen, fortryd hvis kollision
                double p2y = player2.getPositionY();
                player2.moveY(dt);
                if (collidesWithArena(player2) || CollisionDetection.aabb(player2, player1)) player2.setY(p2y);

                player2.update(dt);

                tryPlaceBomb(player1);
                tryPlaceBomb(player2);

                // Spillere samler powerups op ved at gå hen på dem
                for (PowerUp pu : powerUps) {
                    if (CollisionDetection.aabb(player1, pu)) pu.interact(player1);
                    if (CollisionDetection.aabb(player2, pu)) pu.interact(player2);
                }
                powerUps.removeIf(PowerUp::isCollected);

                // Tegn alt på canvas
                gameView.clear();
                arena.render(gameView.getGraphicsContext());
                for (PowerUp pu : powerUps) pu.render(gameView.getGraphicsContext());
                for (Bomb bomb : bombs) bomb.render(gameView.getGraphicsContext());
                for (Explosion exp : explosions) exp.render(gameView.getGraphicsContext());
                player1.render(gameView.getGraphicsContext());
                player2.render(gameView.getGraphicsContext());
                renderHUD(gameView.getGraphicsContext());

                // Tjek om spillet er slut
                checkWinCondition();
            }
        };
        gameLoop.start();
    }

    public void goBack() throws IOException {
        gameLoop.stop();
        CollisionDetection.shutdown();
        App.setRoot("mainmenu");
    }

    private void renderHUD(javafx.scene.canvas.GraphicsContext gc) {
        // Semi-transparent sort bjælke øverst
        gc.setFill(new javafx.scene.paint.Color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, arena.getCols() * Arena.TILE_SIZE, 26);

        gc.setFont(javafx.scene.text.Font.font(18));

        gc.setFill(javafx.scene.paint.Color.CORNFLOWERBLUE);
        gc.fillText("P1 liv: " + player1.getLives(), 5, 20);

        gc.setFill(javafx.scene.paint.Color.TOMATO);
        gc.fillText("P2 liv: " + player2.getLives(), arena.getCols() * Arena.TILE_SIZE - 85, 20);
    }
}