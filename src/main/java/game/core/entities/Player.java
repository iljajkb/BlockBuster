package game.core.entities;

import game.GameConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Player {
    private int lives = 3;
    private int score = 0;

    public void loseLife() {
        lives--;
    }

    public boolean checkForGameOver() {
        return lives <= 0;
    }

    public void increaseScore(int increment) {
        score = score + increment;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void reset() {
        score = 0;
        lives = 3;
    }
}
