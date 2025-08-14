package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.MyVector;
import javafx.scene.canvas.GraphicsContext;

public class BlockGrid {
    private static Block[][] createGrid(int width, int height) {
        Block[][] grid = new Block[width][height];
        int vGap = 15;
        int hGap = 15;
        int margin = 200;
        int topMargin = 75;

        for (int r = 0; r < width; r++) {
            double y = margin + r * (GameConfig.BLOCK_HEIGHT + vGap);
            for (int c = 0; c < height; c++) {
                double x = margin + c * (GameConfig.BLOCK_WIDTH + hGap);
                double random = Math.random(); // probability for special blocks
                Block block;
                if (random > 0.9) {
                    block = new ExtraBallBlock();
                } else {
                    block = new StandardBlock();
                }
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

    public static boolean allBlocksDestroyed(Block[][] grid) {
        for (Block[] blocks : grid) {
            for (Block block : blocks) {
                if (!block.isDestroyed()) {
                    return false;
                }
            }
        }
        return true;
    }

}
