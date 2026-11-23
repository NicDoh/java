package arena;

import entities.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    public static final double TILE_SIZE = 50;

    private final Entity[][] grid;
    private final int rows;
    private final int cols;
    private Image floorImg;

    public Arena(String mapResource) {
        floorImg   = new Image(getClass().getResourceAsStream("/assets/terrain/grass.png"));
        Image wallImg  = new Image(getClass().getResourceAsStream("/assets/terrain/wall.png"));
        Image crateImg = new Image(getClass().getResourceAsStream("/assets/terrain/crate.png"));

        List<String> lines = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(mapResource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
        } catch (Exception e) {
            throw new RuntimeException("Could not load arena: " + mapResource, e);
        }

        rows = lines.size();
        cols = lines.isEmpty() ? 0 : lines.get(0).length();
        grid = new Entity[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = col * TILE_SIZE;
                double y = row * TILE_SIZE;
                char tile = lines.get(row).charAt(col);
                grid[row][col] = switch (tile) {
                    case 'W' -> new IndestructibleWall(x, y, TILE_SIZE, TILE_SIZE, wallImg);
                    case 'C' -> new DestructibleWall(x, y, TILE_SIZE, TILE_SIZE, crateImg);
                    default  -> new Floor(x, y, TILE_SIZE, TILE_SIZE, floorImg);
                };
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Entity getEntity(int col, int row) { return grid[row][col]; }
    public Entity[][] getGrid() { return grid; }

    public void update(double dt) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Entity e = grid[row][col];
                if (e instanceof DestructibleWall dw && dw.isDestroyed()) {
                    grid[row][col] = new Floor(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, floorImg);
                }
                e.update(dt);
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Entity[] row : grid)
            for (Entity e : row)
                e.render(gc);
    }
}
