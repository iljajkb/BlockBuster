package game.core.controller;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.entities.paddle.Paddle;
import game.core.logic.CollisionHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameController {

    private final GraphicsContext gc;
    private final Canvas canvas;
    private final Ball ball;
    private final Paddle paddle;
    private final Player p1;

    private boolean paused = false;
    private boolean gameStarted = false;

    public GameController(GraphicsContext gc, Canvas canvas) {
        this.gc = gc;
        this.canvas = canvas;
        this.ball = new Ball(new MyVector(100, 100), new MyVector(4, -4));
        this.paddle = new Paddle(GameConfig.FRAME_WIDTH / 2);
        this.p1 = new Player("p1");
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case SPACE, ENTER -> gameStarted = true;
            case LEFT -> { if (!paused) paddle.moveLeft(); }
            case RIGHT -> { if (!paused) paddle.moveRight(); }
            case ESCAPE -> paused = !paused;
        }
    }

    public void showStartScreen() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(50));
        drawCenteredText(gc, "Press SPACE to start the Game", GameConfig.FRAME_HEIGHT / 2.0, 40);
        startGameLoop();
    }

    private void startGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Pause
                if (paused) {
                    gc.setFill(Color.WHITE);
                    drawCenteredText(gc, "PAUSED\nPRESS ESC TO CONTINUE", GameConfig.FRAME_HEIGHT / 2.0, 20);
                    return;
                }
                // Game Over
                if (p1.checkForGameOver()) {
                    drawCenteredText(gc, "GAME OVER", GameConfig.FRAME_HEIGHT / 2.0, 30);
                    ball.setVelocity(new MyVector(0, 0));
                    return;
                }

                ball.move();
                CollisionHandler.checkForPaddleCollision(ball, paddle);
                // (Optional) Wände abprallen lassen:
                CollisionHandler.checkEdgeCollision(ball, p1);

                // Render
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                p1.renderLives(gc);
                paddle.render(gc);
                ball.render(gc);

            }
        }.start();

    }

    // Hilfsfunktion für zentrierten Text (GPT 5 Kreation)
    private void drawCenteredText(GraphicsContext gc, String text, double y, double fontSize) {
        gc.setFont(new Font(fontSize));
        javafx.scene.text.Text helper = new javafx.scene.text.Text(text);
        helper.setFont(gc.getFont());
        double textWidth = helper.getLayoutBounds().getWidth();
        double x = (GameConfig.FRAME_WIDTH - textWidth) / 2;
        gc.fillText(text, x, y);
    }

}
