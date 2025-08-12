package game.core.entities;

import game.GameConfig;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;

public class StandardBlock implements Block {
    private int hp = 50;
    private MyVector pos;

    @Override
    public void hit(Ball ball, Player player) {
        // clamp to 0 so we don't go negative
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        // hp = hp - ball.getCurrentDamage();
    }

    @Override
    public boolean isDestroyed() {
        // true once hp is 0 or below
        return hp <= 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            gc.setFill(GameConfig.COLOR_1);
            gc.fillRect(pos.x, pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);

//            gc.setStroke(Color.BLACK);
//            gc.strokeRect(pos.x, pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
        }
    }

    @Override
    public MyVector getPosition() { return pos; }

    @Override
    public void setPosition(MyVector pos) { this.pos = pos; }

}
