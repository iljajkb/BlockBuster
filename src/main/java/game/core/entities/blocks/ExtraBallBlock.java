package game.core.entities.blocks;

import game.GameConfig;
import game.core.controller.GameController;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExtraBallBlock extends Block {
    public ExtraBallBlock() {
        super(50);
    }

    @Override
    public void hit(Ball ball, Player player) {
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        Ball additionalBall = Ball.createExtraBall(this.getPosition(), new MyVector(1, -4));
        GameController.addBall(additionalBall);
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.setFill(GameConfig.COLOR_1);
            gc.fillRect(pos.x, pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
            gc.setFill(Color.DARKBLUE);
            gc.fillOval(pos.x + GameConfig.BLOCK_WIDTH / 2.0 - 10, pos.y + GameConfig.BLOCK_HEIGHT / 4.0, 20, 20);
        }
    }
}
