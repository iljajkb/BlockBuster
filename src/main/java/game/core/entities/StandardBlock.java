package game.core.entities;

import game.core.entities.ball.Ball;
import game.core.entities.ball.Effects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StandardBlock implements Block {
    private int hp = 50;

    @Override
    public void hit(Ball ball, Effects effect) {
        hp = hp - ball.getCurrentDamage();
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(50, 50, 100, 70);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(50, 50, 100, 70);
    }
}
