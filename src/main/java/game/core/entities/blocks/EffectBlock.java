package game.core.entities.blocks;

import game.GameConfig;
import game.core.controller.GameController;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.entities.Effects;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Optional;

import static game.GameConfig.BLOCK_HEIGHT;
import static game.GameConfig.BLOCK_WIDTH;

public class EffectBlock extends Block {
    private final Effects effect;
    private static Image blockImage;

    public EffectBlock(Effects effect) {
        super(50);
        this.effect = effect;
        if (blockImage == null) {
            CreateBlockImage creator = new CreateBlockImage(Color.ORANGE);
            blockImage = creator.createGlowCache();
        }
    }

    @Override
    public Optional<Ball> hit(Ball ball, Player player) {
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        MyVector dir = new MyVector(0, 1).normalize();
        Ball effectBall = Ball.createEffectBall(this.getPosition(), dir.scale(GameConfig.INITIAL_BALL_SPEED * 0.5), effect);
        return Optional.of(effectBall);
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.drawImage(blockImage, pos.x - 20, pos.y - 20);
        }
    }

}
