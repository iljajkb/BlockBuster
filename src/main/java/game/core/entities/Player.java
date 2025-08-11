package game.core.entities;

import javafx.scene.canvas.GraphicsContext;

public class Player {
    private int lives = 3;
    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public void loseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void checkForGameOver(GraphicsContext gc) {
        if (lives <= 0) {
            renderGameOver(gc);
        }
    }

    private void renderGameOver(GraphicsContext gc) {
        gc.fillText("GAME OVER", 50, 50);
    }

    public int getScore() {
        return score;
    }
}
