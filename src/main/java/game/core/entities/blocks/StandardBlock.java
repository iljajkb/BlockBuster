package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;

public class StandardBlock extends Block {

    public StandardBlock() {
        super(50);
    }

    @Override
    public void hit(Ball ball, Player player) {
        // clamp to 0 so we don't go negative
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        // hp = hp - ball.getCurrentDamage();
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.setFill(GameConfig.COLOR_1);
            gc.fillRect(pos.x, pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
        }
    }

}
