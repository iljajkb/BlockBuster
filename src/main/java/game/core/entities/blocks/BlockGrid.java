package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.EffectController;
import game.core.entities.Effects;
import game.core.entities.MyVector;
import javafx.scene.canvas.GraphicsContext;

public class BlockGrid {
    private static Block[][] createGrid(int rows, int cols) {
        Block[][] grid = new Block[rows][cols];
        int vGap = 15;
        int hGap = 15;

        double totalGridWidth = (cols * GameConfig.BLOCK_WIDTH) + ((cols - 1) * hGap);
        // double totalGridHeight = (rows * GameConfig.BLOCK_HEIGHT) + ((rows - 1) * vGap);

        // center the grid
        double startX = (GameConfig.FRAME_WIDTH - totalGridWidth) / 2.0;
        double startY = 100;

        for (int r = 0; r < rows; r++) {
            double y = startY + r * (GameConfig.BLOCK_HEIGHT + vGap);
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (GameConfig.BLOCK_WIDTH + hGap);
                double random = Math.random(); // probability for special blocks
                Block block;
                if (random > 0.9) {
                    block = new ExtraBallBlock();
                } else if (random > 0.8) {
                    Effects randomEffect = EffectController.getRandomEffect();
                    block = new EffectBlock(randomEffect);
                }
                else {
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
