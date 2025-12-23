package game.core.controller;

import game.core.entities.ball.Ball;
import game.core.entities.blocks.Block;
import game.core.entities.blocks.BlockGrid;
import game.core.entities.paddle.Paddle;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;

public class LevelController {
    private static final int MAX_LEVEL = 5;

    private int currentLevel;

    Map<Integer, LevelData> levelMap = new HashMap<>();

    public LevelController() {
        currentLevel = 1;
        LevelData level1 = new LevelData(4,5);
        LevelData level2 = new LevelData(5,5);
        LevelData level3 = new LevelData(6,6);
        LevelData level4 = new LevelData(6,7);
        LevelData level5 = new LevelData(8,9);
        levelMap.put(1, level1);
        levelMap.put(2, level2);
        levelMap.put(3, level3);
        levelMap.put(4, level4);
        levelMap.put(5, level5);

    }

    public Block[][] initFirstLevel(GraphicsContext gc) {
        currentLevel = 1;
        return BlockGrid.renderBlockGrid(gc, getCurrentRows(), getCurrentColumns());
    }

    public Block[][] renderNewLevel(GraphicsContext gc, Ball ball, Paddle paddle) {
        if (currentLevel < MAX_LEVEL) {
            currentLevel++;
        }

        ball.reset(paddle);
        ball.updateSpeed(1.25);

        int currentRows = getCurrentRows();
        int currentColumns = getCurrentColumns();

        return BlockGrid.renderBlockGrid(gc, currentRows, currentColumns);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentRows() {
        return levelMap.get(currentLevel).rows();
    }

    public int getCurrentColumns() {
        return levelMap.get(currentLevel).cols();
    }
}
