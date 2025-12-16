package game.core.entities.blocks;

import game.GameConfig;
import game.core.controller.GameController;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.entities.ball.Effects;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class BadEffectBlock extends Block {
    public BadEffectBlock() {
        super(50);
    }

    @Override
    public void hit(Ball ball, Player player) {
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        MyVector dir = new MyVector(Math.random(), -1).normalize();
        Ball effectBall = Ball.createEffectBall(this.getPosition(), dir.scale(GameConfig.INITIAL_BALL_SPEED), Effects.SLOW_PADDLE);
        System.out.println("EFFECT BALL CREATED");
        GameController.addBall(effectBall);
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.setFill(Color.DARKORANGE);
            gc.fillRect(pos.x, pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
            gc.setFill(Color.BLACK);
            gc.fillText("EFFECT", pos.x + GameConfig.BLOCK_WIDTH / 6.0, pos.y + GameConfig.BLOCK_HEIGHT / 1.5);
        }
    }
}
