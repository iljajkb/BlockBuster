package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.Particle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;


public class ExtraBallBlock extends Block {
    private static Image blockImage;
    private final static Color COLOR = Color.CYAN;

    public ExtraBallBlock() {
        super(50);
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

        MyVector dir = new MyVector(Math.random(), -1).normalize(); // direction only
        Ball additionalBall = Ball.createExtraBall(this.getPosition(), dir.scale(GameConfig.INITIAL_BALL_SPEED - 50.0)); // extra ball little slower
        return Optional.of(additionalBall);
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.drawImage(blockImage, pos.x - 20, pos.y - 20);
        }
    }
}
