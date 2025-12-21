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
    private final int frameHeight;
    private final Player p1;
    private Block[][] blocks;
    private int highscore;

    private boolean moveLeftPressed = false;
    private boolean moveRightPressed = false;

    private int initalWidth, initalHeight = 5;

    private long lastNs = 0;

    private static final List<Ball> balls = new ArrayList<>();
    private static final List<Ball> ballsToAdd = new ArrayList<>();

    private boolean paused = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;

    private static String activeEffect = null;
    private List<ActiveEffect> activeEffects = new ArrayList<>();
    private final EffectController effectController;

    public GameController(GraphicsContext gc, Canvas canvas, int frameHeight) {
        this.gc = gc;
        this.canvas = canvas;
        this.frameHeight = frameHeight;
        this.paddle = new Paddle(GameConfig.FRAME_WIDTH / 2, frameHeight - GameConfig.PADDLE_HEIGHT - 20);
        this.ball = Ball.createMainBall(paddle);
        addBall(this.ball);
        this.p1 = new Player();
        this.effectController = new EffectController(paddle, ball);
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case SPACE, ENTER -> {
                if (!gameStarted) {gameStarted = true;}
                else if (gameOver) {resetGame();}
            }
            case UP -> { if (ball.isAttached()) ball.launch(new MyVector(0, -1).scale(GameConfig.INITIAL_BALL_SPEED)); }
            case ESCAPE -> {
                if(!gameOver && gameStarted) {paused = !paused;}
            }
            case LEFT -> moveLeftPressed = true;
            case RIGHT -> moveRightPressed = true;
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT -> moveLeftPressed = false;
            case RIGHT -> moveRightPressed = false;
        }
    }

    public void startGameLoop() {
        highscore = 0;
        blocks = BlockGrid.renderBlockGrid(gc, 6,5);
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                gameOver = p1.checkForGameOver();

                // --- Level cleared ---
                if (BlockGrid.allBlocksDestroyed(blocks)) {
                    renderNewLevel();
                    lastNs = 0; // prevent dt spike
                }

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

                    if (gameOver) {
                        renderGameOver(gc);
                        if (p1.getScore() > highscore) {
                            highscore = p1.getScore();
                        }
                        return;
                    }

                    if (lastNs == 0) { lastNs = now; return; }
                    double dt = (now - lastNs) / 1_000_000_000.0; // seconds
                    lastNs = now;

                    if (dt > 0.25) dt = 0.25;

                    // physics substeps for stable collisions
                    final double STEP = 1.0 / 120.0; // 120 Hz
                    while (dt > 0) {
                        double use = Math.min(dt, STEP);
                        updatePhysics(use); // move all balls here, handle collisions
                        if (!paused && gameStarted && !gameOver) {
                            if (moveLeftPressed) paddle.moveLeft();
                            if (moveRightPressed) paddle.moveRight();
                        }
                        dt -= use;
                    }

                    // --- Follow paddle for attached main ball(s) BEFORE rendering ---
                    for (Ball b : balls) {
                        if (b.isMain() && b.isAttached()) {
                            b.followPaddle(paddle);
                        }
                    }

                    // --- Render (draw only) ---
                    for (Block[] row : blocks) {
                        for (Block blk : row) {
                            blk.render(gc);
                        }
                    }

                    p1.renderLives(gc);
                    p1.renderScore(gc);
                    paddle.render(gc);
                    for (Ball b : balls) {
                        b.render(gc);
                    }

                    balls.addAll(ballsToAdd);
                    ballsToAdd.clear();

                    renderEffectText(gc);
                    effectController.update();
                }
            }
        }.start();
    }

    private void renderNewLevel() {
        ball.reset(paddle);
        if (initalHeight < 8 && initalWidth < 8) {
            initalHeight++;
            initalWidth++;
        }
        removeAllExtraBalls();
        blocks = BlockGrid.renderBlockGrid(gc, initalWidth,initalHeight); // vorläufig
        double incrementedSpeed = ball.getSpeed() * 1.05;
        ball.setSpeed(incrementedSpeed);
    }

    private void updatePhysics(double dt) {
        for (Ball b : balls) {
            if (!b.isAttached()) b.move(dt); // position += velocity(px/s) * dt
        }
        CollisionHandler.checkForPaddleCollision(balls, paddle, effectController);
        CollisionHandler.checkEdgeCollision(balls, p1, paddle, frameHeight);
        CollisionHandler.checkBlockCollision(balls, blocks, p1);
    }

    // help funtion for centered text (with gpt 5)
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

    public void renderEffectText(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new Font(20));
        List<ActiveEffect> list = effectController.getActiveEffects();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder effectText = new StringBuilder("EFFECT ACTIVATED:\n");
        for (ActiveEffect value : list) {
            effectText.append(value.getActiveEffect().name());
            effectText.append("\n");
        }
        gc.fillText(effectText.toString(), 30 , 120);
    }

    private void resetGame() {
        p1.reset();
        ball.reset(paddle);
        blocks = BlockGrid.renderBlockGrid(gc, 6, 5);
        gameOver = false;
        gameStarted = true;
        paused = false;
        removeAllExtraBalls();
    }

    public static void addBall(Ball b) {
        // safer: we do not add to main list balls
        ballsToAdd.add(b);
    }

    public static void removeBall(Ball b) {
        balls.remove(b);
    }

    public static void removeAllExtraBalls() {
        balls.removeIf(b -> !b.isMain());
    }

}
