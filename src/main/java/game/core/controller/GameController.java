package game.core.controller;

import game.GameConfig;
import game.core.entities.*;
import game.core.entities.ball.Ball;
import game.core.entities.blocks.Block;
import game.core.entities.blocks.BlockGrid;
import game.core.entities.paddle.Paddle;
import game.core.logic.CollisionHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final GraphicsContext gc;
    private final Canvas canvas;
    private final Ball ball;
    private final Paddle paddle;
    private final Player p1;
    private Block[][] blocks;
    private int highscore;

    private static final List<Ball> balls = new ArrayList<>();


    private boolean paused = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;

    public GameController(GraphicsContext gc, Canvas canvas) {
        this.gc = gc;
        this.canvas = canvas;
        this.ball = new Ball(new MyVector(600, 820), new MyVector(Math.random(), -4));
        addBall(this.ball);
        this.paddle = new Paddle(GameConfig.FRAME_WIDTH / 2);
        this.p1 = new Player();
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case SPACE, ENTER -> {
                if (!gameStarted) {gameStarted = true;}
                else if (gameOver) {resetGame();}
            }
            case LEFT -> { if (!paused) paddle.moveLeft(); }
            case RIGHT -> { if (!paused) paddle.moveRight(); }
            case ESCAPE -> {
                if(!gameOver && gameStarted) {paused = !paused;}
            }
        }
    }

    public void showStartScreen() {
        startGameLoop();
    }

    private void startGameLoop() {
        highscore = 0;
        blocks = BlockGrid.renderBlockGrid(gc, 6,5);
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                gameOver = p1.checkForGameOver();

                if (!gameStarted && !p1.checkForGameOver()) {
                    gc.setFill(Color.WHITE);
                    gc.setFont(new Font(50));
                    drawCenteredText(gc, "Press SPACE to start the Game", GameConfig.FRAME_HEIGHT / 2.0, 40);
                }
                if (gameStarted) {

                    if (paused) {
                        gc.setFill(Color.WHITE);
                        drawCenteredText(gc, "PAUSED\nPRESS ESC TO CONTINUE", GameConfig.FRAME_HEIGHT / 2.0, 20);
                        return;
                    }
                    // Game Over
                    if (gameOver) {
                        renderGameOver(gc);
                        if (p1.getScore() > highscore) {
                            highscore = p1.getScore();
                        }
                        return;
                    }

                    ball.move();
                    CollisionHandler.checkForPaddleCollision(balls, paddle);

                    CollisionHandler.checkEdgeCollision(balls, p1);

                    for (Block[] row : blocks) {
                        for (Block b : row) {
                            b.render(gc); // zeichnet nur, wenn !isDestroyed()
                        }
                    }
                    CollisionHandler.checkBlockCollision(balls, blocks, p1);
                    p1.renderLives(gc);
                    p1.renderScore(gc);
                    paddle.render(gc);
                    for (Ball b : balls) {
                        b.move();
                        b.render(gc);
                    }
                }
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

    private void renderGameOver(GraphicsContext gc) {

        gc.setFill(Color.DARKRED);
        drawCenteredText(gc, "GAME OVER", GameConfig.FRAME_HEIGHT / 2.0, 40);
        gc.setFill(Color.WHITE);
        drawCenteredText(gc, "SCORE: " + p1.getScore(), GameConfig.FRAME_HEIGHT / 2.0 + 30, 20);
        drawCenteredText(gc, "HIGHSCORE: " + highscore, GameConfig.FRAME_HEIGHT / 2.0 + 60, 20);
        drawCenteredText(gc, "Press space to play again", GameConfig.FRAME_HEIGHT / 2.0 + 90, 20);
    }

    private void resetGame() {
        p1.reset(); // Score, Leben zurücksetzen
        ball.reset(new MyVector(600, 600), new MyVector(4, -4)); // Startposition & Geschwindigkeit
        blocks = BlockGrid.renderBlockGrid(gc, 6, 5); // alte Blöcke ersetzen
        gameOver = false;
        gameStarted = true;
        paused = false;
    }

    public static void addBall(Ball b) {
        balls.add(b);
    }

}
