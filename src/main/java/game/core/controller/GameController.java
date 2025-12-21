package game.core.controller;

import game.GameConfig;
import game.core.entities.*;
import game.core.entities.ball.Ball;
import game.core.entities.blocks.Block;
import game.core.entities.blocks.BlockGrid;
import game.core.entities.blocks.Particle;
import game.core.entities.paddle.Paddle;
import game.core.logic.CollisionHandler;
import game.ui.RenderUIController;
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

    private long lastNs = 0;

    private final List<Ball> balls = new ArrayList<>();
    private final List<Ball> ballsToAdd = new ArrayList<>();
    private final List<Ball> ballsToRemove = new ArrayList<>();

    private final List<Particle> particles = new ArrayList<>();
    private final List<Particle> particlesToAdd = new ArrayList<>();

    private boolean paused = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;

    private final EffectController effectController;

    private final RenderUIController uiController;

    private final LevelController levelController;

    public GameController(GraphicsContext gc, Canvas canvas, int frameHeight) {
        this.gc = gc;
        this.canvas = canvas;
        this.frameHeight = frameHeight;
        this.paddle = new Paddle(GameConfig.FRAME_WIDTH / 2, frameHeight - GameConfig.PADDLE_HEIGHT - 20);
        this.ball = Ball.createMainBall(paddle);
        addBall(this.ball);
        this.p1 = new Player();
        this.effectController = new EffectController(paddle, ball);
        this.uiController = new RenderUIController(gc, effectController, p1);
        this.levelController = new LevelController();
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
        blocks = levelController.initFirstLevel(gc);
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gameOver = p1.checkForGameOver();

                // --- Level cleared ---
                if (BlockGrid.allBlocksDestroyed(blocks)) {
                    blocks = levelController.renderNewLevel(gc, ball, paddle);

                    removeAllExtraBalls();
                    ballsToAdd.clear();
                    ballsToRemove.clear();

                    lastNs = 0; // prevent dt spike
                }

                if (!gameStarted && !p1.checkForGameOver()) {
                    gc.setFill(Color.WHITE);
                    gc.setFont(new Font(50));
                    uiController.drawCenteredText(gc, "Press SPACE to start the Game", GameConfig.FRAME_HEIGHT / 2.0, 40);
                }
                if (gameStarted) {

                    if (paused) {
                        gc.setFill(Color.WHITE);
                        uiController.drawCenteredText(gc, "PAUSED\nPRESS ESC TO CONTINUE", GameConfig.FRAME_HEIGHT / 2.0, 20);
                        return;
                    }

                    if (gameOver) {
                        uiController.renderGameOver(gc);
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

                    for (Particle p : particles) {
                        p.render(gc);
                    }

                    p1.renderLives(gc);
                    p1.renderScore(gc);
                    paddle.render(gc);
                    for (Ball b : balls) {
                        b.render(gc);
                    }
                    uiController.renderEffectText(gc);
                    effectController.update();
                }
            }
        }.start();
    }

    private void updatePhysics(double dt) {
        balls.addAll(ballsToAdd);
        ballsToAdd.clear();

        for (Ball b : balls) {
            if (!b.isAttached()) b.move(dt); // position += velocity(px/s) * dt
        }
        CollisionHandler.checkForPaddleCollision(balls, paddle, effectController, ballsToRemove);
        CollisionHandler.checkEdgeCollision(balls, p1, paddle, frameHeight, this, ballsToRemove);
        CollisionHandler.checkBlockCollision(balls, blocks, p1, ballsToAdd, particles);

        balls.removeAll(ballsToRemove);
        ballsToRemove.clear();

        particles.addAll(particlesToAdd);
        particlesToAdd.clear();

        particles.forEach(p -> p.update(dt));
        particles.removeIf(Particle::isDead);
    }

    private void resetGame() {
        p1.reset();
        ball.reset(paddle);
        blocks = levelController.initFirstLevel(gc);
        gameOver = false;
        gameStarted = true;
        paused = false;
        removeAllExtraBalls();
    }

    public void addBall(Ball b) {
        // safer: we do not add to main list balls
        ballsToAdd.add(b);
    }

    public void removeAllExtraBalls() {
        List<Ball> allExtraBalls = balls.stream().filter(b -> !b.isMain()).toList();
        ballsToRemove.addAll(allExtraBalls);
    }

}
