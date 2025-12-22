package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.Particle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.entities.Effects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

public class EffectBlock extends Block {
    private final Effects effect;
    private static Image blockImage;
    private final static Color COLOR = Color.ORANGE;

    public EffectBlock(Effects effect) {
        super(50);
        this.effect = effect;
        if (blockImage == null) {
            CreateBlockImage creator = new CreateBlockImage(COLOR);
            blockImage = creator.createGlowCache();
        }
    }

    @Override
    public Optional<Ball> hit(Ball ball, Player player, List<Particle> particlesToAdd) {
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());

        MyVector pos = this.getPosition();
        if (this.hp <= 0) {
            Particle.animateMultipleParticles(particlesToAdd, pos.x, pos.y);
        }

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
