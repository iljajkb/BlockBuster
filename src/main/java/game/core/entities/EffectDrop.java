package game.core.entities;

import game.GameConfig;
import game.core.entities.ball.Ball;
import game.core.entities.ball.BallEffects;
import game.core.entities.paddle.Paddle;
import game.core.entities.paddle.PaddleEffects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EffectDrop {
    private BallEffects ballEffects;
    private PaddleEffects paddleEffects;
    private Ball ball;
    private Paddle paddle;
    private boolean goodEffect;

    public EffectDrop(Ball ball, Paddle paddle, boolean goodEffect) {
        this.ball = ball;
        this.paddle = paddle;
        this.goodEffect = goodEffect;
    }

    public BallEffects getBallEffects() {
        return ballEffects;
    }

    public PaddleEffects getPaddleEffects() {
        return paddleEffects;
    }

    public void spawnEffectDrop(double x, double y, GraphicsContext gc) {
        double random = Math.random();
        boolean good = false;
        if (random > 0.85) {
            if (random >= 0.925) {
                good = true;
            }
            renderEffectDrop(gc, good, x, y);
        }
    }

    private void renderEffectDrop(GraphicsContext gc, boolean good, double x, double y) {
        if (good) {
            gc.setFill(Color.DARKCYAN);
        } else {
            gc.setFill(Color.RED);
        }
        gc.fillRoundRect(x, y, 10, 10, 10, 10);
    }

    private void applyEffect() {

    }
}
