package game.core.entities;

import game.core.entities.ball.Ball;
import game.core.entities.ball.Effects;
import javafx.scene.canvas.GraphicsContext;

public interface Block {
    void hit(Ball ball, Effects effect);
    boolean isDestroyed();
    void render(GraphicsContext gc);
}
