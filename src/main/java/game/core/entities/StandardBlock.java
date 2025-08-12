package game.core.entities;

import game.GameConfig;
import game.core.entities.ball.Ball;
import game.core.entities.ball.BallEffects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StandardBlock implements Block {
    private int hp = 50;
    private MyVector pos;

    @Override
    public void hit(Ball ball, BallEffects effect) {
        // clamp to 0 so we don't go negative
        hp = Math.max(0, hp - ball.getCurrentDamage());
    }

    @Override
    public boolean isDestroyed() {
        // true once hp is 0 or below
        return hp <= 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        // draw at the block's position using configured size
        double x = pos != null ? pos.x : 0.0;
        double y = pos != null ? pos.y : 0.0;

        gc.setFill(GameConfig.COLOR_1);
        gc.fillRect(x, y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
        if (isDestroyed()) {
            renderDestroyed(gc);
        }
    }

    @Override
    public MyVector getPosition() { return pos; }

    @Override
    public void setPosition(MyVector pos) { this.pos = pos; }

    public void renderDestroyed(GraphicsContext gc) {
        gc.clearRect(this.pos.x, this.pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
    }
}
