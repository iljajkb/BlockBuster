package game.core.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

    public boolean checkForGameOver() {
        return lives <= 0;
    }

//    private void renderGameOver(GraphicsContext gc) {
//        gc.fillText("GAME OVER", 50, 50);
//    }

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

}
