package game.core.entities.blocks;

import game.core.entities.MyVector;
import game.core.entities.Particle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

import static game.GameConfig.COLOR_1;

public class StrongBlock extends Block{
    private static Image blockImage;

    public StrongBlock() {
        super(100);
        if (blockImage == null) {
            CreateBlockImage creator = new CreateBlockImage(Color.ORCHID);
            blockImage = creator.createGlowCache();
        }
    }

    @Override
    public Optional<Ball> hit(Ball ball, Player player, List<Particle> particlesToAdd) {
        this.hitHandler(ball, player, particlesToAdd);
        return Optional.empty();
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.drawImage(blockImage, pos.x - 20, pos.y - 20);
        }
    }
}
