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

    public int getLives() {
        return lives;
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

    public void renderLives(GraphicsContext gc) {
        double heartX = 20;
        double heartY = 20;
        double heartSize = 15;

        gc.setFill(Color.RED);

        // Herz zeichnen (links + rechts Halbkugel + Dreieck unten)
        gc.fillOval(heartX, heartY, heartSize, heartSize);

        // Text daneben
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(15));
        gc.fillText("x " + lives, heartX + heartSize + 10, heartY + heartSize / 1.5);
    }

    public void renderScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(20));
        gc.fillText("SCORE: " + score, GameConfig.FRAME_WIDTH - 150, 35);
    }

    public void reset() {
        score = 0;
        lives = 3;
    }
}
