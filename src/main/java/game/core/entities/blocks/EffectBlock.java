package game.core.entities.blocks;

import game.GameConfig;
import game.core.controller.GameController;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.entities.Effects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

public class EffectBlock extends Block {
    private final Effects effect;

    public EffectBlock(Effects effect) {
        super(50);
        this.effect = effect;
    }

    @Override
    public Optional<Ball> hit(Ball ball, Player player) {
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        MyVector dir = new MyVector(0, 1).normalize();
        Ball effectBall = Ball.createEffectBall(this.getPosition(), dir.scale(GameConfig.INITIAL_BALL_SPEED * 0.5), effect);
        System.out.println("EFFECT BALL CREATED");
        return Optional.of(effectBall);
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
