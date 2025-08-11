package game.core.entities;

import game.core.entities.ball.Ball;
import game.core.entities.ball.BallEffects;
import javafx.scene.canvas.GraphicsContext;

public interface Block {
    void hit(Ball ball, BallEffects effect);
    boolean isDestroyed();
    void render(GraphicsContext gc);
}
