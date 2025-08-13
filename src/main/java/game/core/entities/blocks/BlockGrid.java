package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.MyVector;
import javafx.scene.canvas.GraphicsContext;

public class BlockGrid {
    private static Block[][] createGrid(int width, int height) {
        Block[][] grid = new Block[width][height];
        int vGap = 10;
        int hGap = 15;
        int margin = 50;

        for (int r = 0; r < width; r++) {
            double y = margin + r * (GameConfig.BLOCK_HEIGHT + vGap);
            for (int c = 0; c < height; c++) {
                double x = margin + c * (GameConfig.BLOCK_WIDTH + hGap);
                StandardBlock block = new StandardBlock();
                block.setPosition(new MyVector(x, y));
                grid[r][c] = block;
            }
        }
        return grid;
    }

    public static Block[][] renderBlockGrid(GraphicsContext gc, int width, int height) {
        Block[][] grid = createGrid(width, height);
        for (Block[] blocks : grid) {
            for (Block block : blocks) {
                block.render(gc);
            }
        }
        return grid;
    }
}
